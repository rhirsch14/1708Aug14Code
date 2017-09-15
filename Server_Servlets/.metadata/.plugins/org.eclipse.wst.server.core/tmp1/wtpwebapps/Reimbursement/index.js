/**
 * 
 */

var loggedIn = false; // Keeps track of whether a user is logged in
var id = -1; // Keeps track of the id of the logged in user. -1 means no user
// is logged in
var isManager = false; // Keeps track of whether the logged-in user is a
// manager
// Keeps track of the last timeout set
var timeout = -1;

window.onload = function() {

	$("#navbar_div").attr("hidden", true);
	$("#manager_navbar_div").attr("hidden", true);

	setEventListeners();

	// If a session is currently active, log in to that one
	// Otherwise show the login screen
	tryLogin();

	$("#loading_div").attr("hidden", true);

};

function setEventListeners() {

	// Employee
	// Employee navbar
	$("#home").click(viewDashboard);
	$("#profile").click(viewProfile);
	$("#submit_reim").click(viewSubmitReim);
	$("#view_pending_reim").click(viewPendingReim);
	$("#view_resolved_reim").click(viewResolvedReim);

	// Miscellaneous
	$("#submit_button").click(submitReimbursement);
	$("#view_username_check").click(rePopulateReimbursementTable);

	// Manager
	// Manager navbar
	$("#home_manager").click(viewDashboard);
	$("#profile_manager").click(viewProfile);
	$("#resolve_reim").click(resolveReimView);
	$("#view_pending_reim_manager").click(viewAllPendingReim);
	$("#view_resolved_reim_manager").click(viewAllResolvedReim);
	$("#view_employees_reim").click(viewEmployeesReim);
	$("#view_employees").click(viewEmployees);
	$("#register_employee").click(registerEmployeeView);
	$("#view_reim_id_button").click(viewEmployeesReims);
	$("#resolve_button").click(resolveReim);

	// Miscellaneous

	// Set event listeners for logging in and creating an account
	$("#username_input").keypress(handleKeyPress);
	$("#password_input").keypress(handleKeyPress);
	$("#login_button").click(login);
	$("#create_button").click(viewCreateAccount);

	// Miscellaneous
	$("#view_password_check").click(togglePasswordView);
	$("#view_password_check2").click(togglePasswordView2);
	$("#update_profile").click(updateProfile);
	$("#update_profile2").click(createWorker);

	$("#view_button").click(getOneReimbursement);
	$("#view_all_button").click(function () {
		hideAllViews();
		$("#view_reim_div").attr("hidden", false);
		viewReimbursements("#view_reim_div", "PENDING");
	});
}

function viewEmployees() {
	hideAllViews();
	$("#view_employees_div").attr("hidden", false);
	showEmployeesTable();
}

function resolveReimView() {
	hideAllViews();
	$("#view_reimbursement_id")[0].value = "";
	$("#resolve_reim_div").attr("hidden", false);
}

function viewEmployeesReims() {
	hideAllViews();
	$("#view_reim_id_div").attr("hidden", false);
	viewReimbursements("#view_reim_id_display", "",
			$("#employee_id_text")[0].value);
}

function getOneReimbursement() {
	$("#resolve_error_message").text("");
	
	var id = $("#view_reimbursement_id")[0].value;

	if (typeof id === "undefined")
		id = -1; // There are no reimbursements with negative numbers

	var dto = [ "" + id ]; // dto must only contain String objects
	console.log("Get one reimbursement dto: " + dto);
	dto = JSON.stringify(dto);
	sendReceiveXMLResponse("POST", "getOneReimbursement", dto, function(
			responseText) {
		var reimbursement = JSON.parse(responseText);

		var div = "#view_reim_div";

		$(view_reim_outer_div).attr("hidden", false);
		if (reimbursement == null)
			$(div).html(
					"That id does not correspond to a reimbursement<br><br>");
		else
			populateReimbursementsTable( [ reimbursement ]);
	});
}

function registerEmployeeView() {
	hideAllViews();
	$("#register_employee_div").attr("hidden", false);
}

function hideAllViews() {
	$("#login_div").attr("hidden", true);
	$("#profile_div").attr("hidden", true);
	$("#submit_reim_div").attr("hidden", true);
	$("#dashboard_div").attr("hidden", true);

	$("#view_reim_id_div").attr("hidden", true);
	$("#view_reim_outer_div").attr("hidden", true);
	$("#register_employee_div").attr("hidden", true);
	$("#resolve_reim_div").attr("hidden", true);
	$("#view_employees_div").attr("hidden", true);
}

function viewLogin() {
	hideAllViews();
	$("#login_div").attr("hidden", false);
}

function viewDashboard() {
	hideAllViews();
	if (isManager) {
		$("#navbar_div").attr("hidden", true);
		$("#manager_navbar_div").attr("hidden", false);
	} else {
		$("#manager_navbar_div").attr("hidden", true);
		$("#navbar_div").attr("hidden", false);
	}

	var name = "";
	getXMLResponse("GET", "getUserInfo", function(responseText) {
		var user = JSON.parse(responseText).user;
		name = user.firstName + " " + user.lastName;
		$("#dashboard_div_header1").text("Welcome " + name + " to");
	});

	$("#dashboard_div_header2").text("The Online Reimbursement Resource");

	if (isManager)
		$("#dashboard_div_header3")
				.text(
						"HR reminds you that behind every good employee is a dedicated manager.");
	else
		$("#dashboard_div_header3").text("");

	$("#dashboard_div").attr("hidden", false);

}

function createWorker() {

	var firstName = $("#firstname_edit2")[0].value;
	var lastName = $("#lastname_edit2")[0].value;
	var email = $("#email_edit2")[0].value;
	var username = $("#username_edit2")[0].value;
	var password = $("#password_edit2")[0].value;
	var thisIsManager = "" + $("#create_manager_check")[0].checked;

	var dto = [ "-1", firstName, lastName, email, username, password, "" + id,
			"" + thisIsManager ];

	createEmployee("#message_edit2", dto);
}

function viewCreateAccount() {
	hideAllViews();
	$("#login_div").attr("hidden", false);
	$("#create_account_div").attr("hidden", false);
}

function viewProfile() {
	hideAllViews();
	$("#message_edit").text(""); // Clear message text
	// Un-check the view password checkbox
	if (document.getElementById("view_password_check").checked)
		$("#view_password_check").click();
	displayProfileInformation();
	$("#profile_div").attr("hidden", false);
}

function viewPendingReim() {
	hideAllViews();
	$("#view_reim_outer_div").attr("hidden", false);
	console.log("View reimbursements for the following employee id: " + id);
	viewReimbursements("#view_reim_div", "PENDING", id);

}
function viewAllPendingReim() {
	hideAllViews();
	$("#view_reim_outer_div").attr("hidden", false);
	viewReimbursements("#view_reim_div", "PENDING");
}
function viewResolvedReim() {
	hideAllViews();
	$("#view_reim_outer_div").attr("hidden", false);
	viewReimbursements("#view_reim_div", "RESOLVED", id);
}
function viewAllResolvedReim() {
	hideAllViews();
	$("#view_reim_outer_div").attr("hidden", false);
	viewReimbursements("#view_reim_div", "RESOLVED");
}
function viewEmployeesReim() {
	hideAllViews();
	$("#view_reim_id_div").attr("hidden", false);
}
function resolveReim() {
	var reimbursement_id = $("#view_reimbursement_id")[0].value
	if (reimbursement_id == "")
		reimbursement_id = -1;
	var status
	if (document.getElementById("status_approved").checked) {
		status = "APPROVED";
	} else if (document.getElementById("status_denied").checked) {
		status = "DENIED";
	}
	var notes = $("#resolve_notes")[0].value;

	var dto = [ "" + reimbursement_id, status, notes ];

	console.log("Resolving reimbursement with dto: " + dto);

	dto = JSON.stringify(dto);
	sendReceiveXMLResponse("POST", "updateReimbursement", dto, function(
			responseText) {
		var response = JSON.parse(responseText);
		var div = "#resolve_error_message";

		console.log("Response recieved in resolveReimbursement: " + response
				+ " of type: " + typeof response);

		if (response == "true" || response === true) { // Success
			$(div).text("Reimbursement resolved");
			$(div).attr("class", "alert alert-success");
			setTimeout(resolveReimView(), 0);

			// Clear fields
			$("#view_reimbursement_id")[0].value = "";
			$("#resolve_notes")[0].value = "";
		} else {
			$(div).text(response);
			$(div).attr("class", "alert alert-danger");
		}

		$(div).attr("hidden", false);
		// Clear the feedback message
		clearTimeout(timeout);
		timeout = setTimeout(function() {
			$(div).attr("hidden", true);
		}, 5000);

	});

}
function viewSubmitReim() {
	hideAllViews();
	$("#submit_reim_div").attr("hidden", false);

}

function togglePasswordView() {
	if ($("#view_password_check").is(":checked"))
		$("#password_edit").attr("type", "text");
	else
		$("#password_edit").attr("type", "password");
}

function togglePasswordView2() {
	if ($("#view_password_check2").is(":checked"))
		$("#password_edit2").attr("type", "text");
	else
		$("#password_edit2").attr("type", "password");
}

// Called when the user presses a key in the password field
function handleKeyPress(event) {
	var PRESS_ENTER = 13;
	if (event.keyCode == PRESS_ENTER)
		login();
}

// div is the HTML div where the table will be shown
// type is either PENDING, RESOLVED, or "" and determines which reimbursements
// to show.
// id is the id for the employee for which the reimbursements belong to
// if id is undefined (omitted in the function call), then view all.
// reimbursements
function viewReimbursements(div, type, id) {

	if (typeof id === "undefined")
		id == -1;

	var dto = [ "" + id, type ]; // Both of these must be Strings

	console.log("Getting list of reimbursements" + div + " " + dto);

	dto = JSON.stringify(dto);
	sendReceiveXMLResponse("POST", "getReimbursements", dto, function(
			responseText) {
		var reimbursements = JSON.parse(responseText);

		if (reimbursements === null || reimbursements.length === 0)
			$(div).text("There are no " + type + " reimbursements");
		else {

			populateReimbursementsTable( reimbursements);
		}
	});

}

function submitReimbursement() {
	var description = $("#submit_description")[0].value;
	var ammount = $("#submit_ammount")[0].value;

	var dto = [ description, "" + ammount ];

	dto = JSON.stringify(dto);
	console.log("submitReimbursement dto: " + dto);
	sendReceiveXMLResponse("POST", "createReimbursement", dto, function(
			responseText) {
		// Message arrived
		var response = JSON.parse(responseText);
		var div = "#submit_error_message";

		console.log("xhr response arrived in submitReimbursement()");
		if (response == "false" || response === false) {
			// Set feedback message
			$(div).text(response);
			$(div).attr("class", "alert alert-danger");
		} else {

			// Clear fields
			$("#submit_description")[0].value = "";
			$("#submit_ammount")[0].value = "";

			// Set feedback message
			$(div).text("Reimbursement created.");
			$(div).attr("class", "alert alert-success");
		}

		$(div).attr("hidden", false);
		// Clear the feedback message
		clearTimeout(timeout);
		timeout = setTimeout(function() {
			$(div).attr("hidden", true);
		}, 5000);

	});
}

function displayProfileInformation() {
	getXMLResponse("GET", "getUserInfo", function(responseText) {
		var user = JSON.parse(responseText).user;
		console.log("Displaying profile...");
		// Set display information:
		// id, name, username, email
		$("#id_text").text(user.workerId);
		$("#name_text").text(user.firstName + " " + user.lastName);
		$("#username_text").text(user.username);
		$("#email_text").text(user.email);

		// Set values for edit information:
		// firstname, lastname, username, password, email
		$("#firstname_edit").attr("value", user.firstName);
		$("#lastname_edit").attr("value", user.lastName);
		$("#username_edit").attr("value", user.username);
		$("#password_edit").attr("value", user.password);
		$("#email_edit").attr("value", user.email);
	});

}

function formatDate(day) {
	if (day === null)
		return "----";
	var month = day.month.charAt(0).toUpperCase() + day.month.slice(1).toLowerCase();
	return month + " " + day.dayOfMonth + ", " + day.year;
}

function setNotes(notes) {
	if (notes === null)
		return "----";
	else
		return notes;
}

// Either log in to a current session or show the login screen
function tryLogin() {

	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {

		if (xhr.readyState == 4) {
			if (xhr.status == 200) {

				var dto = JSON.parse(xhr.responseText);
				var user = dto.user;
				console.log("User: " + user);

				// Open the navbar and show the home page
				setLoggedInDetails();

			} else if (xhr.status == 418) {

				// Show login page
				viewLogin();
			}
		}
	}
	xhr.open("GET", "getUserInfo", true);
	xhr.send();
}

function setLoggedInDetails() {
	loggedIn = true;
	getXMLResponse("GET", "getUserInfo", function(responseText) {
		var user = JSON.parse(responseText).user;
		id = user.workerId;
		isManager = user.manager;

		if (isManager)
			navbar = "#manager_navbar_div";
		else
			navbar = "#navbar_div";

		$(navbar).attr("hidden", false);
		viewDashboard();
	});

}

// Validates login and returns a useful error message if there is a mistake
function login() {
	var username = $("#username_input")[0].value;
	var password = $("#password_input")[0].value;

	var dto = [ username, password ];

	dto = JSON.stringify(dto);

	sendReceiveXMLResponse(
			"POST",
			"login",
			dto,
			function(responseText) {
				// Message arrived
				var response = responseText;
				var div = "#login_message";
				console
						.log("xhr response arrived in login function in loginMessage.js: "
								+ responseText);
				if (response == "username") {
					$(div).text("Invalid username. Please try again");
					$(div).attr("hidden", false);
				} else if (response == "password") {
					$(div).text("Invalid password. Please try again");
					$(div).attr("hidden", false);
				} else {
					$(div).text("");

					setLoggedInDetails();
				}

				// Clear the feedback message
				clearTimeout(timeout);
				timeout = setTimeout(function() {
					$(div).attr("hidden", true);
				}, 5000)
			});
}

function updateProfile() {
	var userId = $("#id_text").text();
	var firstName = $("#firstname_edit")[0].value;
	var lastName = $("#lastname_edit")[0].value;
	var email = $("#email_edit")[0].value;
	var username = $("#username_edit")[0].value;
	var password = $("#password_edit")[0].value;

	var dto = [ userId, firstName, lastName, email, username, password, "-1",
			"null" ];

	updateEmployee("#message_edit", dto);
}

function createEmployee(div, dto) {
	dto = JSON.stringify(dto);
	console.log("updateProfile dto: " + dto);
	sendReceiveXMLResponse(
			"POST",
			"updateProfile",
			dto,
			function(responseText) {
				// Message arrived
				var response = responseText;

				console.log("xhr response arrived in updateProfile()");
				if (response == "true" || response === true) {

					$(div).text("Employee created.");
					$(div).attr("class", "alert alert-success");

					// Clear fields
					$("#firstname_edit2")[0].value = "";
					$("#lastname_edit2")[0].value = "";
					$("#email_edit2")[0].value = "";
					$("#username_edit2")[0].value = "";
					$("#password_edit2")[0].value = "";
					if (document.getElementById("create_manager_check").checked)
						$("#create_manager_check").click();
					if (document.getElementById("view_password_check2").checked)
						$("#view_password_check2").click();

				} else {
					$(div).text(response);
					$(div).attr("class", "alert alert-danger");
				}

				$(div).attr("hidden", false);
				// Clear the feedback message
				clearTimeout(timeout);
				timeout = setTimeout(function() {
					$(div).attr("hidden", true);
				}, 5000);

			});
}

function updateEmployee(div, dto) {
	dto = JSON.stringify(dto);
	console.log("updateProfile dto: " + dto);
	sendReceiveXMLResponse("POST", "updateProfile", dto,
			function(responseText) {
				// Message arrived
				var response = responseText;

				console.log("xhr response arrived in updateProfile()");
				if (response == "true" || response === true) {
					setTimeout(viewProfile(), 0);

					$(div).text("Information updated.");
					$(div).attr("class", "alert alert-success");

				} else {
					$(div).text(response);
					$(div).attr("class", "alert alert-danger");
				}

				$(div).attr("hidden", false);
				// Clear the feedback message
				clearTimeout(timeout);
				timeout = setTimeout(function() {
					$(div).attr("hidden", true);
				}, 5000);

			});
}

function showEmployeesTable() {

	getXMLResponse("GET", "getEmployees", function(responseText) {
		var employees = JSON.parse(responseText);

		populateEmployeesTable(employees);
	});

}

// type should be "GET" or "POST"
// url corresponds to the servlet being called
// callback is the function to call after the request is received and successful
function getXMLResponse(type, myurl, callback) {
	xmlhttp = new XMLHttpRequest();

	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			if (typeof callback === "function")
				callback(xmlhttp.responseText);
		}
	}

	xmlhttp.open(type, myurl, true);
	xmlhttp.send();
}

function sendReceiveXMLResponse(type, myurl, dto, callback) {
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			callback(xmlhttp.responseText);
		} else if (xmlhttp.status == 500)
			$("#login_message").text("Something went wrong"); // Meant to show
																// on the
		// login page
	}
	xmlhttp.open(type, myurl, true);
	xmlhttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xmlhttp.send(dto);
}

// FIXME how to make the table not so wide OR be able to dynamically shrink
// horizontally
// div is where the table should be displayed
// reimbursements is a list of reimbursements to display
function populateReimbursementsTable( reims ) {
	var div;
	if ($("#view_reim_div").is(":hidden")) {
		div = "#view_reim_id_display";
		$("#view_reim_div").html=("");	// Clear the other one
	}
	else {
		div = "#view_reim_div";
		$("#view_reim_id_display").html=("");	// Clear the other one
	}
	
	var html = "<h3>Reimbursements</h3>";
	console.log("Populating reimbursements table..." + reims.length);
	// Table head
	html += "<table id='reimbursement_table' class='table table-striped'><thead>	"
			+ "<th>Reimbursement ID</th>";
	if ($("#view_username_check")[0].checked)
		html += "<th>Submitter</th>"	+ "<th>Resolver</th>";
	else html += "<th>Submitter ID</th>"	+ "<th>Resolver ID</th>";
	html += "<th>Description</th>"
			+ "<th>Ammount</th>" + "<th>Date opened</th>"
			+ "<th>Date closed</th>" + "<th>Status</th>" + "<th>Notes</th>"
			+ "<tbody>";

	// Table rows
	for (var i = 0; i < reims.length; i++) {
		
		html += "<tr>"
		html += "<td>" + reims[i].reimbursementId + "</td>";
		html += "<td>" + setSubmitterId(reims[i]) + "</td>";
		html += "<td>" + setResolverId(reims[i]) + "</td>";
		html += "<td>" + reims[i].description + "</td>";
		html += "<td>$" + reims[i].ammount + "</td>";
		html += "<td>" + formatDate(reims[i].submitDate) + "</td>";
		html += "<td>" + formatDate(reims[i].resolvedDate) + "</td>";
		html += "<td>" + reims[i].status + "</td>";
		html += "<td>" + setNotes(reims[i].resolveNotes) + "</td>";

		html += "</tr>";
	}

	// Table end
	html += "</tbody></thead></table>";

	$(div).html(html);
	//$("#reimbursement_table").dataTable();
}

function populateEmployeesTable(employees) {

	var html = "<h3>Employees</h3>";
	console.log("Populating employees table...");
	// Table head
	html += "<table id='employee_table' class='table table-striped'><thead>	"
			+ "<th>Worker ID</th>" + "<th>First name</th>"
			+ "<th>Last name</th>" + "<th>Email</th>" + "<th>Username</th>"
			+ "<th>is Manager</th>" + "<tbody>";

	// Table rows
	for (var i = 0; i < employees.length; i++) {
		
		html += "<tr>"
		html += "<td>" + employees[i].workerId + "</td>";
		html += "<td>" + employees[i].firstName + "</td>";
		html += "<td>" + employees[i].lastName + "</td>";
		html += "<td>" + employees[i].email + "</td>";
		html += "<td>" + employees[i].username + "</td>";
		html += "<td>" + employees[i].isManager + "</td>";

		html += "</tr>";
	}

	// Table end
	html += "</tbody></thead></table>";

	$("#view_employees_div").html(html);
	//$("#employee_table").dataTable();
}

function setResolverId(reimbursement) {
	
	if (reimbursement.status == "PENDING")
		return "----";
	else {
		if ($("#view_username_check")[0].checked)
			return reimbursement.resolverUsername;
		else return reimbursement.resolverId;
	}
}

function setSubmitterId(reimbursement) {
	
	if ($("#view_username_check")[0].checked)
		return reimbursement.submitterUsername;
	else return reimbursement.submitterId;
}

function rePopulateReimbursementTable() {
	alert("rePopulateReimbursementTable not implemented yet");
	// viewReimbursements(string, id)
}
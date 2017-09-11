/**
 * 
 */

var loggedIn = false;
window.onload = function(){

	loadHomeView();
	
	if(loggedIn == false){
		$("#navbar").hide();
	}
	else{
		$("#navbar").show();};

		document.getElementById("homePage")
		.addEventListener("click", loadDashboardView);
		document.getElementById("accPage")
		.addEventListener("click", loadAccountPageView);

};


function loadHomeView(){
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			console.log("Message received in loadHomeView: " + xhr.responseText);
			document.getElementById('view').innerHTML = xhr.responseText;		

			// Set event listeners for logging in
			document.getElementById("login_button")
			.addEventListener("click", login);
			document.getElementById("password_input")
			.addEventListener("handleKeyPress", login);

		}
	}
	console.log("getting homepage")
	xhr.open("GET", "../getLoginView", true);
	xhr.send();
};


function loadDashboardView(){
	console.log("in load dashboard view");
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			document.getElementById('view').
			innerHTML = xhr.responseText;
			getUserPageInfo(); // loads user info by calling function

		}
	}
	console.log("getting dash");
	xhr.open("GET", "../getDashboard", true);
	xhr.send();
};

function loadAccountPageView(){
	alert("loading account page view");
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			document.getElementById('view').
			innerHTML = xhr.responseText;
			alert("Getting account information");
			getAccountInformation(); // loads user info by calling function
			
		}
	}
	alert("end of getting accts")
	//xhr.open("GET", "../getAccPage", true);
	//xhr.send();
}

function getAcctPageInfo(){ // loads basic user info and account info into html
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			console.log(xhr.responseText);
			var dto = JSON.parse(xhr.responseText);
			var user = dto.user;
			var accounts = dto.accounts;


			if (accounts.length == 0){
				document.getElementById("accounts").style.visibility = "hidden"; 

			}
			else{

				for(var i = 0; i < accounts.length; i++){
					// populate accounts table
					var table = document.getElementById("accTable");
					var row = table.insertRow();
					var acc = row.insertCell(0);
					var type = row.insertCell(1);
					var bal = row.insertCell(2);
					acc.innerHTML = "Account No. " + accounts[i].id;
					type.innerHTML = accounts[i].type;
					bal.innerHTML = "$" + accounts[i].balance;
				}
			}
		}
	}
	xhr.open("GET", "../getUserInfo", true);
	xhr.send();
	
}


function getUserPageInfo(){ // loads basic user info and account info into html
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			console.log(xhr.responseText);
			var dto = JSON.parse(xhr.responseText);
			var user = dto.user;
			document.getElementById('name')
			.innerHTML = user.firstname + " " + user.lastname;
		}
	}
	xhr.open("GET", "../getUserInfo", true);
	xhr.send();

};

// Validates login and returns a useful error message if there is a mistake
function login() {
	var username = document.getElementById("username_input").value;
	var password = document.getElementById("password_input").value;
	
	var dto = [username, password];
	
	dto = JSON.stringify(dto);
	
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			// Message arrived
			var response =  xhr.responseText;

			console.log("xhr response arrived in login function in loginMessage.js: " + xhr.responseText);
			if (response == "fail"){
				document.getElementById("message")
				.innerHTML = "Invalid credentials. Please try again";
			}
			else if(response == "pass"){
				document.getElementById("message")
				.innerHTML = "Invalid user. Please try again";
			}
			else{
				loggedIn = true;
				console.log(response);
				console.log("calling success function");
				loadDashboardView();
				$("#navbar").show();
			}
			
		}
	}
	
	xhr.open("POST", "../loginMessageTest", true);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send(dto);
}


function getAccountInformation() {
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {

		if (xhr.readyState == 4 && xhr.status == 200) {
			var dto = JSON.parse(xhr.responseText);
			var accounts = dto.accounts;

			if (accounts.length == 0) {
				document.getElementById("accounts").style.visibility = "hidden";
				console.log("null");
				console.log("accounts");
			} else {
				console.log("not null");
				console.log(accounts.length);
				populateAccountsTable(accounts);
			}
		}
	}
	xhr.open("GET", "../getUserInfo", true);
	xhr.send();

}

function populateAccountsTable(accounts) {
	var html = "";

	// Table head
	html += "<table class='table table-striped'><thead>	" + "<th>ID</th>"
			+ "<th>Date opened</th>" + "<th>Level</th>" + "<th>Type</th>"
			+ "<th>Balance</th><tbody>";

	// Table rows
	for (var i = 0; i < accounts.length; i++) {

		var day = accounts[i].accountOpenedDate;
		html += "<tr>"
		html += "<td>" + accounts[i].accountId + "</td>";
		html += "<td>" + day.month + " " + day.dayOfMonth + ", " + day.year
				+ "</td>";
		html += "<td>" + accounts[i].level + "</td>";
		html += "<td>" + accounts[i].type + "</td>";
		html += "<td>" + accounts[i].balance + "</td>";
		html += "</tr>";
	}

	// Table end
	html += "</tbody></thead></table>";

	document.getElementById('accounts_table').innerHTML = html;
}


// Called when the user presses a key in the password field
function handleKeyPress(event) {
	var PRESS_ENTER = 13;
	if (event.keyCode == PRESS_ENTER)
		login();
}
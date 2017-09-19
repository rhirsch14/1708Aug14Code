
/*
function loadUser(){
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			document.getElementById('content1').innerHTML = xhr.responseText;
			getUserInfo();
			$('#edit').click(function(){
				if($('#edit').text() == "Edit"){
					$("#fn").prop('readonly', false);
					$("#ln").prop('readonly', false);	
					$("#email").prop('readonly', false);
					$("#password").prop('readonly', false);	
					$('#edit').text("Save");	
				}else if($('#edit').text() == "Save"){
					$('#myModal').appendTo("body").modal('show');
					$('#update').click(validateUpdateUserInfo);
				}
			})
		}
	}	
	xhr.open("GET", "loadUser", true);
	xhr.send();
}

function getUserInfo(){
	$.ajax({
		type: 'GET',
		url: 'userInfo',
		success: function(response){
			//var dto = JSON.parse(response);
			var user = response;
			$('#fn').val(user.fname);
			$('#ln').val(user.lname);
			$('#email').val(user.email);
			$('#password').val(user.password);
		}
	})
}

function validateUpdateUserInfo(){
	var password1 = $('#password1').val(); 
 	var password2 = $('#password2').val();	

 	if(password1 == password2){
	 	var to = [password2];

	 	to = JSON.stringify(to);

		$.ajax({
	 		type: 'POST',
	 		url: 'validateUser',
	 		data: to,
	 		dataType: 'JSON',
	 		success: function(response){
				if(response == true){
					var fn = $('#fn').val();
					var ln = $('#ln').val();
				 	var email = $('#email').val();
				 	var password = $('#password').val();
				 	var to = [fn,ln,email,password];

				 	to = JSON.stringify(to);
					$.ajax({
				 		type: 'POST',
				 		url: 'updateUserInfo',
				 		data: to,
				 		dataType: 'JSON',
				 		success: function(response2){
							if(response2 == "User Information Updated"){
								//$('#updateStatus').text("User Information Updated");
								$("#fn").prop('readonly', true);
								$("#ln").prop('readonly', true);
								$("#email").prop('readonly', true);
								$("#password").prop('readonly', true);
								$('#edit').text("Edit");
								$('#myModal').appendTo("body").modal('hide');
								$('#password1').val("");
								$('#password2').val("");
				 			}
				 		}
					})	
				}else if(response == false){
					$('#updateStatus').text("Invalid Password");
				}
	 		}	
		})
 	}
	else{
 		$('#updateStatus').text("Password Mismatch");
	}
}*/

/*
function loadReimE(){
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			document.getElementById('content1').innerHTML = xhr.responseText;
			getReimInfoE();	
		}
	}
	xhr.open("GET", "loadReim", true);
	xhr.send();
}

function loadReimM(){
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			document.getElementById('content1').innerHTML = xhr.responseText;
			getReimInfoM();	
		}
	}
	xhr.open("GET", "loadReim", true);
	xhr.send();
}

function getReimInfoE(){
	$.ajax({
		type: 'GET',
		url: 'reimInfo',
		success: function(response){
			var reim = response;

			for(var x = 0; x<reim.length; x++){
				var tr = "<tr>";
				tr += "<td>" + reim[x].reimID + "</td>";
				tr += "<td>" + reim[x].description + "</td>";
				if(reim[x].notes){
					tr += "<td>" + reim[x].notes + "</td>";
				}
				else{
					tr += "<td></td>";
				}
				tr += "<td>" + (reim[x].submitterID.fname + " " + reim[x].submitterID.lname) + "</td>";
				if(reim[x].resolverID){
					tr += "<td>" + (reim[x].resolverID.fname + " " + reim[x].resolverID.lname) + "</td>";
				}
				else{
					tr += "<td></td>";
				}
				tr += "<td>" + reim[x].submitDate + "</td>";
				if(reim[x].resolveDate){
					tr += "<td>" + reim[x].resolveDate + "</td>";
				}
				else{
					tr += "<td></td>";
				}
				tr += "<td class='statusClick'>" + reim[x].statusID.statusName + "</td>";
				tr += "<td class='money'>$" + reim[x].amount.toFixed(2) + "</td>";
				tr += "</tr>"	
				$( "#reimTable tbody" ).append(tr);
			}

			var status = [
			"Pending",
			"Approved",
			"Denied"
			]

			var reimTable = $('#reimTable').DataTable({
				"pageLength": 5,
				"bLengthChange": false,	
    			/*
    			"columnDefs": [
    			{ "width": "15px", "targets": 0 },
    			{ "width": "150px", "targets": 1 },
    			{ "width": "70px", "targets": 2 },
    			{ "width": "70px", "targets": 3 },
    			{ "width": "60px", "targets": 4 },
    			{ "width": "60px", "targets": 5 },
    			{ "width": "40px", "targets": 6 },
    			{ "width": "60px", "targets": 7 },
    			],
    			*/

/*

    			initComplete: function () {
    				this.api().columns([0,3,4,7]).every( function () {
    					var column = this;
    					var select = $('<select><option value="">Show all</option></select>');
    					select.appendTo($(column.footer()).empty()).on( 'change', function () {
    						var val = $.fn.dataTable.util.escapeRegex($(this).val());
    						column.search( val ? '^'+val+'$' : '', true, false ).draw();
    					} );
    					column.cells('', column[0]).render('display').sort().unique().each( function ( d, j )
    					{
    						select.append( '<option value="'+d+'">'+d+'</option>' )
    					} );
    				} );
    			}
    		});
		}
	})
}

function getReimInfoM(){
	$.ajax({
		type: 'GET',
		url: 'reimInfo',
		success: function(response){
			var reim = response;

			for(var x = 0; x<reim.length; x++){
				var tr = "<tr>";
				tr += "<td>" + reim[x].reimID + "</td>";
				tr += "<td>" + reim[x].description + "</td>";
				if(reim[x].notes){
					tr += "<td>" + reim[x].notes + "</td>";
				}
				else{
					tr += "<td></td>";
				}
				tr += "<td>" + (reim[x].submitterID.fname + " " + reim[x].submitterID.lname) + "</td>";
				if(reim[x].resolverID){
					tr += "<td>" + (reim[x].resolverID.fname + " " + reim[x].resolverID.lname) + "</td>";
				}
				else{
					tr += "<td></td>";
				}
				tr += "<td>" + reim[x].submitDate + "</td>";
				if(reim[x].resolveDate){
					tr += "<td>" + reim[x].resolveDate + "</td>";
				}
				else{
					tr += "<td></td>";
				}
				// status class for onclick
				tr += "<td class='statusClick'>" + reim[x].statusID.statusName + "</td>";
				/*const status0 = "Pending";
				const status1 = "Approved";
				const status2 = "Denied";
				let option0 = "<option value=" + status0 + ">" + status0 + "</option>";
				let option1 = "<option value=" + status1 + ">" + status1 + "</option>";
				let option2 = "<option value=" + status2 + ">" + status2 + "</option>";
				if(reim[x].amount == status0){
					option0 = "<option value=" + status0 + " selected='selected'>" + status0 + "</option>";
				}else if(reim[x].amount == status1){
					option0 = "<option value=" + status1 + " selected='selected'>" + status1 + "</option>";
				}else if(reim[x].amount == status2){
					option0 = "<option value=" + status2 + " selected='selected'>" + status2 + "</option>";
				}
				tr += "<td><select>" + option0 + option1 + option2 + "</select></td>";
				*/

/*

				tr += "<td class='money'>$" + reim[x].amount.toFixed(2) + "</td>";
				tr += "</tr>"	
				$( "#reimTable tbody" ).append(tr);
			}

			var status = [
				"Pending",
				"Approved",
				"Denied"
			]

			var reimTable = $('#reimTable').DataTable({
				"pageLength": 5,
    			"bLengthChange": false,	
    			/*
    			"columnDefs": [
    			{ "width": "15px", "targets": 0 },
    			{ "width": "150px", "targets": 1 },
    			{ "width": "70px", "targets": 2 },
    			{ "width": "70px", "targets": 3 },
    			{ "width": "60px", "targets": 4 },
    			{ "width": "60px", "targets": 5 },
    			{ "width": "40px", "targets": 6 },
    			{ "width": "60px", "targets": 7 },
    			],
    			*/
    			/*
    			"columns": [
    				null,
    				null,
    				null,
    				null,
    				null,
    				{
    					render: function(data, type, row, meta){
    						/*
    						var $select = $("<select></select>", {
    							"id": row[0]+"start",
    							"value": data
    						});
    						$.each(status, function(key, value){
    							var $option = $("<option></option>", {
    								"text": value,
    								"value": value
    							});
    							if(data === value){
    								$option.attr("selected", "selected")
    							}
    							$select.append($option);
    						});
    						console.log()
    						return $select.prop("outerHTML");*//*
    						var selectStatus = "<select>";
    						for(y in status){
    							if(data === status[y]){
    								selectStatus += "<option value=\'"+status[y]+"\'' selected='selected'>"+status[y]+"</option>";
    							}else{
    								selectStatus += "<option value=\'"+status[y]+"\''>"+status[y]+"</option>";
    							}
    						}
    						selectStatus += "</select>";

    						return selectStatus;
    					}
    				},
    				null	
    			],*/

/*

				initComplete: function () {
					this.api().columns([0,3,4,7]).every( function () {
						var column = this;
						var select = $('<select><option value="">Show all</option></select>');
						select.appendTo($(column.footer()).empty()).on( 'change', function () {
							var val = $.fn.dataTable.util.escapeRegex($(this).val());
							column.search( val ? '^'+val+'$' : '', true, false ).draw();
						} );
						column.cells('', column[0]).render('display').sort().unique().each( function ( d, j )
						{
							select.append( '<option value="'+d+'">'+d+'</option>' )
						} );
					} );
				}
			} );

			$('#reimTable').on('click','tr .statusClick', function(){
				var cell = reimTable.cell(this);
				var data = cell.data();
				$('#statusModal').appendTo("body").modal('show');
				$('#reimDescription').val(reimTable.row(this).data()[1]);
				if(data == "Pending"){
					$('[value="Pending"]').attr('selected',true);
				}else if(data == "Approved"){
					$('[value="Approved"]').attr('selected',true);
				}else if(data == "Denied"){
					$('[value="Denied"]').attr('selected',true);
				}
				var reimID = reimTable.row(this).data()[0];
				$('#statusUpdate').one("click",function(){
					var notes = $('#notes').val();
					var status = $('#statusSelected option:selected').val();

					var to = [reimID, status, notes];
				 	to = JSON.stringify(to);

					$.ajax({
				 		type: 'POST',
				 		url: 'updateReim',
				 		data: to,
				 		dataType: 'JSON',
				 		success: function(response2){
				 			if(response2 != "Failure"){
					 			$('#statusModal').appendTo("body").modal('hide');
								$('#notes').val("");
								var current = new Date();
								reimTable.cell(cell.index().row,2).data(notes);
								reimTable.cell(cell.index().row,4).data(response2);
								reimTable.cell(cell.index().row,6).data(datetime());
								cell.data($('#statusSelected option:selected').text());
				 			}
				 		}
					})
				})
			})
/*
			var selectStatus = "<select>";
			selectStatus += "<option value='Show All' selected='selected'>Show All</option>";
			for(y in status){
				selectStatus += "<option value=\'"+status[y]+"\''>"+status[y]+"</option>"
			}
			selectStatus += "</select>";

			console.log(selectStatus);
			$('#selectStatus').append(selectStatus).on("change",function(){
				

				//console.log(reimTable.columns(5));
				//reimTable.columns(5).search("Pending").draw();
			});
*/
/*
			var reimTable = document.getElementById('reimTable');
			console.log(reimTable);
			
			for(var x = 0; x<reim.length; x++){
				var row = reimTable.insertRow(x+1);
				var col1 = row.insertCell(0);
				var col2 = row.insertCell(1);
				var col3 = row.insertCell(2);
				var col4 = row.insertCell(3);
				var col5 = row.insertCell(4);
				var col6 = row.insertCell(5);
				var col7 = row.insertCell(6);

				col1.innerHTML = reim[x].reimID;
				col2.innerHTML = (reim[x].submitterID.fname + " " + reim[x].submitterID.lname);
				if(reim[x].resolverID){
					col3.innerHTML = (reim[x].resolverID.fname + " " + reim[x].resolverID.lname);
				}
				else{
					col3.innerHTML = (reim[x].resolverID);
				}
				col4.innerHTML = reim[x].submitDate;
				col5.innerHTML = reim[x].resolveDate;
				col6.innerHTML = reim[x].statusID.statusName;
				col7.innerHTML = reim[x].amount;
				console.log(reim[x]);
			}
*/

/*
			for(var x = 0; x<reim.length; x++){
				var resID;
				if(reim[x].resolverID){
					resID = (reim[x].resolverID.fname + " " + reim[x].resolverID.lname);
				}
				else{
					resID = (reim[x].resolverID);
				}

				reimTable.row.add([
					reim[x].reimID,
					(reim[x].submitterID.fname + " " + reim[x].submitterID.lname),
					resID,
					reim[x].submitDate,
					reim[x].resolveDate,
					reim[x].statusID.statusName,
					reim[x].amount,
				]).draw(true);

			}
*/

/*

		}
	})
}

function datetime(){
	var dt = new Date($.now());
	var date = dt.getMonth() + "/" + dt.getDate() + "/" + dt.getFullYear();
	var hour = dt.getHours();
	var minutes = dt.getMinutes();
	var seconds = dt.getSeconds();
	var ampm;

	if(hour > 11){
		ampm = "PM";
	}else{
		ampm = "AM";
	}

	if(hour > 12){
		hour = hour-12;
	}

	if(minutes <= 9){
		minutes = "0" + minutes;
	}

	if(seconds <= 9){
		seconds = "0" + seconds;
	}
	var time12 = hour +  ":" + minutes + ":" + seconds + " " + ampm;

	return date + " " + time12;
}*/

/*
function loadSubmitReim(){
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			document.getElementById('content1').innerHTML = xhr.responseText;
			$('#submit').click(function(){
				submitReim();
				$('#submitModal').appendTo("body").modal('show');
			});
		}
	}
	xhr.open("GET", "loadSubmitReim", true);
	xhr.send();
}

function submitReim(){
	var description = $('#description').val();
	var amount = $('#amount').val();

 	var to = [description,amount];

 	to = JSON.stringify(to);
 	$.ajax({
 		type: 'POST',
 		url: 'submitReim',
 		data: to,
 		dataType: 'JSON',
 		success: function(response){
			if(response == "Reimbursement Submitted"){
				$('#submitStatus').text("Reimbursement Submitted");
				$('body').one("click", loadSubmitReim);
 			}
 		}
	})
}*/

/*
function loadEmployee(){
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			document.getElementById('content1').innerHTML = xhr.responseText;
			loadEmployeeInfo();
			
		}
	}
	xhr.open("GET", "loadEmployee", true);
	xhr.send();
/*
	console.log("loadEmployee")
	$.ajax({
		type: 'GET',
		url: 'loadEmployee',
		success: function(){
			loadEmployeeInfo();
		}
	})*/
//}

/*

function loadEmployeeInfo(){
	console.log("loadEmployeeInfo")
	$.ajax({
		type: 'GET',
		url: 'employeeInfo',
		success: function(response){
			console.log(response);
			var emps = response;
			for(var x = 0; x<emps.length; x++){
				var tr = "<tr>";
				tr += "<td>" + emps[x].fname + "</td>";
				tr += "<td>" + emps[x].lname + "</td>";
				tr += "<td>" + emps[x].email + "</td>";
				tr += "</tr>"	
				$( "#empTable tbody" ).append(tr);
			}

			var empTable = $('#empTable').DataTable({
				"pageLength": 8,
				"bLengthChange": false,
    			initComplete: function () {
    				this.api().columns([0,1,2]).every( function () {
    					var column = this;
    					var select = $('<select><option value="">Show all</option></select>');
    					select.appendTo($(column.footer()).empty()).on( 'change', function () {
    						var val = $.fn.dataTable.util.escapeRegex($(this).val());
    						column.search( val ? '^'+val+'$' : '', true, false ).draw();
    					} );
    					column.cells('', column[0]).render('display').sort().unique().each( function ( d, j )
    					{
    						select.append( '<option value="'+d+'">'+d+'</option>' )
    					} );
    				} );
    			}
    		});
		}
	})
}*/

/*
function loadRegisterEmployee(){
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			document.getElementById('content1').innerHTML = xhr.responseText;
			$('#register').click(function(){
				registerEmployee();
				$('#registerModal').appendTo("body").modal('show');
			});
		}
	}
	xhr.open("GET", "loadRegisterEmployee", true);
	xhr.send();
}

function registerEmployee(){
	var fn = $('#fn').val();
	var ln = $('#ln').val();
 	var email = $('#email').val();
 	var password = $('#password').val();

 	var to = [fn,ln,email,password];

 	to = JSON.stringify(to);

	$.ajax({
		type: 'POST',
		url: 'registerEmployee',
		data: to,
		dataType: 'JSON',
		success: function(response){
			if(response == "Employee Registered"){
				$('#registerStatus').text("Employee Registered");
				$('body').one("click", loadRegisterEmployee);
			}else if(response == "Failed Registration"){
				$('#registerStatus').text("Failed Registration");
				$('body').one("click", loadRegisterEmployee);
			}
		}
	})
}*/

/*
function invalidateSession(){
	$.ajax({
		type: 'GET',
		url: 'logout',
		success: function(){
			loadLogin();
			//window.location = "";
		}
	})
}*/
/*
function loadHome(){
	$.ajax({
		type: 'GET',
		url: 'loadHome',
		success: function(response){
			document.getElementById('content1').innerHTML = response;
		}
	})
}*/
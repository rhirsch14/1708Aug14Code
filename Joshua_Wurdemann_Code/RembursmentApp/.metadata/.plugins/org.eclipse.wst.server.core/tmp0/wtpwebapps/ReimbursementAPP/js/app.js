var logged = false;
window.onload = function(){

	loadHomeView();
	
	if(logged == false){
		$("#navbar").hide();
	}
	else{
		$("#navbar").show();};

		document.getElementById("homePage")
		.addEventListener("click", loadDashboardView);

		document.getElementById("remPage")
		.addEventListener("click", loadReimbursmentPageView);
	
};


function loadHomeView(){
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			//console.log(xhr.responseText);
			document.getElementById('view').innerHTML = xhr.responseText;		
			document.getElementById("login")
				.addEventListener("click", login);
		}
	}
	console.log("getting homepage")
	xhr.open("GET", "getLoginView", true);
	xhr.send();
};


function login(){
	var email = document.getElementById("email").value;
	var pass = document.getElementById("pass").value;
	var tx = [email, pass];
	tx = JSON.stringify(tx);

	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			var response =  xhr.responseText;

			if (response == "fail"){
				document.getElementById("message")
				.innerHTML = "Invalid credentials. Please try again";
			}
			else if(response == "pass"){
				document.getElementById("message")
				.innerHTML = "Invalid user. Please try again";
			}
			else{
				logged = true;
				console.log(response);
				loadDashboardView();
				$("#navbar").show();
			}
		}
	}

	xhr.open("POST", "login", true);
	xhr.setRequestHeader("Content-type",
	"application/x-www-form-urlencoded");
	xhr.send(tx);
};


function loadDashboardView(){
	console.log("in load dashboard view");
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			document.getElementById('view').
			innerHTML = xhr.responseText;
			getUserPageInfo(); // loads user info by calling function
			getReimbursmentPageInfo();
		}
	}
	console.log("getting dash");
	xhr.open("GET", "getDashboard", true);
	xhr.send();
};




function loadReimbursmentPageView(){
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			document.getElementById('view').
			innerHTML = xhr.responseText;
			getReimbursmentPageInfo(); // loads user info by calling function
			
		}
	}
	console.log("getting user reimbursments")
	xhr.open("GET", "getRemPage", true);
	xhr.send();
}

function getReimbursmentPageInfo(){ // loads basic user info and account info into html
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			console.log("inside reimbursement page info js function");
			console.log(xhr.responseText);
			var dto = JSON.parse(xhr.responseText);
			var someUser = dto.user;
			var rem = dto.reimbursement;

			//document.getElementById("username").innerHTML = "Welcome " + someUser.user.firstname + " " + someUser.user.lastname + ".";
			if (rem.length == 0){
				document.getElementById("reimbursements").style.visibility = "hidden"; 

			}
			else{

				for(var i = 0; i < rem.length; i++){
					// populate accounts table
					var table = document.getElementById("remTable");
					var row = table.insertRow();
					var remid = row.insertCell(0);
					var amount = row.insertCell(1);
					var sumitdate = row.insertCell(2);
					var description = row.insertCell(3);
     				remid.innerHTML = rem[i].reimburseid;
					amount.innerHTML = "$" + rem[i].amount;
					sumitdate.innerHTML = rem[i].submitDate;
					description.innerHTML =  rem[i].description;
				}
			}
		}
	}
					
	xhr.open("GET", "getUserInfo", true);
	xhr.send();
	
}


function getUserPageInfo(){ // loads basic user info and account info into html
	console.log("in user page info function");
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			console.log(xhr.responseText);
			var someUser = JSON.parse(xhr.responseText);
			var user = someUser.user;
			document.getElementById('name').innerHTML = user.firstName + " " + user.lastNAme;
		}
	}
	xhr.open("GET", "getUserInfo", true);
	xhr.send();

};


function addRemibursement(){ // allows us to add new reimbursements 
	var rem = document.getElementbyId("addRem").value;
	console.log(rem);

	var type = JSON.stringify(rem);

	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			console.log(xhr.responseText);

		}
	}

	xhr.open("POST", "addReimbursement" , true);
	//set the header to tell the server you have data for it to process
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");//research this !!!
	xhr.send(type); //include your post data in the send()

}


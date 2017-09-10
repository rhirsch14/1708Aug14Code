window.onload = function(){
	loadDashboardView();
};

function loadDashboardView(){
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status ==200){
			document.getElementById('view').innerHTML = xhr.responseText;
			getUserInformation();
		}
	}
	xhr.open("GET","getDashboard",true);
	xhr.send();
}

function getUserInformation(){
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status ==200){
			console.log(xhr.responseText);
			var dto = JSON.parse(xhr.responseText);
			var emp = dto.emp;
			var reimb = dto.reimbs;
			
			document.getElementById('name').innerHTML = emp.firstname + " " +emp.lastname;
			
			if(reimb.length == 0){
				
			}
			else{
				for(var i =0; i <reimb.length; i++){
					var table = document.getElementById("empReimbs");
					var row = table.insertRow();
					var reid = row.insertCell(0);
					var subdate = row.insertCell(1);
					var status = row.insertCell(2);
					var desc = row.insertCell(3);
					var amt = row.insertCell(4);
					reid.innerHTML = "No.: " + reimb[i].id + " ";
					subdate.innerHTML = reimb[i].submitdate+ " ";
					status.innerHTML = reimb[i].type.name + " ";
					desc.innerHTML = reimb[i].descript+ " ";
					amt.innerHTML = "$ "+reimb[i].amount;
				}
			}
			//document.getElementById('info').innerHTML = JSON.stringify(user,null,4);
		}
	}
	xhr.open("GET","getEmployeeInfo",true);
	xhr.send();
}
function getName(){
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			document.getElementById("name").innerHTML = xhr.responseText;
		}
	}
	xhr.open("POST", "login", true);
	xhr.send();
}
document.addEventListener("DOMContentLoaded", getName);
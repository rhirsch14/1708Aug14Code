/**
 * 
 */

loadNavBar();

function loadNavBar() {
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			// Replace the "view" div with the xhr response
			document.getElementById("navbar_view").innerHTML = xhr.responseText;
			
		}
	}
	xhr.open("GET", "../getNavBarView", true);
	xhr.send();
}
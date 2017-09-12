/**
 * JS HOMEWORK
 * KATIE BIXBY
 */

function fib(n){
	if(n<=1) return n;
	else return (fib(n-1) + fib(n-1));
}

function runFib(){
	var display = document.getElementById("fibDisplay");
	var n = document.getElementById("fib").value;
	display.innerHTML = fib(n);
	
	
}

document.getElementById("doFib").addEventListener("click",runFib);
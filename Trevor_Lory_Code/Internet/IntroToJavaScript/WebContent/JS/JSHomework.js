/**
 * JS HOMEWORK Trevor Lory
 * 
 * -----------------------------------------------------------------------------------
 * PART I Create a single Javascript file called homework.js to answer these
 * questions. Please put the question itself as a comment above each answer.
 * -----------------------------------------------------------------------------------
 */

// 1. Fibonacci
// Define function: fib(n)
// Return the nth number in the fibonacci sequence.
function fib(n) {
	if (n <= 1)
		return n;
	else
		return (fib(n - 1) + fib(n - 2));
}

function runFib() {
	var display = document.getElementById("fibDisplay");
	var n = document.getElementById("fib").value;
	display.innerHTML = fib(n);
}

document.getElementById("doFib").addEventListener("click", runFib);
//document.getElementById("inner").addEventListener("click", function(){alert("inner!")});
//document.getElementById("middle").addEventListener("click", function(){alert("middle!")});
//document.getElementById("outer").addEventListener("click", function(){alert("outer!")});
/**
*
*/

//1. Fibonacci
//Define function: fib(n) 
//Return the nth number in the fibonacci sequence.

function fib(n){
	if(n <= 1)return n;
	
	else return fib(n-1) + fib(n-2);
}
function runFib(){
	var display= document.getElementById("fibDisplay");
	var n = document.getElementById("fib").value;
	display.innerHTML = fib(n);
}
document.getElementById("doFib").addEventListener("click", runFib);

/*
 *. Bubble Sort
Define function: bubbleSort(numArray)
Use the bubble sort algorithm to sort the array.
Return the sorted array. 
 * 
 */
function bubbleSort(a)
{
    var swapped;
    do {
        swapped = false;
        for (var i=0; i < a.length-1; i++) {
            if (a[i] > a[i+1]) {
                var temp = a[i];
                a[i] = a[i+1];
                a[i+1] = temp;
                swapped = true;
            }
        }
    } while (swapped);
}


//3. Reverse String
//Define function: reverseStr(someStr)
//Reverse and return the String.
function reverseStr(str){
	var  splitStr = str.split("");
	var reverseArray = splitStr.reverse();
	var joint =reverseArray.join("");
	return joint;
}
function runReverseStr(){
	var display = document.getElementById("reverseString");
	var str = document.getElementById("rev").value;
	display.innerHTML = reverseStr(str);
}
document.getElementById("doReverse").addEventListener("click", runReverseStr);

/*4. Factorial
Define function: factorial(someNum)
Use recursion to compute and return the factorial of someNum.
 * 
 * 
 */
 function factorial(n){
	 if (n==1) return n;
	 else return n* factorial(n-1);
 }
 function runFactorial(){
		var display= document.getElementById("displayFactorial");
		var n = document.getElementById("fac").value;
		display.innerHTML = factorial(n);
	}

 document.getElementById("doFact").addEventListener("click", runFactorial);
 //5
 
 /*
  * 6. Even Number
Define function: isEven(someNum)
Return true if even, false if odd.
Do not use % operator.
  */
 
 function isEven(someNum){
	 var m = someNum/2;
	 if(someNum === m * 2)
		 return true;
	 else
		 return false;
	 
 }
 function runIsEven(){
		var display= document.getElementById("dispalyIsEven");
		var n = document.getElementById("even").value;
		display.innerHTML = isEven(n);
	}
 document.getElementById("doIsEven").addEventListener("click", runIsEven);
 
 /*
  * 7. Palindrome
Define function isPalindrome(someStr)
Return true if someStr is a palindrome, otherwise return false
  */
function isPalindrome(someStr){
	var  splitStr = someStr.split("");
	var reverseArray = splitStr.reverse();
	var joint =reverseArray.join("");
	if(someStr == joint)
		return true;
	else
		return false;
	
}
/*
8. Shapes
Define function: printShape(shape, height, character)
shape is a String and is either "Square", "Triangle", "Diamond".
height is a Number and is the height of the shape. Assume the number is odd.
character is a String that represents the contents of the shape. Assume this String contains just one character.
Use a switch statement to determine which shape was passed in.
Use the console.log function to print the desired shape.
Example for printShape("Square", 3, "%");
%%%
%%%
%%%
Example for printShape("Triangle", 3, "$");
$
$$
$$$
Example for printShape("Diamond", 5, "*");
  *
 ***
*****
 ***
  *
  *
  
  */
function printShape(shape,height, character){
	switch(shape){
	case 'square':
		var str = character;
		for(var i=1; i <= height; i++){
			var sq="";
			for(var j = height; j>=height; j--){
				sq += str.charAt('0'); 				
			}
			console.log(s);			
		}
		
		break;
	case 'triangle':
		var str = character;
		for(var i=0; i <= height; i++){
			var s = "";
			for(var j = i; j>=1; j--){
				s += str.charAt('0'); 				
			}
			console.log(s);		
			//break;
		}
		break;
	case'diamond':
		break;
	}
		}

		
		
/*	
10. Delete Element
Define function deleteElement(someArr)
Print length
Delete the third element in the array.
Print length
The lengths should be the same.*/
function deleteArray(someArr){
	console.log(someArr.length);
	delete someArr[3];
	console.log(someArr.length);
	
}
/*
11. Splice Element
Define function spliceElement(someArr)
Print length
Splice the third element in the array.
Print length*/
function spliceArray(someArr){
	console.log(someArr.length);
	 someArr.splice(3,1);
	console.log(someArr.length);
	
}
/*
13. Defining an object using an object literal
Define function getPerson(name, age)
The following line should set a Person object to the variable john:
	var john = getPerson("John", 30);*/


function getPerson(name, age){
	
	var person={
			p_name: name,
			p_age: age	
		}
	console.log(person);}	


//document.getElementById("inner").addEventListener("click", function(){alert("inner!")})
//document.getElementById("middle").addEventListener("click", function(){alert("middle!")})
//document.getElementById("outer").addEventListener("click", function(){alert("outer!")})
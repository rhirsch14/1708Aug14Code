/**
 * JS HOMEWORK
 * ANDREW BONDS



-----------------------------------------------------------------------------------
PART I
Create a single Javascript file called homework.js to answer these questions.
Please put the question itself as a comment above each answer.
-----------------------------------------------------------------------------------
 */

/*
 * Problem 1
 */

function fib(n){ 
	if(n <= 1) return n;
	else return (fib(n-1) + fib(n-2));
	}

function runFib(){
	var display = document.getElementById("fibDisplay");
	var n = document.getElementById("fibN").value;
	display.innerHTML = fib(n);
}
  
document.getElementById("doFib").addEventListener("click",runFib);

document.getElementById("inner").addEventListener("click", 
		function(){alert("inner!")});

document.getElementById("middle").addEventListener("click", 
		function(){alert("middle!")});

document.getElementById("outer").addEventListener("click", 
		function(){alert("outer!")});


/*
 * Problem 2
 */


var numArray = (1,0,5,6,3,2,3,7,9,8,4);


//I think this works???????
function bubbleSort(items) {
	  var length = items.length;
	  for (var i = 0; i < length; i++) { 
	    for (var j = 0; j < (length - i - 1); j++) { 
	      if(items[j] > items[j+1]) {
	        var tmp = items[j];  
	        items[j] = items[j+1]; 
	        items[j+1] = tmp; 
	      }
	    }        
	  }
	}
console.log(numArray);

/*
 * Problem 3
 * 
 */

function reverseString(str){

	var value = str;
			for(var i = value.length - 1; i >= 0; i--) { //For loop that initializes i at the end of string and increments backwards
		console.log(value.charAt(i)); //Prints out the character at i, thus reversing it.
	}   
}

/*
 * Problem 4
 */


function getFactorial(x) { //The getFactoral function. It's static since its part of the same class.
	/*
	 * The following is using recursion to calculate the factorial
	 */
	if(x == 0) //When x reaches 0 return 1
		return 1;
	else //Else use recursion to rerun the function to get the result.
		return x = x * getFactorial(x-1);
}

/*
 * Problem 5
 */


function subString(someStr, length, offset){
	
	if(isNaN(someStr)){
		var str = " ";

		var idx = length;
		for(var i = offset; i<=idx; i++){
			str = str.concat(someStr.charAt(i))
		}
	return str;
	}
	else{
		alert("This is a Number");
	}
}

/*
 * Problem 6
 */


function isEven(x){
	var num = x /2;
	
	if(Number.isSafeInteger(num)){
		return true;
	}
	else
		return false;
}

/*
 * Problem 7
 */


function isPalin(someStr){
	var str = "";
	
	for(var i = 0; i <= someStr.length; i++){
		str = str.concat(someStr.charAt(i));
	}
	if(str === someStr){
		return true;
	}
	else
		return false;
}

/*
 * Problem 8 (FINISH DIAMOND)
 */


function printShape(shape, height, character){
	var counter;
	
	switch(shape){
	case("Square"):
		counter = height;
	for(var i = 0; i < height; i++){
		for(var j = 0; j < counter; j++){
			console.log(character);
		}
	}
	break;
	case("Triangle"):
		counter = 1;
	for(var i = 0; i < height; i++){
		counter++
		for(var j = 0; j <= counter; j++){
			console.log(character);
		}
}
	break;
	case("Diamond"):
		break;
	}

	
}

/*
 * Problem 9
 */

var Sue = {fname: "Sue", lname: "Hercales", email: "SHeracles123@gmail.com"};

function traverseObject(someObj){
	var person = someObj;
	var txt = "";
	var x;
	for(x in person){
		txt += person[x] + " ";
	}
	console.log(txt);
}

/*
 * Problem 10
 */

var array = [5,5,3,5,5];

function deleteElement(array){
	console.log(array.length);
	delete array[2];
	console.log(array.length);
	
}

/*
 * Problem 11
 */


function spliceElement(array){
	console.log(array.length);
	array.splice(2,1);
	console.log(array.length);
}

/*
 * Problem 12
 */

function person(name, age){
 	this.name = name;
	this.age = age;   
}
var John = new person("John", 30);



/*
 * Problem 13 (WORK ON LATER)
 */


function getPerson(name, age){
	var john = {name: "John", age: 30};

}






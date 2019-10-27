// run with js --polyglot --jvm polyglot.js

var array = Polyglot.eval("R", "c(1,2,42,4)")

// load a Python Library that defines a Function called Fibonacci; 
// get hold of a reference to that Python function and invoke the function from JS through that reference
Polyglot.evalFile("python", "library.py");
fibFunc = Polyglot.eval("python", `Fibonacci`)
print("Fibonacci(9) - called from JS, executed in Python "+fibFunc(9))

var title = Polyglot.eval("python", "title")
print ("Title retrieved from Python: "+ title)

// Store value in Polyglot Map - read and update it in Python then read it again in JS
Polyglot.export("name","Wim")
print("Name from Polyglot Map: "+ Polyglot.import("name"))
Polyglot.eval("python", `print("Name from Polyglot is ", polyglot.import_value("name"))`)
Polyglot.eval("python", `polyglot.export_value("Hans" , "name")`)
print("Name from Polyglot Map "+ Polyglot.import("name"))


var arrayPython = Polyglot.eval("python", "[11,21,84,41]")
var arrayRuby = Polyglot.eval("ruby", "[1,2,42,4]")
console.log(array[2]);
console.log(arrayPython[3]);
console.log(arrayRuby[1]);

// create a lambda function in Python, return it to JS and invoke it from JS
var square = Polyglot.eval("python",`(lambda x: x*x)`);
var x = 5
print(`Square of ${x} = ${square(x)} - according to Python`)

// store the function reference in Polyglot and invoke it in R; return the result and receive in JS
Polyglot.export("PythonSquare", square)
fnc = Polyglot.eval("R", `function(input) {
    squared <- import('PythonSquare')
    result <- squared(input)
    print( paste('Calculated Square in R using Python Function retrieved from Polyglot through JS : ', result))
    result
}`)
print(`Square of 9 = ${fnc(9)}`)

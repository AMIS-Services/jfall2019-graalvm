// run with js --polyglot --jvm polyglot.js

var array = Polyglot.eval("R", "c(1,2,42,4)")

Polyglot.evalFile("python", "library.py");

fibFunc = Polyglot.eval("python", `Fibonacci`)

//fibFunc = Polyglot.import("Fibonacci")
print("Fibonacci(9) - called from JS, executed in Python "+fibFunc(9))

var title = Polyglot.eval("python", "title")
print ("Title retrieved from Python: "+ title)

Polyglot.export("name","Wim")
print("Name from Polyglot Map: "+ Polyglot.import("name"))
Polyglot.eval("python", `print("Name from Polyglot is ", polyglot.import_value("name"))`)
Polyglot.eval("python", `polyglot.export_value(value="Hans",name="name")`)
print("Name from Polyglot Map"+ Polyglot.import("name"))


var arrayPython = Polyglot.eval("python", "[11,21,84,41]")
var arrayRuby = Polyglot.eval("ruby", "[1,2,42,4]")
console.log(array[2]);
console.log(arrayPython[3]);
console.log(arrayRuby[1]);


var square = Polyglot.eval("python",`(lambda x: x*x)`);
var x = 5
print(`Square of ${x} = ${square(x)} - according to Python`)
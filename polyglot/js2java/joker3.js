// run this application in a GraalVM execution environment with:
// node --jvm --vm.cp application-bundle.jar --polyglot joker3.js
//node --jvm --vm.cp=/my/class/path 
//js --jvm --vm.cp=/my/class/path

const howManyJokesCanWeTake = 5

const javaJokerClass = Java.type('nl.amis.js2java.Joker');
const javaJoker1 = new javaJokerClass();

// var IntArray = Java.type("int[]");
// var iarr = new IntArray(5);

var JArray = Java.type('java.lang.reflect.Array');
var JString = Java.type('java.lang.String');
// create a Java String Array
print("* Create, populate and use a Java String[] in JavaScript code")

var jokeArray = JArray.newInstance(JString, howManyJokesCanWeTake);
for (var joke=0;joke<5;joke++){
  jokeArray[joke] = javaJoker1.getJoke()
}

for (let joke of jokeArray) {
  print(joke)
}  

// get String[] returned from Java
print("* Receive a Java String[] as return object from Java to JavaScript")
jokeArray =  javaJoker1.getJokes(howManyJokesCanWeTake)
for (let joke of jokeArray) {
  print(joke)
}  


// get Map with String[] returned from Java
print("* Use a Map as transfer object to shuttle between JavaScript and Java")
var HashMap = Java.type('java.util.HashMap');
var transferObject = new HashMap();
transferObject.put("desiredNumberOfJokes", howManyJokesCanWeTake);
javaJoker1.transferJokes(transferObject)
jokeArray = transferObject.get("jokes")
for (let joke of jokeArray) {
  print(joke)
}  
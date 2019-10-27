// run this application in a GraalVM execution environment with:
// node --jvm --vm.cp application-bundle.jar validateJS2J2JS.js
//node --jvm --vm.cp=/my/class/path 
//js --jvm --vm.cp=/my/class/path

const howManyJokesCanWeTake = 5

const javaValidatorClass = Java.type('nl.amis.java2js.ValidateThroughNPMValidator');
const javaValidator = new javaValidatorClass();

var postalCode = "1253 EX"
print(`Postal Code ${postalCode} is a ${javaValidator.isPostalCode(postalCode,'NL')} value`)

var postalCode = "AS 1252"
print(`Postal Code ${postalCode} is a ${javaValidator.isPostalCode(postalCode,'NL')} value`)
// run this application in a GraalVM execution environment with:
// node --jvm --vm.cp application-bundle.jar  joker2.js
//node --jvm --vm.cp=/my/class/path 
//js --jvm --vm.cp=/my/class/path
// see: https://github.com/graalvm/graaljs/blob/master/docs/user/JavaInterop.md

const javaJokerClass = Java.type('nl.amis.js2java.Joker');
const javaJoker1 = new javaJokerClass()

function crackJoke() {
   print(getJoke())
}

function getJoke() {
    return javaJoker1.getJoke()
}

var i=1
while (i++<10) {
  crackJoke()
}  
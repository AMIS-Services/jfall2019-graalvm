# make sure to install graalpython: 
# to run: graalpython --polyglot --jvm polyglot.py
# check https://fniephaus.com/2019/icw19-polyglot-adapters.pdf 

import polyglot
import java

array = polyglot.eval(language="js", string="[1,2,42,4]")
print("JavaScript ",array[2])

array = polyglot.eval(language="R", string="c(1L,2L,42L,4L)")
print("R ",array[1])


array = java.type("int[]")(4)
array[2] = 42
print("Java", array[2])

array = polyglot.eval(language="ruby", string="[1,2,42,4]")
print("Ruby", array[2])

title = "Polyglot Programming"

# object_methods = [method_name for method_name in dir(polyglot)
#                   if callable(getattr(polyglot, method_name))]
# print (object_methods)

polyglot.export_value (title, "title" )

# methods on polyglot: ['__invoke__', '__element_info__', '__has_size__', '__get_size__', '__new__', '__keys__', '__key_info__', '__execute__', '__remove__', '__read__', 'eval', 'import_value', '__is_boxed__', '__is_null__', '__has_keys__', 'export_value', '__write__', '__loader__', '__getattribute__', '__bool__', '__str__', '__hash__', '__eq__', '__delattr__', '__class__', '__init_subclass__', '__setattr__', '__getattr__', '__repr__', '__init__', '__truffle_richcompare__', '__ne__', '__format__', '__reduce__', '__reduce_ex__', '__sizeof__', '__dir__']
func = polyglot.eval(language="js", string="(function(name) { print(`Title from polyglot ${Polyglot.import('title')}`);return `Hello ${name}, welcome to the world of JavaScript`; })")
print(func)
msg = func("Hank")
print("The result of invoking the function produced by the JavaScript function: ",msg)



polyglot.eval(language="js", string="Polyglot.export('key','value');")
value = polyglot.import_value ("key" )
print("Imported key from polyglot",value)

# have JS create a function and store it in Polyglot
polyglot.eval(language="js", string="Polyglot.export('squared', x => {print(x); return x * x})")
js_squared = polyglot.import_value('squared')
result = js_squared(22)
print("The result of invoking function squared imported from polyglot",result)

# have R create a function, leveraing the function squared stored from JS in polyglot
fnc = polyglot.eval(string="""function(input) {
    squared <- import('squared')
    result <- squared(input)
    print( paste('Imported title from Polyglot: ', import('title')))
    result
}""", language="R")

# invoke the function returned from R, leveraging JS
print("squared from R using function from JS",fnc(5))
#import polyglot
import polyglot

# def printMethods(object):
#     print [method for method in dir(object) if callable(getattr(object, method))]

title = "Polyglot Programming - defined in Python"

#polyglot.export ("title", title)

# try out a little polyglotting
arrayX = [199,299,99,4]
print(arrayX[2])

def Fibonacci(n): 
    if n<0: 
        print("Incorrect input") 
    # First Fibonacci number is 0 
    elif n==1: 
        return 0
    # Second Fibonacci number is 1 
    elif n==2: 
        return 1
    else: 
        return Fibonacci(n-1)+Fibonacci(n-2) 
  
# Driver Program 
#polyglot.export( Fibonacci,"Fibonacci")  
print("Fibonacci(9)",Fibonacci(9)) 
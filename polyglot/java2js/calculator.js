function fibonacci(num) {
    if (num <= 1) return 1;
    return fibonacci(num - 1) + fibonacci(num - 2);
  }

function squareRoot(num) {
    if (num < 0) return NaN;
    return Math.sqrt(num);
 }
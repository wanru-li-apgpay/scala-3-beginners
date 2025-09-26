package com.rockthejvm.part1basics

object C_Functions {

  def aFunction(a: String, b: Int): String =
    a + " " + b // 这里是一行表达式，返回字符串

  // 函数调用
  val aFunctionInvocation = aFunction("Scala", 999999999)

  // 无参数函数
  def aNoArgFunction(): Int = 45
  // 无括号函数（参数列表为空时可以省略括号）
  def aParamterlessFunction: Int = 45

  // 在 Scala 里一般不推荐使用 for、while 等传统循环，
  // 而是推荐使用递归（recursion），尤其是尾递归（tail recursion）,等等会介绍

  //字串重复n次
  def stringConcatenation(str: String, n: Int): String =
    if (n == 0) "" // 递归终止条件 1
    else if (n == 1) str // 递归终止条件 2
    else str + stringConcatenation(str, n - 1) // 递归调用

  /*
    stringConcatenation("Scala", 3) 的执行过程：
    sc("Scala", 3) = "Scala" + sc("Scala", 2)
                   = "Scala" + ("Scala" + sc("Scala", 1))
                   = "Scala" + ("Scala" + "Scala")
                   = "ScalaScalaScala"
   */
  val scalax3 = stringConcatenation("Scala", 3)
  // 在 Scala 里，当你需要循环时，推荐使用递归

  // "void" 函数（在 Scala 中用 Unit 表示）
  def aVoidFunction(aString: String): Unit =
    println(aString) // 只执行副作用（打印），没有返回有意义的值

  // 带副作用的函数：一边做打印（副作用），一边返回值
  def computeDoubleStringWithSideEffect(aString: String): String = {
    aVoidFunction(aString) // 这里执行 println，但返回 Unit
    aString + aString // 这里才是真正的返回值
  }
  //scala 中尽量避免副作用，需要副作用时（如写文件、打印）则会明确标注或封装

  // 大函数可以拆分成小函数
  def aBigFunction(n: Int): Int = {
    // 内部定义的小函数（辅助函数）
    def aSmallerFunction(a: Int, b: Int): Int = a + b

    aSmallerFunction(n, n + 1)
  }

  /**
   * 练习：
   * 1. 实现一个问候函数 (name, age) => 返回 "Hi my name is $name and I am $age years old."
   * 2. 阶乘函数 factorial(n) => 1 * 2 * 3 * ... * n
   * 3. 斐波那契函数
   *    fib(1) = 1
   *    fib(2) = 1
   *    fib(3) = fib(2) + fib(1) = 2
   *    fib(n) = fib(n-1) + fib(n-2)
   *
   * 4. 判断一个数是否为质数（素数）
   */

  // 1. 问候函数
  def greetingForKids(name: String, age: Int): String = {
    "Hi, my name is " + name + " and I am " + age + " years old."
    s"Hi, my name is $name and I am $age years old."
  }

  // 2. 阶乘函数
  /*
    factorial(5) 的执行过程：
    f(5) = 5 * f(4) = 120
    f(4) = 4 * f(3) = 24
    f(3) = 3 * f(2) = 6
    f(2) = 2 * f(1) = 2
    f(1) = 1
   */
  def factorial(n: Int): Int =
    if (n <= 0) 0 // 阶乘的特殊情况：n<=0
    else if (n == 1) 1 // 递归终止条件
    else n * factorial(n - 1) // 递归调用

  // 3. 斐波那契函数
  /*
    fibonacci(5) 的执行过程：
    fib(5) = fib(4) + fib(3) = 5
    fib(4) = fib(3) + fib(2) = 3
    fib(3) = fib(2) + fib(1) = 2
    fib(2) = 1
    fib(1) = 1
   */
  def fibonacci(n: Int): Int =
    if (n <= 2) 1
    else fibonacci(n - 1) + fibonacci(n - 2)

  // 4. 判断质数函数
  /*
    isPrime(7) 的执行过程：
    isPrimeUntil(3) = 7 % 3 != 0 && isPrimeUntil(2)
    isPrimeUntil(2) = 7 % 2 != 0 && isPrimeUntil(1)
    isPrimeUntil(1) = true
    => 所以 7 是质数
   */
  def isPrime(n: Int): Boolean = {
    def isPrimeUntil(t: Int): Boolean =
      if (t <= 1) true // 递归终止条件
      else n % t != 0 && isPrimeUntil(t - 1)

    isPrimeUntil(n / 2)
  }

  def main(args: Array[String]): Unit = {
    println(greetingForKids("Lulu", 5))
    println(factorial(5)) // 120
    println(fibonacci(5)) // 5
    println(isPrime(7)) // true
  }
}

package com.rockthejvm.part1basics

object E_CBNvsCBV {

  // CBV（Call By Value）= 传值调用：参数会在函数调用之前就被计算好
  def aFunction(arg: Int): Int = arg + 1
  val aComputation = aFunction(23 + 67)
  // 传值：23 + 67 会先计算为 90，然后传入函数

  // CBN（Call By Name）= 传名调用：参数以“原始代码”的形式传入，每次使用都会重新计算
  def aByNameFunction(arg: => Int): Int = arg + 1
  val anotherComputation = aByNameFunction(23 + 67)
  // 传名：表达式 23 + 67 每次用到都重新执行一次

  // 传值调用：x 会先被求值（只计算一次）
  def printTwiceByValue(x: Long): Unit = {
    println("By value: " + x)
    println("By value: " + x)
    // 输出结果两次相同，因为 x 在调用时就已被计算
  }

  /*
    传名调用的主要特性：
    - 参数的求值被延迟，直到真正用到时才会计算
    - 每次用到参数，都会重新计算一次
   */
  def printTwiceByName(x: => Long): Unit = {
    println("By name: " + x)
    println("By name: " + x)
    // 输出结果可能不同，因为每次使用 x 都会重新计算
  }

  /*
    传名调用的另一个优点：
    - 如果参数没有被使用，就不会被计算
    - 可以避免不必要或有副作用的运算
   */
  def infinite(): Int = 1 + infinite()
  // 一个无限递归函数，会导致程序崩溃（栈溢出）

  def printFirst(x: Int, y: => Int) = println(x)
  // y 是传名参数，如果函数体没有使用 y，那么 y 就不会被求值

  def main(args: Array[String]): Unit = {
    printTwiceByValue(System.nanoTime())

    // 传值调用：System.nanoTime() 在函数调用之前执行 → 两次结果相同

    printTwiceByName(System.nanoTime())
    // 传名调用：System.nanoTime() 每次使用都重新执行 → 两次结果不同

    printFirst(42, infinite())
    // 没有崩溃，因为 infinite() 没有被用到

     printFirst(infinite(), 42)
    // ❌ 程序崩溃：infinite() 是传值参数，在调用前就会执行，导致无限递归
  }
}

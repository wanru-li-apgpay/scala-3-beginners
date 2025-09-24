package com.rockthejvm.part1basics

import scala.annotation.tailrec

object D_Recursion {

  // 使用递归实现
  def sumUntil(n: Int): Int =
    if (n <= 0) 0
    else n + sumUntil(n - 1) // 普通递归：每次调用都会消耗新的栈空间（stack frame）/記憶體堆疊

  def sumUntil_v2(n: Int): Int = {
    /*
      调用过程模拟：
      sumUntilTailrec(10, 0) =
      sumUntilTailrec(9, 10) =
      sumUntilTailrec(8, 10 + 9) =
      sumUntilTailrec(7, 10 + 9 + 8) =
      ...
      sumUntilTailrec(0, 1 + 2 + 3 + ... + 10)
      最终返回累加值
     */

 
    @tailrec    // @tailrec 编译器强制检查是否为尾递归,如果不是尾递归，会直接编译错误，帮助你发现问题
    def sumUntilTailrec(x: Int, accumulator: Int): Int =
      if (x <= 0) accumulator
      else sumUntilTailrec(x - 1, accumulator + x)
    // 尾递归：递归调用是函数中的最后一步，因此可以被优化为循环结构，不再消耗栈空间

    sumUntilTailrec(n, 0)
  }

  // 计算从 a 到 b 的所有整数之和（包含 a 和 b）
  def sumNumbersBetween(a: Int, b: Int): Int =
    if (a > b) 0
    else a + sumNumbersBetween(a + 1, b)

  // 上面函数的尾递归版本
  def sumNumbersBetween_v2(a: Int, b: Int): Int = {
    @tailrec
    def sumTailrec(currentNumber: Int, accumulator: Int): Int =
      if (currentNumber > b) accumulator
      else sumTailrec(currentNumber + 1, accumulator + currentNumber)

    sumTailrec(a, 0)
  }

  /**
   * 练习：
   * 1. 将一个字符串重复拼接 n 次
   * 2. 实现斐波那契函数，使用尾递归
   * 3. 判断 isPrime 函数是否是尾递归？
   */

  // 练习 1：重复拼接字符串 n 次
  def concatenate(string: String, n: Int): String = {
    @tailrec
    def concatTailrec(remainingTimes: Int, accumulator: String): String =
      if (remainingTimes <= 0) accumulator
      else concatTailrec(remainingTimes - 1, string + accumulator)

    concatTailrec(n, "")
  }

  // 练习 2：斐波那契数列（使用尾递归）
  def fibonacci(n: Int): Int = {
    // i 表示当前的索引，last 是 F(i-1)，previous 是 F(i-2)
    def fiboTailrec(i: Int, last: Int, previous: Int): Int =
      if (i >= n) last
      else fiboTailrec(i + 1, last + previous, last)

    // 前两个数字是 1
    if (n <= 2) 1
    else fiboTailrec(2, 1, 1)
  }

  // 练习 3：判断一个数是否为质数（不是尾递归）
  def isPrime(n: Int): Boolean = {
    def isPrimeUntil(t: Int): Boolean =
      if (t <= 1) true                  // 没有整除因子，说明是质数
      else if (n % t == 0) false       // 找到整除因子，说明不是质数
      else isPrimeUntil(t - 1)         // 继续检查下一个因子

    isPrimeUntil(n / 2)
    // ⚠️ 注意：这不是尾递归，因为递归调用不是最后一步，还包含了 if 判断
  }

  // 主方法：用于运行测试
  def main(args: Array[String]): Unit = {
    println(sumUntil_v2(20000))              // 计算 1 到 20000 的总和，测试尾递归
    println(concatenate("Scala", 5))         // 输出 ScalaScalaScalaScalaScala
    println(fibonacci(7))                    // 输出第 7 个斐波那契数（结果是 13）
  }
}

package com.rockthejvm.part3fp

import scala.annotation.tailrec

object B_HOFsCurrying {

  // -----------------------------
  // 高阶函数 (Higher-Order Functions, HOFs)
  // -----------------------------
  // 高阶函数指：接受函数作为参数，或返回一个函数作为结果的函数

  // 示例：接收一个 Int 和一个函数参数 (Int => Int)，返回 Int
  val aHof: (Int, (Int => Int)) => Int = (x, func) => x + 1

  // 示例：返回一个函数 (即函数的函数)
  // 输入 x，返回一个新的函数 (y => y + 2 * x)
  val anotherHof: Int => (Int => Int) = x => (y => y + 2 * x)

  // 快速练习（嵌套函数定义形式）
  val superfunction: (Int, (String, (Int => Boolean)) => Int) => (Int => Int) = {
    (x, func) => (y => x + y)
  }
  val super1 = superfunction(3,(_,_)=> 100)
  val super2 = super1(4)

  // -----------------------------
  // 常见高阶函数示例：map、flatMap、filter 等
  // -----------------------------
  // 它们都属于“接收函数作为参数”的典型代表。

  // -----------------------------
  // 递归示例：重复执行函数 n 次
  // -----------------------------
  // 函数 f 被重复应用 n 次：f(f(f(...(x)...)))
  @tailrec
  def nTimes(f: Int => Int, n: Int, x: Int): Int =
    if (n <= 0) x
    else nTimes(f, n - 1, f(x))

  // 示例函数：加 1
  val plusOne = (x: Int) => x + 1
  // 调用：对 0 重复加 1 共 10000 次，结果应为 10000
  val tenThousand = nTimes(plusOne, 10000, 0)

  /*
    📘 递归版本分析：
    nTimes_v2(po, 3) =
      (x: Int) => nTimes_v2(po, 2)(po(x))
                 = po(po(po(x)))

    nTimes_v2(po, 2) =
      (x: Int) => nTimes_v2(po, 1)(po(x))
                 = po(po(x))

    nTimes_v2(po, 1) =
      (x: Int) => nTimes_v2(po, 0)(po(x))
                 = po(x)

    nTimes_v2(po, 0) = (x: Int) => x
   */
  def nTimes_v2(f: Int => Int, n: Int): Int => Int =
    if (n <= 0) (x: Int) => x
    else (x: Int) => nTimes_v2(f, n - 1)(f(x))

  val plusOneHundred = nTimes_v2(plusOne, 100)
  val oneHundred = plusOneHundred(0)

  // -----------------------------
  // 柯里化 (Currying)
  // -----------------------------
  // 柯里化是一种函数转换方式：将一个多参数函数，转换为一连串接收单个参数的函数。
  // 例如：(x, y) => x + y   变为   x => (y => x + y)

  val superAdder: Int => Int => Int = (x: Int) => (y: Int) => x + y

  // 调用第一层：固定第一个参数 x = 3
  val add3: Int => Int = superAdder(3)
  // 调用第二层：再传入 y = 100，得到结果 103
  val invokeSuperAdder = superAdder(3)(100) // 输出 103

  // -----------------------------
  // 柯里化方法 (Curried Method)
  // -----------------------------
  // 方法也可以有多个参数列表。
  def curriedFormatter(fmt: String)(x: Double): String = fmt.format(x)

  // 应用第一个参数，生成一个新函数
  val standardFormat: (Double => String) = curriedFormatter("%4.2f")
  val preciseFormat: (Double => String) = curriedFormatter("%10.8f")

  // -----------------------------
  // 函数式编程练习题
  // -----------------------------
  /**
   * 1️⃣ LList 相关练习
   *    - foreach(A => Unit): Unit
   *      [1,2,3].foreach(x => println(x))
   *
   *    - sort((A, A) => Int): LList[A]
   *      [3,2,4,1].sort((x, y) => x - y) = [1,2,3,4]
   *      （提示：可使用插入排序 insertion sort）
   *
   *    - zipWith[B](LList[A], (A, A) => B): LList[B]
   *      [1,2,3].zipWith([4,5,6], x * y) => [4, 10, 18]
   *
   *    - foldLeft[B](start: B)((A, B) => B): B
   *       (x + y) = 10
   *      过程：
   *        0 + 1 = 1
   *        1 + 2 = 3
   *        3 + 3 = 6
   *        6 + 4 = 10
   *
   * 2️⃣ 函数柯里化转换
   *     toCurry(f: (Int, Int) => Int): Int => Int => Int
   *     fromCurry(f: (Int => Int => Int)): (Int, Int) => Int
   *
   * 3️⃣ 组合函数
   *     compose(f,g) => x => f(g(x))
   *     andThen(f,g) => x => g(f(x))
   */

  // -----------------------------
  // 函数柯里化转换
  // -----------------------------
  def toCurry[A, B, C](f: (A, B) => C): A => B => C =
    x => y => f(x, y)

  val superAdder_v2 = toCurry[Int, Int, Int](_ + _) // 等价于 superAdder

  def fromCurry[A, B, C](f: A => B => C): (A, B) => C =
    (x, y) => f(x)(y)

  val simpleAdder = fromCurry(superAdder)

  // -----------------------------
  // 函数组合 (Function Composition)
  // -----------------------------
  // compose(f, g): 先执行 g，再执行 f
  // andThen(f, g): 先执行 f，再执行 g
  def compose[A, B, C](f: B => C, g: A => B): A => C =
    x => f(g(x))

  def andThen[A, B, C](f: A => B, g: B => C): A => C =
    x => g(f(x))

  // 示例：定义两个简单函数
  val incrementer = (x: Int) => x + 1
  val doubler = (x: Int) => 2 * x

  // compose: f(g(x)) = incrementer(doubler(x))
  val composedApplication = compose(incrementer, doubler)

  // andThen: g(f(x)) = doubler(incrementer(x))
  val aSequencedApplication = andThen(incrementer, doubler)

  // -----------------------------
  // 主程序入口
  // -----------------------------
  def main(args: Array[String]): Unit = {
    println(tenThousand)              // 10000
    println(oneHundred)               // 100
    println(standardFormat(Math.PI))  // 格式化输出 π (两位小数)
    println(preciseFormat(Math.PI))   // 格式化输出 π (八位小数)
    println(simpleAdder(2, 78))       // 80
    println(composedApplication(14))  // 29 = 2*14 + 1
    println(aSequencedApplication(14)) // 30 = (14+1)*2
  }
}

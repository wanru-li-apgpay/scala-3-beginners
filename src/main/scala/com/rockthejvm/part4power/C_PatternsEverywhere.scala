package com.rockthejvm.part4power

object C_PatternsEverywhere {

  //  #1：catch 实际上也是模式匹配（MATCHES）
  val potentialFailure = try {
    // 这里放可能抛异常的代码
  } catch {
    case e: RuntimeException => "runtime ex"       // 捕获 RuntimeException 类型
    case npe: NullPointerException => "npe"       // 捕获 NullPointerException 类型
    case _ => "some other exception"              // 捕获其他所有异常
  }

  /*
    上面等价于：
    try { .. code }
    catch (e) {
      e match {
        case e: RuntimeException => "runtime ex"
        case npe: NullPointerException => "npe"
        case _ => "some other exception"
      }
    }
    说明 Scala 的 catch 本质就是对异常对象做模式匹配
  */

  // #2：for-comprehension 背后也是基于模式匹配
  val aList = List(1,2,3,4)
  val evenNumbers = for {
    n <- aList if n % 2 == 0           // 取出每个元素 n，并加上过滤条件 if
  } yield 10 * n                        // yield 生成一个新 List，元素是 10 * n

  val tuples = List((1,2), (3,4))
  val filterTuples = for {
    (first, second) <- tuples if first < 3  // 拆 tuple 并加条件
  } yield second * 100                        // 生成新 List，元素是 second * 100

  // #3：新的语法（Python-like 的解构语法）
  val aTuple = (1,2,3)
  val (a, b, c) = aTuple       // tuple 解构，把值直接赋给 a, b, c

  val head :: tail = tuples     // List 解构，把第一个元素赋给 head，其余赋给 tail

  def main(args: Array[String]): Unit = {
    // 可以在这里调用上面变量查看结果
  }
}

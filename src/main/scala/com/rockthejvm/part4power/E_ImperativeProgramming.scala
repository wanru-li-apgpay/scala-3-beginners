package com.rockthejvm.part4power

object E_ImperativeProgramming {

  val meaningOfLife: Int = 42 // val 是不可变变量，类似 C# 的 readonly

  // var 是可变变量，可以被重新赋值
  var aVariable = 99
  aVariable = 100 // vars 可以被重新赋值
  // aVariable = "Scala" // 类型不能改变，Scala 是强类型

  // 直接修改变量
  aVariable += 10 // 等价于 aVariable = aVariable + 10

  // Scala 没有 ++ 或 -- 操作符
  // aVariable++ // 在 Scala 中非法

  // ===================== 循环 =====================
  def testLoop(): Unit = {
    var i = 0
    while (i < 10) {                  // while 循环
      println(s"Counter at $i")       // 输出当前计数
      i += 1                           // 递增 i
    }
  }

  /*
    命令式编程（Imperative Programming）特点：
    - 使用循环、可变变量和可变数据
    - 不推荐在 Scala 中广泛使用：
      1. 代码可读性差，维护困难（尤其是大型代码）
      2. 容易出现并发问题（需要额外同步）

    适用场景：
    - 性能关键的应用（很少见，大约 0.1% 情况）
      （Akka / ZIO / Cats 已经非常高效）
    - 与 Java 库交互（Java 库大多是可变的）

    如果在没有理由的情况下使用命令式编程，会违背 Scala 的设计初衷
  */

  val anExpression: Unit = aVariable += 10   // 表达式也是值，返回 Unit（类似 void）
  val aLoop: Unit = while (aVariable < 130) {
    println("counting more")
    aVariable += 1
  }

  def main(args: Array[String]): Unit = {
    testLoop()
  }
}

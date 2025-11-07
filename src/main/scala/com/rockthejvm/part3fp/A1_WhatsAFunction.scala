package com.rockthejvm.part3fp

object A1_WhatsAFunction {

  // 🔹 在函数式编程（Functional Programming, FP）中：
  // “函数（Function）”是第一等公民（first-class citizen），
  // 意思是函数可以像数字、字符串或对象一样被存放在变量里、当作参数传入、或作为结果返回。

  // 🔹 但 JVM（Java 虚拟机）最初是为 Java 设计的，
  // 而 Java 是面向对象（OOP）的语言，
  // 在 OOP 里，对象（Object，也就是类的实例）才是“第一等公民”。

  // 🔹 Scala 的解决方式：
  // 透过“trait + apply 方法”这种设计，让函数也能像对象一样被操作。

  // 定义一个泛型 trait，用来表示一个“函数类型”
  trait MyFunction[A, B] {
    def apply(arg: A): B  // 接收一个类型为 A 的参数，返回类型为 B 的结果
  }

  // 定义一个具体的函数对象：把输入的数字乘以 2
  val doubler = new MyFunction[Int, Int] {
    override def apply(arg: Int) = arg * 2
  }

  val meaningOfLife = 42
  val meaningDoubled = doubler(meaningOfLife) // 实际上就是 doubler.apply(meaningOfLife)

  // 🔹 Scala 其实内建了 FunctionN 这样的接口 (Function1, Function2, Function3... Function22)
  //    - Function1[A, B] 代表接收一个参数 A，返回一个结果 B
  //    - Function2[A, B, C] 代表接收两个参数 A、B，返回一个结果 C
  //    - 以此类推，最多支持到 Function22

  // Function1 示例：一个输入 -> 一个输出
  val doublerStandard = new Function1[Int, Int] {
    override def apply(arg: Int) = arg * 2
  }
  val meaningDoubled_v2 = doublerStandard(meaningOfLife)

  // Function2 示例：两个输入 -> 一个输出
  val adder = new Function2[Int, Int, Int] {
    override def apply(a: Int, b: Int) = a + b
  }
  val anAddition = adder(2, 67)

  // Function4 示例：四个输入 -> 一个输出
  // 函数类型：(Int, String, Double, Boolean) => Int
  val aThreeArgFunction = new Function4[Int, String, Double, Boolean, Int] {
    override def apply(v1: Int, v2: String, v3: Double, v4: Boolean): Int = ???
  }

  // ✅ 重点：
  // Scala 里所有的“函数值”（function value）
  // 都是 FunctionN 这些 trait 的实例（instance），并且都实现了 apply 方法。

  // 🔹 补充概念说明：
  // function value vs. method
  // function value（函数值） = FunctionN 的实例，可以被当作值操作
  // method（方法） = 类里面定义的可调用成员，属于面向对象的概念
  // 二者虽然看起来相似，但本质上不同（方法不是对象，但函数值是）

  /**
   * 🧩 小练习
   * 1. 写一个函数，接收两个字符串参数，把它们连接在一起。
   * 2. Predicate / Transformer 类型可以用 Function 类型来替代吗？怎么替代？
   * 3. 定义一个函数，它接收一个 Int 参数，然后返回“另一个函数”。
   */

  // 练习 1：字符串拼接函数
  val concatenator = new Function2[String, String, String] {
    override def apply(a: String, b: String) = a + b
  }
  

  def main(args: Array[String]): Unit = {
    println(concatenator("I love ", "Scala")) // 输出: I love Scala
  }
}

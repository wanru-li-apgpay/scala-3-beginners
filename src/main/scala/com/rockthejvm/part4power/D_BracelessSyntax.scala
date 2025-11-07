package com.rockthejvm.part4power

//无花括号语法
object D_BracelessSyntax {

  // ===================== if 表达式 =====================
  // 基本 if 表达式，会返回一个值
  val anIfExpression = if (2 > 3) "bigger" else "smaller"

  // Java 风格（带大括号）
  val anIfExpression_v2 =
    if (2 > 3) {
      "bigger"
    } else {
      "smaller"
    }

  // 简化版（省略大括号）
  val anIfExpression_v3 =
    if (2 > 3) "bigger"
    else "smaller"

  // Scala 3 风格（无大括号，用 then / else + 缩进）
  val anIfExpression_v4 =
    if 2 > 3 then
      "bigger" // 缩进高于 if 行
    else
      "smaller"

  // Scala 3 多行 if 表达式
  val anIfExpression_v5 =
    if 2 > 3 then
      val result = "bigger"
      result
    else
      val result = "smaller"
      result

  // Scala 3 单行 if 表达式
  val anIfExpression_v6 = if 2 > 3 then "bigger" else "smaller"

  // ===================== for-comprehension =====================
  val aForComprehension = for {
    n <- List(1,2,3)
    s <- List("black", "white")
  } yield s"$n$s"

  // Scala 3 无花括号风格
  val aForComprehension_v2 =
    for
      n <- List(1,2,3)
      s <- List("black", "white")
    yield s"$n$s"

  // ===================== pattern matching =====================
  val meaningOfLife = 42
  val aPatternMatch = meaningOfLife match {
    case 1 => "the one"
    case 2 => "double or nothing"
    case _ => "something else"
  }

  // Scala 3 无花括号风格
  val aPatternMatch_v2 =
    meaningOfLife match
      case 1 => "the one"
      case 2 => "double or nothing"
      case _ => "something else"

  // ===================== 方法定义 =====================
  // 无大括号方法体，缩进表示方法体范围
  def computeMeaningOfLife(arg: Int): Int = // 缩进开始就是方法体
    val partialResult = 40
    partialResult + 2 // 仍属于方法体

  // ===================== 类定义 =====================
  // 缩进表示类体范围（适用于 class, trait, object, enum 等）
  class Animal: // 编译器期待类体
    def eat(): Unit =
      println("I'm eating")
    end eat

    def grow(): Unit =
      println("I'm growing")

    // 可以继续写更多代码...
  end Animal // 类结束（可选 end 标识，增强可读性）

  // ===================== 匿名类 =====================
  val aSpecialAnimal = new Animal:
    override def eat(): Unit = println("I'm special")

  // ===================== 缩进规则 =====================
  // 缩进必须严格大于上一层
  // 3 spaces + 2 tabs > 2 spaces + 2 tabs
  // 3 spaces + 2 tabs > 3 spaces + 1 tab
  // 3 tabs + 2 spaces ??? 2 tabs + 3 spaces

  def main(args: Array[String]): Unit = {
    println(computeMeaningOfLife(78))
  }
}

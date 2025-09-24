package com.rockthejvm.part1basics

object B_Expressions {

  // 表达式是可以被计算并返回一个值的结构
  val meaningOfLife = 40 + 2

  // 数学运算表达式: +, -, *, /, 位运算 |, &, <<, >>, >>>
  val mathExpression = 2 + 3 * 4

  // 比较运算表达式: <, <=, >, >=, ==, !=
  val equalityTest = 1 == 2

  // 布尔运算表达式: !, ||, &&
  val nonEqualityTest = !equalityTest

  // 在 Scala 中，一切都是表达式(expressions)
  // 指令（instructions）：“做某事”
  // 表达式（expressions）：“计算出一个值”


  // if 在 Scala 中也是一种表达式，可以返回一个值
  val aCondition = true
  val anIfExpression = if (aCondition) 45 else 99

  // 代码块（code block）
  val aCodeBlock = {
    // 局部变量
    val localValue = 78
    // 代码块里可以写多个表达式...

    // 最后一行表达式的结果，就是整个代码块的值
    localValue + 54
  }


  /**
   * 练习：
   * 不运行代码，试着思考下面这些值会打印出什么？
   */
  // 1
  val someValue = {
    2 < 3
  }

  // 2
  val someOtherValue = {
    if (someValue) 239 else 986
    42
  }

  // 3
  val yetAnotherValue = println("Scala")
  //  val yetAnotherValue2: Unit = println("Scala")
  //  val theUnit: Unit = () // Unit == 在其他语言里等同于 "void"
  //
  //  def yetAnotherValue3 = println("Scala")

  def main(args: Array[String]): Unit = {
    println(someValue)
    println(someOtherValue)
    println(yetAnotherValue)
    // println(yetAnotherValue3)

    //scala執行時,會先執行 object Expressions 中定義的所有頂層的 val
    // 所以在你執行 main() 前println("Scala")已經被執行了
    // 直接執行 println("Scala") 函數再把返回值（Unit，即 ()）指定給 yetAnotherValue
  }
}

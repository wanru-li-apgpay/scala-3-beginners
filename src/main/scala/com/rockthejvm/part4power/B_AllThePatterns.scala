package com.rockthejvm.part4power

import com.rockthejvm.practice.*

object B_AllThePatterns {

  // Scala 中 object 是单例（Singleton），类似于 C# 的 static class + 单例模式
  object MySingleton

  // 1 - 常量匹配（Constants Pattern）
  val someValue: Any = "Scala"
  val constants = someValue match {
    case 42 => "a number"            // 匹配整数常量
    case "Scala" => "THE Scala"      // 匹配字符串常量
    case true => "the truth"         // 匹配布尔值
    case MySingleton => "a singleton object" // 匹配单例对象
  }

  // 2 - 匹配任意值（Wildcard / Variable pattern）
  val matchAnythingVar = 2 + 3 match {
    // something 是变量名，会把任何值绑定到它上
    case something => s"I've matched anything, it's $something"
  }

  val matchAnything = someValue match {
    case _ => "I can match anything at all" // _ 表示「我不在乎具体值是什么」
  }

  // 3 - Tuple（元组）匹配
  val aTuple = (1,4)
  val matchTuple = aTuple match {
    case (1, somethingElse) => s"A tuple with 1 and $somethingElse"
    case (something, 2) => "A tuple with 2 as its second field"
  }

  // Tuple 模式可以嵌套（Nested）
  val nestedTuple = (1, (2, 3))
  val matchNestedTuple = nestedTuple match {
    case (_, (2, v)) => "A nested tuple ..."
  }

  // 4 - Case class 匹配（结构拆解）
  val aList: LList[Int] = Cons(1, Cons(2, Empty()))
  val matchList = aList match {
    case Empty() => "an empty list"
    case Cons(head, Cons(_, tail)) => s"a non-empty list starting with $head"
  }

  // Option 类型也支持模式匹配（Some / None）
  val anOption: Option[Int] = Option(2)
  val matchOption = anOption match {
    case None => "an empty option"
    case Some(value) => s"non-empty, got $value"
  }

  // 5 - List 专属匹配方式（标准 List）
  val aStandardList = List(1,2,3,42)
  val matchStandardList = aStandardList match {
    case List(1, _, _, _) => "list with 4 elements, first is 1" // 精确长度 + 第一个元素是 1
    case List(1, _*) => "list starting with 1"                  // 不限定长度，只要以 1 开头
    case List(1, 2, _) :+ 42 => "list ending in 42"             // 以 42 结尾
    case head :: tail => "deconstructed list"                   // :: 是 List 的解构符号（head + tail）
  }

  // 6 - 类型匹配（Type Specifier）
  val unknown: Any = 2
  val matchTyped = unknown match {
    case anInt: Int => s"I matched an int, I can add 2 to it: ${anInt + 2}"
    case aString: String => "I matched a String"
    case _: Double => "I matched a double I don't care about" // 只关心类型，不使用值
  }

  // 7 - 名称绑定（Name Binding）
  val bindingNames = aList match {
    // rest @ Cons(_, tail) 表示将匹配到的整个结构命名为 rest
    case Cons(head, rest @ Cons(_, tail)) => s"Can use $rest"
  }

  // 8 - 多重条件（复合模式，用 | 代表 or）
  val multiMatch = aList match {
    case Empty() | Cons(0, _) => "an empty list to me"
    case _ => "anything else"
  }

  // 9 - if 条件守卫（Pattern + if）
  val secondElementSpecial = aList match {
    case Cons(_, Cons(specialElement, _)) if specialElement > 5 => "second element is big enough"
    case _ => "anything else"
  }

  // ============================== Anti-Pattern (反例) ==============================

  val aSimpleInt = 45

  // 不推荐：使用 match 来判断布尔值 (冗长,不必要的 pattern matching 开销)
  val isEven_bad = aSimpleInt match {
    case n if n % 2 == 0 => true
    case _ => false
  }

  // 不推荐：if (condition) true else false
  val isEven_bad_v2 = if (aSimpleInt % 2 == 0) true else false

  // 最佳写法：条件表达式本身就是 Boolean
  val isEven = aSimpleInt % 2 == 0

  // ============================== 类型擦除陷阱（Tricky Example） ==============================
  val numbers: List[Int] = List(1,2,3,4)
  val numbersMatch = numbers match {
    case listOfStrings: List[String] => "a list of strings"
    case listOfInts: List[Int] => "a list of numbers"
  }

  /*
   编译器允许你写这个 match
   但在运行时，JVM 不知道元素是 Int 还是 String (在 JVM 层面，它们都只是 List，无法区分内部元素类型。)
   只要是 带泛型参数的类，运行时 JVM 不保留具体类型信息
   所以 pattern matching 永远会先匹配第一个 case
   这就是「类型擦除陷阱」
   */

  def main(args: Array[String]): Unit = {
    println(numbersMatch)
  }
}

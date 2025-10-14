package com.rockthejvm.part1basics

import scala.util.{Try, Success, Failure}


object A_ValuesAndTypes {

  // 定义一个常量（不可改变的值）
  val meaningOfLife: Int = 42 // 等价于c#里的 const int meaningOfLife = 42;

  // 不允许重新赋值
  //meaningOfLife = 45

  // 也可定义一个变数, 但在 Scala 的慣例中，建議盡可能使用 val 不建議使用var
  var variable: Int = 42
  variable = 43

  // 类型可以省略，Scala 会自动推断
  val anInteger = 67 // : Int is optional

  //<editor-fold> 常见的数据类型:
  val aBoolean: Boolean = false
  val aChar: Char = 'a'
  val anInt: Int = 78 // 4 bytes
  val aShort: Short = 5263 // 2 bytes
  val aLong: Long = 52789572389234L // 8 bytes
  val aFloat: Float = 123.3333333333333f // 4 bytes 只有 ~7 位十進位有效數字，9 位會不準
  val aDouble: Double = 123.3333333333333 // 8 bytes 大約 15–16 位十進位有效數字，但底層是二進位浮點
  val d1: Double = 0.1
  val d2: Double = 0.2
  val dSum: Double = d1 + d2
  val aDecimal = BigDecimal(123.123456789123123) // 可以表示任意精度的小数,运算速度比 Double 慢，但换来的是精准
  val b1 = BigDecimal("0.1")
  val b2 = BigDecimal("0.2")
  val bSum = b1 + b2
  // string
  val aString: String = "Scala"
  val unitValue: Unit = () // c# void

  // 常见的集合类型
  val listValue: List[Int] = List(1, 2, 3)
  val setValue: Set[String] = Set("Scala", "Java", "Python", "Scala") // 自動去重 = c# HashSet
  val mapValue: Map[String, Int] = Map("one" -> 1, "two" -> 2, "three" -> 3) // c# Dictionary
  val arrayValue: Array[Int] = Array(4, 5, 6)
  val tupleValue: (Int, String, Boolean) = (42, "Answer", true)
  //</editor-fold>

  //<editor-fold> scala 特有的

  // Option[T] 代表「可能有值，也可能沒有值」
  //
  // 在 Scala 中：
  //   Some(x) → 有值
  //   None    → 沒有值
  val optionValue: Option[String] = Some("I am here") // c# string? optionValue = "Scala";
  val noName: Option[String] = None // string? noName = null;
  val nullValue: String = null // 這樣也是可以但不建議

  def findUserName(id: Int): Option[String] = 
    if (id == 1) return Some("Alice")
    else None

  val name1 = findUserName(1).getOrElse("Unknown") // 輸出 "Alice"
  val name2 = findUserName(2).getOrElse("Unknown") // 輸出 "Unknown"

  // Either[A, B] 代表「要嘛是 A，要嘛是 B」
  //
  // 在 Scala 中：
  //   Left  → 通常代表錯誤或失敗 (A)
  //   Right → 通常代表成功或正確結果 (B)
  //
  // 👉 所以 Either[String, Int] 可以用來表示：
  //   失敗時 → 得到 String（錯誤訊息）
  //   成功時 → 得到 Int
  val eitherValue: Either[String, Int] = Right(42)

  def safeDivide(a: Int, b: Int): Either[String, Int] =
    if (b == 0) Left("除數不能是 0")
    else Right(a / b)

  val r1 = safeDivide(10, 2) // Right(5)
  val r2 = safeDivide(10, 0) // Left("除數不能是 0")

    r1 match {
      case Right(value) => println(s"結果: $value")
      case Left(err) => println(s"錯誤: $err")
    }

  // Try[T] 代表「嘗試運算，可能成功，也可能失敗」
  //
  // 在 Scala 中：
  //   Success(x) → 運算成功，得到結果
  //   Failure(ex) → 運算失敗，得到例外
  //
  // 👉 所以 Try[Int] 可以用來表示：
  //   成功時 → 得到 Int
  //   失敗時 → 得到 Exception
  val tryValue: Try[Int] = Try(10 / 2)

  // Try 範例：轉換字串為數字
  def parseInt(s: String): Try[Int] = Try(s.toInt)

  val t1 = parseInt("123") // Success(123)
  val t2 = parseInt("abc") // Failure(NumberFormatException)

    t1 match {
      case Success(v) => println(s"成功: $v")
      case Failure(ex) => println(s"失敗: ${ex.getMessage}")
    }

  // Nothing 型別說明
  //
  // 1. Nothing 是 Scala 型別階層的最底層 (bottom type)
  //    → 它是所有型別的子型別
  //
  // 2. Nothing 本身沒有任何值
  //    → 代表「程式不會有正常結果」
  //
  // 3. 常見用途：
  //    - 拋出例外 (throw new Exception)
  //    - 無窮遞迴
  //    - 幫助編譯器型別推斷
  //
  // 範例：
  //
  def fail(msg: String): Nothing =
    throw new RuntimeException(msg)
    
//    val x: String = fail("error!") // OK
//    val y: Int = fail("boom!")     // OK

//   差異：C# 沒有對應的 Nothing
//   - 在 C# 中 throw 只能被當成「特殊語法」
   //- 在 Scala 中 throw 的型別是 Nothing
   val nothingValue: Nothing = throw new RuntimeException("Nothing value")
  //</editor-fold>

  def main(args: Array[String]): Unit = {
    println("Float:" + aFloat)
    println("Double:" + aDouble)
    println("Double Sum:" + dSum)
    println("Decimal:" + aDecimal)
    println("Decimal Sum: " + bSum)
    println("Set: " + setValue)

    println("Option1: " + name1)
    println("Option2: " + name2)
    println("Either1: " + r1)
    println("Either2: " + r2)

  }
}

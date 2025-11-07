package com.rockthejvm.part4power

import scala.util.Random

object A_PatternMatching {

  // ================== 基本模式匹配（類似 C# switch，但更強大） ==================
  val random = new Random()
  val aValue = random.nextInt(100) // 產生 0~99 的隨機整數

  // Scala 的 match 表達式類似 C# 的 switch，但功能更強、可匹配類型、結構等
  val description = aValue match {
    case 1 => "the first"            // 如果 aValue == 1
    case 2 => "the second"           // 如果 == 2
    case 3 => "the third"            // 如果 == 3
    case _ => s"something else: $aValue" // '_' 表示通配符，相當於 default
  }

  // ================== 解構（Decompose）case class ==================
  // case class 天生支援模式匹配與 immutability（不可變）
  case class Person(name: String, age: Int)
  val bob = Person("Bob", 16)

  // 模式匹配 + 條件守衛（if）
  val greeting = bob match {
    // 這裡自動將 bob 解構為 name 和 age => n, a
    case Person(n, a) if a < 18 => s"Hi, my name is $n and I'm $a years old."
    case Person(n, a) => s"Hello there, my name is $n and I'm not allowed to say my age."
    case _ => "I don't know who I am." // 理論上不會進來（因為 Person 是 case class）
  }

  // class 沒辦法直接用模式匹配
//  class Person1(n:String,a:Int)
//  val p1 = Person1("b",10)
//  val m1 = p1 match {
//    case Person1(n, a) if a < 18 => s"Hi, my name is $n and I'm $a years old."
//    case Person1(n, a) => s"Hello there, my name is $n and I'm not allowed to say my age."
//    case _ => "I don't know who I am."
//  }

  //要支援match 就要手動定義一個 companion object 並自己寫 unapply
//  object Person1 {
//    def unapply(p: Person): Option[(String, Int)] =
//      Some((p.name, p.age))
//  }

  /*
    ⚠️ 重點注意：
    1. 模式是從上往下比對的（first match wins）
    2. 如果沒有任何 case 匹配成功 => 會丟 MatchError
    3. match 表達式是有返回值的，返回類型是所有分支的「共同父類型」
   */

  // ================== sealed class + 模式匹配 ==================
  // sealed 表示此類型只能在本文件中被繼承，這樣編譯器能檢查是否有漏寫情況
  sealed class Animal
  case class Dog(breed: String) extends Animal
  case class Cat(meowStyle: String) extends Animal

  val anAnimal: Animal = Dog("Terra Nova")
  val animalPM = anAnimal match {
    case Dog(someBreed) => "I've detected a dog"
    case Cat(meow) => "I've detected a cat"
    // 因為 Animal 是 sealed + 只有 Dog/Cat 子類，所以理論上不用寫 default case
  }

  // ================== 練習：實作簡單的表示式（Expression Tree）+ show 方法 ==================
  sealed trait Expr // trait 類似 C# interface + 可以有實作
  case class Number(n: Int) extends Expr      // 表示常數數字
  case class Sum(e1: Expr, e2: Expr) extends Expr // 加法
  case class Prod(e1: Expr, e2: Expr) extends Expr // 乘法

  // 將表達式樹轉成可讀字串，類似 C# 中 override ToString() 但更彈性
  def show(expr: Expr): String = expr match {
    case Number(n) => s"$n" // 數字直接印
    case Sum(left, right) => show(left) + " + " + show(right) // 左 + 右
    case Prod(left, right) =>
      // 若是乘法，遇到加法要加括號，保持數學運算優先順序
      def maybeShowParentheses(exp: Expr): String = exp match {
        case Prod(_, _) => show(exp)              // 乘法不需要括號
        case Number(_) => show(exp)               // 單一數字也不用括號
        case Sum(_, _) => s"(${show(exp)})"       // 加法要加括號
      }

      maybeShowParentheses(left) + " * " + maybeShowParentheses(right)
  }

  def main(args: Array[String]): Unit = {
    println(description)  // 隨機值的描述
    println(greeting)     // bob 的問候語

    // 測試 show 函數
    println(show(Sum(Number(2), Number(3)))) // 2 + 3
    println(show(Sum(Sum(Number(2), Number(3)), Number(4)))) // 2 + 3 + 4
    println(show(Prod(Sum(Number(2), Number(3)), Number(4)))) // (2 + 3) * 4
    println(show(Sum(Prod(Number(2), Number(3)), Number(4)))) // 2 * 3 + 4
  }
}

package com.rockthejvm.part2oop  // 定义包名

// 允许使用后缀调用（例如 person isAlive 这种写法）
import scala.language.postfixOps

object B_MethodNotations {

  // 定义一个类 Person，包含 name、age 以及最喜欢的电影
  class Person(val name: String, val age: Int, favoriteMovie: String) {

    // ===============================
    // 中缀表示法（infix notation）
    // ===============================
    // 定义一个方法 likes，用于判断输入的电影是否是此人最喜欢的电影
    def likes(movie: String): Boolean = {
      movie == favoriteMovie // 返回布尔值，是否相等
    }

    //infix 只是作為 語法提示，告訴編譯器這個方法推薦用中缀形式，也可以在生成文檔時標註。但即使不加 infix，仍然可以使用中缀表示法，只要方法符合「單參數」條件。

    // 定义 + 方法（重载版本1）：接收另一个 Person，返回字符串描述两人一起出去玩
    infix def +(person: Person): String =
      s"${this.name} is hanging out with ${person.name}"

    // 定义 + 方法（重载版本2）：接收昵称字符串，返回一个新的 Person 对象，名字加上昵称
    infix def +(nickname: String): Person =
      new Person(s"$name ($nickname)", age, favoriteMovie)

    // 定义一个 “!!” 运算符方法，接收编程语言字符串，返回趣味文字
    infix def !!(progLanguage: String): String =
      s"$name wonders how can $progLanguage be so cool!"

    // ===============================
    // 前缀表示法（prefix notation）
    // ===============================
    // Scala 只支持四种一元前缀运算符： -, +, ~, !
    // 定义 unary_- ：当使用 -person 时触发
    def unary_- : String =
      s"$name's alter ego"

    // 定义 unary_+ ：当使用 +person 时触发，返回一个新 Person，年龄 +1
    def unary_+ : Person =
      new Person(name, age + 1, favoriteMovie)

    // ===============================
    // 后缀表示法（postfix notation）
    // ===============================
    // 定义一个简单的方法 isAlive，返回 true
    def isAlive: Boolean = true

    // ===============================
    // apply 方法（特殊语法）
    // ===============================
    // 无参版本：当我们写 person() 时，会自动调用这个方法
    def apply(): String =
      s"Hi, my name is $name and I really enjoy $favoriteMovie"

    // 带参数版本：当我们写 person(2) 时，会调用这个版本
    def apply(n: Int): String =
      s"$name watched $favoriteMovie $n times"
  }



  // 定义一个普通整数，演示负号运算
  val negativeOne = -1
  
  // 主程序入口
  def main(args: Array[String]): Unit = {

    // 创建两个 Person 实例
    val mary = new Person("Mary", 34, "Inception")
    val john = new Person("John", 36, "Fight Club")

    // 调用普通方法
    println(s"mary.likes :" +mary.likes("Fight Club"))
    // 中缀表示法（infix）：当方法只有一个参数时，可以省略点号和括号
    println(s"mary likes :" + (mary likes "Fight Club")) // 等价写法

    // “操作符”其实就是普通方法的另一种调用方式
    println(s"mary + john :" + (mary + john))     // 调用 mary.+(john)
    println(s"mary.+(john) :" + mary.+(john))    // 等价写法

    val mary2= mary + "the rockstar"
    println(s"mary2 ${mary2.name} ${mary2.age}") // nickname
    println("2 + 3 =" + (2 + 3))           // 调用 2.+(3)
    println("2.+(3) = " +2.+(3))          // 完全相同
    println("maru !! " + (mary !! "Scala")) // 调用 mary.!!("Scala")

    // ===============================
    // 前缀表示法（prefix）
    // ===============================
    println(s"-mary ${(-mary)}" ) // 实际上是调用 mary.unary_-
    val mary3 = +mary
    println(s"+mary ${mary3.name} ${mary3.age}" )

    // ===============================
    // 后缀表示法（postfix）
    // ===============================
    println(s"mary.isAlive ${mary.isAlive}") // 普通调用
    println(s"mary.isAlive ${mary isAlive}") // 后缀形式（已不推荐使用，但语法允许）

    // ===============================
    // apply 特殊方法
    // ===============================
    println(s"mary.apply ${mary.apply()}") // 普通调用
    println(s"mary() ${mary()}")       // 等价写法，更常用（Scala 的语法糖）
    // 调用 apply(Int) 方法
    println(mary(10)) // 输出：Mary watched Inception 10 times
    
    }
}

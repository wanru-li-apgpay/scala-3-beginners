package com.rockthejvm.part2oop

object J_CaseClasses {

  // ============================================
  // 🎯 样例类（Case Class）
  // - 是 Scala 中用于表示“轻量级数据结构”的特殊类。
  // - 它自动生成很多有用的方法（如 equals、hashCode、toString、copy 等）
  // - 常用于不可变（immutable）的数据模型、模式匹配 (pattern matching) 等场景。
  // ============================================

  // 定义一个样例类：Person，有两个字段 name 和 age
  case class Person(name: String, age: Int) {
    // 也可以在样例类中定义其他方法或逻辑
  }

  // 1️⃣ 样例类的主构造参数自动成为字段（fields）
  val daniel = new Person("Daniel", 99) // 可使用 new，但通常可以省略
  val danielsAge = daniel.age           // 可以直接访问字段（Scala 自动生成 getter）

  // 2️⃣ 样例类自动生成：
  // - toString() 方法（打印更易读）
  // - equals() 和 hashCode()（按内容比较，而不是引用）
  val danielToString = daniel.toString // 输出: Person(Daniel,99)
  val danielDuped = new Person("Daniel", 99)
  val isSameDaniel = daniel == danielDuped // true，因为样例类比较字段值，而非引用

  // 3️⃣ copy() 方法：可创建“修改部分字段”的新对象
  val danielYounger = daniel.copy(age = 78) // 创建新对象: Person("Daniel", 78)

  // 4️⃣ 样例类自动生成伴生对象 (companion object)
  // - 伴生对象中包含一个 apply() 方法，可省略 new 来构造实例
  val thePersonSingleton = Person // singleton 对象
  val daniel_v2 = Person.apply("Daniel", 77) // 等价于 new Person("Daniel", 99)
  val daniel_v3 = Person("Daniel", 99) // 等价于 new Person("Daniel", 99)
  
  
  // 5️⃣ 样例类可用于 “模式匹配 (pattern matching)”
  // - 可直接在 match 表达式中解构对象
  //   例如：
  //   daniel match {
  //     case Person(name, age) => println(s"$name is $age years old")
  //   }

  // ---------------------------------------------
  // ⚠️ 注意：
  // 样例类不能定义为没有参数列表的形式，
  // 因为这样所有实例都会相等（无差别）。
  // ---------------------------------------------

//    case class CCWithNoArgs {
//      // some code
//    }
//
//    val ccna = new CCWithNoArgs
//    val ccna_v2 = new CCWithNoArgs // 所有实例都相等，不合理！
//  

  // ✅ 如果真的需要“无参样例类”，可用 `case object`（样例对象）
  case object UnitedKingdom {
    // 可以有字段和方法
    def name: String = "The UK of GB and NI"
  }

  // ✅ 也可以定义带泛型参数的空参数样例类
  case class CCWithArgListNoArgs[A]() // 通常用于泛型场景

  /**
   * 💪 练习：
   * 将你之前的 LList（链表类）改写成 case class。
   * 提示：使用 case class 可以让模式匹配非常方便。
   */

  def main(args: Array[String]): Unit = {
    println(daniel)        // Person(Daniel,99)
    println(isSameDaniel)  // true
    println(danielYounger) // Person(Daniel,78)
    println(UnitedKingdom.name) // The UK of GB and NI
  }
}

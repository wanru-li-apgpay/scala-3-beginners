package com.rockthejvm.part2oop

// ===============================
// 第五章：防止继承 (Preventing Inheritance)
// ===============================
object E_PreventingInheritance {

  // -----------------------------------
  // 1️⃣ final 成员：防止被子类覆写（override）
  // -----------------------------------
  class Person(name: String) {

    // 使用 final 修饰方法，表示此方法不能在子类中被覆写
    final def enjoyLife(): Int = 42 // 42：生命的意义 😄
  }

  class Adult(name: String) extends Person(name) {
    // ❌ 下面的代码会编译错误，因为 enjoyLife 是 final 方法，不能被 override
    // override def enjoyLife() = 999
  }

  // -----------------------------------
  // 2️⃣ final 类：防止被继承（extends）
  // -----------------------------------
  final class Animal
  // ❌ 以下会编译错误：不能继承 final 类
  // class Cat extends Animal

  // -----------------------------------
  // 3️⃣ sealed 类：允许“受控继承”
  // -----------------------------------
  // sealed 表示该类的继承只能出现在“同一个文件中”
  // ✅ 文件外无法再扩展 Guitar
  sealed class Guitar(nStrings: Int)

  // 这些子类都定义在同一文件中 → 合法
  class ElectricGuitar(nStrings: Int) extends Guitar(nStrings)
  class AcousticGuitar extends Guitar(6)

  // sealed 类的常见用途：
  // - 通常与 “模式匹配 (pattern matching)” 一起使用
  // - Scala 编译器能知道所有可能的子类型，确保匹配完整性

  // -----------------------------------
  // 4️⃣ open 类（Scala 3 特有）
  // -----------------------------------
  // 默认情况下，Scala 不鼓励随意继承（与 Java 相反）
  // 如果希望类可以明确地被继承，应使用 `open` 关键字标记
  // 这是一种设计上的“意图声明”，告诉使用者这个类是可扩展的
  open class ExtensibleGuitar(nStrings: Int)

  // （open 类只是允许扩展，不是强制。）
  // 若一个类没有标记为 open，但被继承，也不会编译错误，

  
  def main(args: Array[String]): Unit = {
    val person = new Person("Alice")
    println(person.enjoyLife()) // 输出 42
  }
}

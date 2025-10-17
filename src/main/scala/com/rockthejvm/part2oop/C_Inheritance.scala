package com.rockthejvm.part2oop  // 定义包名

object C_Inheritance {

  // ===============================
  // 定义一个父类 Animal（动物类）
  // ===============================
  class Animal {
    val creatureType = "wild" // 字段：动物的类型，默认是“野生的”
    def eat(): Unit = println("nomnomnom") // 方法：吃东西时输出“nomnomnom”
  }

  // ===============================
  // 定义一个子类 Cat，继承自 Animal
  // ===============================
  class Cat extends Animal { // 表示“猫是一种动物”（is-an relationship）
    def crunch() = {
      eat() // 可以直接调用父类的 eat 方法（继承而来）
      println("crunch, crunch") // 输出额外信息
    }
  }

  // 创建一个 Cat 实例
  val cat = new Cat
  cat.eat()
  cat.crunch()
  // ===============================
  // 演示构造函数继承
  // ===============================
  class Person(val name: String, age: Int) { // 定义一个 Person 类，有名字和年龄
    // 辅助构造函数（用于只传入名字时，默认年龄为0）
    def this(name: String) = this(name, 0)
  }

  // Adult 类继承 Person
  // 注意：子类构造时必须指定调用哪个父类构造函数（这里调用 Person(name)）
  class Adult(name: String, age: Int, idCard: String) extends Person(name)

  // ===============================
  // 方法重写（Overriding）
  // ===============================
  class Dog extends Animal {
    // 使用 override 关键字重写父类的字段
    override val creatureType = "domestic" // 家养的
    // 重写父类的 eat 方法
    override def eat(): Unit = println("mmm, I like this bone")

    // 重写 toString 方法（所有类都默认继承自 AnyRef 的 toString）
    override def toString: String = "a dog"
  }

  // ===============================
  // 子类型多态（Subtype Polymorphism）
  // ===============================
  val dog: Animal = new Dog // 右边是 Dog 类型，但左边是 Animal 类型（父类引用指向子类对象）
  dog.eat() // 实际调用的是 Dog 中的 eat() 方法（动态绑定，运行时多态）

  // ===============================
  // 方法重载（Overloading） vs 方法重写（Overriding）
  // ===============================
  class Crocodile extends Animal {
    // 重写字段和方法
    override val creatureType = "very wild"
    override def eat(): Unit = println("I can eat anything, I'm a croc")

    // -------------------------------
    // 方法重载（Overloading）：
    // 多个方法拥有相同的名字，但参数列表不同
    // -------------------------------
    // 参数列表不同的几种方式：
    // 1. 参数个数不同
    // 2. 参数类型不同
    // 3. 返回类型不同（不常用）
    def eat(animal: Animal): Unit = println("I'm eating this poor fella")
    def eat(dog: Dog): Unit = println("eating a dog")
    def eat(person: Person): Unit = println(s"I'm eating a human with the name ${person.name}")
    def eat(person: Person, dog: Dog): Unit = println("I'm eating a human AND the dog")
    // def eat(): Int = 45 // ❌ 无效重载（与上一个 eat() 的返回类型不同，但参数列表相同）
    def eat(dog: Dog, person: Person): Unit = println("I'm eating a human AND the dog")
  }

  // ===============================
  // 主程序入口
  // ===============================
  def main(args: Array[String]): Unit = {
    // println 会自动调用对象的 toString 方法
    println(dog) // 实际调用 Dog 类中重写的 toString 方法，输出 "a dog"
  }
}

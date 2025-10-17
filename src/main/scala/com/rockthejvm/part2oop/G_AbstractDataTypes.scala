package com.rockthejvm.part2oop  // 定义包名

// ===============================
// 第七章：抽象数据类型（Abstract Data Types）
// ===============================
object G_AbstractDataTypes {

  // ===============================
  // 1️⃣ 抽象类（Abstract Class）
  // ===============================
  abstract class Animal {
    val creatureType: String       // 抽象字段（abstract），子类必须实现
    def eat(): Unit                // 抽象方法，子类必须实现

    // 非抽象字段/方法也是允许的
    def preferredMeal: String = "anything" // "accessor method"，可以被重写
  }

  // 抽象类不能直接实例化
  // val anAnimal: Animal = new Animal // ❌ 编译错误

  // ===============================
  // 2️⃣ 继承抽象类并实现抽象成员
  // ===============================
  class Dog extends Animal {
    override val creatureType = "domestic"               // 实现抽象字段
    override def eat(): Unit = println("crunching this bone") // 实现抽象方法
    // 覆写非抽象字段/方法也是合法的
    override val preferredMeal: String = "bones"        // 重写 accessor 方法
  }

  val aDog: Animal = new Dog // 可以把子类实例赋值给抽象类类型

  // ===============================
  // 3️⃣ 特质（Traits）
  // 類似c# interface, 但可以有部分實作
  // ===============================
  //食肉动物
  trait Carnivore { // Scala 3 特质可以带构造参数
    def eat(animal: Animal): Unit // 抽象方法
  }

  class Shark extends Carnivore {
    override def eat(animal: Animal): Unit =
      println("I'm a shark, I eat animals")
  }

  // ===============================
  // 4️⃣ 抽象类 vs 特质
  // ===============================
  // 抽象类：
  //   - 通常表示“事物”（THINGS）
  //   - 单继承
  // 特质：
  //   - 通常表示“行为”（BEHAVIORS）
  //   - 多继承（可以混入多个 trait）

  trait ColdBlooded

  // 多重继承示例
  class Crocodile extends Animal with Carnivore with ColdBlooded {
    override val creatureType = "croc"
    override def eat(): Unit = println("I'm a croc, I just crunch stuff")  // Animal 抽象方法实现
    override def eat(animal: Animal): Unit = println("croc eating animal") // Carnivore 方法实现
  }

  // ===============================
  // 5️⃣ Scala 类型体系概览
  // ===============================
  /*
    Any
      ├─ AnyRef        所有引用类型（类）继承自 AnyRef
      │    └─ scala.Null  null 类型
      └─ AnyVal        所有值类型（Int, Boolean, Char...）继承自 AnyVal

    scala.Nothing       所有类型的子类型，用于异常、空集合等
  */

  val aNonExistentAnimal: Animal = null // AnyRef 类型可以赋值 null
  val anInt: Int = throw new NullPointerException // Nothing 可以赋值给任意类型

  // ===============================
  // 6️⃣ 主函数
  // ===============================
  def main(args: Array[String]): Unit = {
    val dog = new Dog
    val shark = new Shark
    val croc = new Crocodile

    dog.eat()           // 输出：crunching this bone
    println(dog.preferredMeal) // 输出：bones

    shark.eat(dog)       // 输出：I'm a shark, I eat animals
    croc.eat()           // 输出：I'm a croc, I just crunch stuff
    croc.eat(dog)        // 输出：croc eating animal
  }
}

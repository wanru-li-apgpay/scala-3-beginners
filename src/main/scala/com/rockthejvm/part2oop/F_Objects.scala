package com.rockthejvm.part2oop // 定义包名

object F_Objects {

  // ===============================
  // 1️⃣ 单例对象（Singleton Object）
  // ===============================
  object MySingleton {
    // object = type + 这个 type 的唯一实例
    val aField = 45 // 字段（静态，类级别）

    def aMethod(x: Int) = x + 1 // 方法（静态，类级别）
  }

  // 对象引用：所有引用都指向同一个实例
  val theSingleton = MySingleton
  val anotherSingleton = MySingleton
  val isSameSingleton = theSingleton == anotherSingleton // true，都是同一个实例

  // 访问对象的字段和方法（类似 Java 的 static）
  val theSingletonField = MySingleton.aField // 45
  val theSingletonMethodCall = MySingleton.aMethod(99) // 100


  // ===============================
  // 3️⃣ 伴生对象（Companion Object）
  // object 可用來作為類的靜態方法或字段,工廠方法
  // 伴生对象 = 类的“静态伙伴”
  // ===============================

  // ===============================
  class Person(val name: String, private val secret: String) {
    // 类中的方法和字段是**实例相关的**（每个对象都有自己的副本）
    def sayHi(): String = s"Hi, my name is $name"

    // 类可以提供访问自身 private 成员的方法
    def revealMySecret(): String = secret

    // 类可以访问伴生对象的 private 成员
    def revealCompanionSecret(): String = {
      s"My companion's secret is: ${Person.secretInfo}"
    }

    override def equals(obj: Any): Boolean = obj match {
      case that: Person => this.name == that.name
      case _ => false
    }
  }

  // 定义与类同名的 object，称为 companion object
  object Person {
    // companion object 可以访问类的 private 字段/方法
    val N_EYES = 2

    def canFly(): Boolean = false

    // 伴生对象的 private 字段
    private val secretInfo = "Scala is awesome!"

    // 伴生对象可以访问类的 private 字段
    def showPersonSecret(p: Person): String = {
      s"${p.name} has a secret: ${p.secret}"
    }
  }

  // ===============================
  // 4️⃣ 类的实例 vs 单例对象
  // ===============================
  val mary = new Person("Mary", "secret") // 实例化类
  val mary_v2 = new Person("Mary", "secret") // 新的实例
  val marysGreeting = mary.sayHi() // 调用实例方法
  mary == mary // true：自身比较

  // 调用伴生对象的方法/字段（静态功能）
  val humansCanFly = Person.canFly() // false
  val nEyesHuman = Person.N_EYES // 2

  // ===============================
  // 5️⃣ 相等性（Equality）
  // ===============================

  // 5.1 - 引用相等（reference equality）
  // eq 只比较地址，不比较内容
  val sameMary = mary eq mary_v2 // false，不是同一个对象
  val sameSingleton = MySingleton eq MySingleton // true，同一个对象
  println("Mary eq Mary_v2: " + sameMary)

  // 5.2 - 值相等 / “内容相等” (sameness)
  val sameMary_v2 = mary equals mary_v2 // false
  val sameMary_v3 = mary == mary_v2 // Scala 中 == 会调用 equals → false
  val sameSingleton_v2 = MySingleton == MySingleton // true
  println("Mary equals Mary_v2: " + sameMary_v2)
  println("Mary == Mary_v2: " + sameMary_v3)

  // ===============================
  // 6️⃣ 对象也可以继承类
  // ===============================
  object BigFoot extends Person("Big Foot", "secret") // 单例对象继承 Person 类
  // 可以直接使用 BigFoot.sayHi()

  // ===============================
  // 7️⃣ Scala 应用程序入口
  // ===============================
  /*
    Scala 应用程序写法：
      object Objects {
        def main(args: Array[String]): Unit = { ... }
      }

    对应 Java 写法：
      public class Objects {
        public static void main(String[] args) { ... }
      }
  */
  def main(args: Array[String]): Unit = {
    println(sameMary_v3)
  }
}

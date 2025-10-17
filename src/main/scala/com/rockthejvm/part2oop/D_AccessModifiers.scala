package com.rockthejvm.part2oop  // 定义包名

// 定义一个单例对象 D_AccessModifiers，用于演示访问修饰符（Access Modifiers）
object D_AccessModifiers {

  // ===============================
  // 定义一个类 Person（人类）
  // ===============================
  class Person(val name: String) {

    // protected 表示：
    // 1️⃣ 该方法只能在当前类（Person）及其子类中访问
    // 2️⃣ 不能被外部对象直接调用
    protected def sayHi(): String = s"Hi, my name is $name."

    // private 表示：
    // 1️⃣ 该方法只能在当前类（Person）内部访问
    // 2️⃣ 即使是子类，也无法访问
    private def watchNetflix(): String = "I'm binge watching my favorite series..."
  }

  // ===============================
  // 定义一个子类 Kid（小孩）
  // ===============================
  class Kid(override val name: String, age: Int) extends Person(name) {
    // 注意：此处 override 表示重写父类构造参数 name

    // greetPolitely() 方法没有任何修饰符 → 默认是 public（公开的）
    def greetPolitely(): String =
      // 子类可以访问父类的 protected 方法 sayHi()
      sayHi() + "I love to play!"
  }

  // 创建 Person 实例
  val aPerson = new Person("Alice")
  // 创建 Kid 实例
  val aKid = new Kid("David", 5)

  // ===============================
  // 子类中的访问限制示例
  // ===============================
  class KidWithParents(
                        override val name: String,
                        age: Int,
                        momName: String,
                        dadName: String
                      ) extends Person(name) {

    // 创建两个 Person 类型的对象，分别代表父母
    val mom = new Person(momName)
    val dad = new Person(dadName)

    // ❌ 无法调用其他实例的 protected 方法 sayHi()
    //    因为 protected 只允许在「当前对象自身」或「子类中调用 this.sayHi()」
    //    不能对其他 Person 实例调用 mom.sayHi() 或 dad.sayHi()

    // 示例（编译错误）：
     def everyoneSayHi(): String =
       this.sayHi() //+ s"Hi, I'm $name, and here are my parents: " + mom.sayHi() + dad.sayHi()
  }

  // ===============================
  // 主程序入口
  // ===============================
  def main(args: Array[String]): Unit = {
    // 调用 Kid 中的公开方法 greetPolitely()
    println(aKid.greetPolitely()) // 输出：Hi, my name is David.I love to play!
  }
}

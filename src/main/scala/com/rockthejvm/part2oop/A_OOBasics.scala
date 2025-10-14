package com.rockthejvm.part2oop  // 定义包名，表示当前文件所在的命名空间

// 定义一个名为 OOBasics 的单例对象（object 相当于 Java 中的静态类）
object OOBasics {

  // =============================
  // 定义一个类 Person（人类）
  // =============================
  class Person(val name: String, age: Int) { // 主构造函数，接收两个参数 name 和 age
    // 注意：name 前面有 val，代表这是一个可在外部访问的“字段”，而 age 只是普通参数（外部无法直接访问）

    // 字段定义：将 name 全部转为大写并存储在 allCaps 中
    val allCaps = name.toUpperCase()

    // 方法定义：接收一个参数 name（这里是局部变量，和字段 name 不同）
    // this.name 表示当前对象的 name 属性
    def greet(name: String): String =
      s"${this.name} says: Hi, $name"

    // 方法重载（Overloading）：相同方法名但不同参数列表
    def greet(): String =
      s"Hi, everyone, my name is $name"

    // 辅助构造函数（Auxiliary constructor）
    // 调用主构造函数并默认 age = 0
    def this(name: String) =
      this(name, 0)

    // 第二个辅助构造函数
    // 调用上一个构造函数，默认 name = "Jane Doe"
    def this() =
      this("Jane Doe")
  }

  // 创建一个 Person 实例，名为 John，年龄 26
  val aPerson: Person = new Person("John", 26)
  // 访问 aPerson 的 name 字段
  val john = aPerson.name
  // 访问大写版本
  val johnAllCaps = aPerson.allCaps
  // 调用 greet 方法（带参数）
  val johnSayHiToDaniel = aPerson.greet("Daniel")
  // 调用 greet 方法（无参数）
  val johnSaysHi = aPerson.greet()
  // 使用辅助构造函数创建一个默认 Person
  val genericPerson = new Person()


  /**
   * 练习：假设我们要为一个图书出版社创建后端系统
   * 我们要创建 Writer（作家）类 和 Novel（小说）类
   *
   * Writer: 包含 名字、姓氏、出生年份
   *   - 方法 fullName：返回全名
   *
   * Novel: 包含 书名、出版年份、作者
   *   - 方法 authorAge：出版时作者的年龄
   *   - 方法 isWrittenBy(author)：判断是否由指定作者写的
   *   - 方法 copy(newYear)：复制一本新出版年份的小说
   */

    
  def main(args: Array[String]): Unit = {
    println("genericPerson " + genericPerson.name)

    // 创建两个 Writer（作家）对象
    val charlesDickens = new Writer("Charles", "Dickens", 1812)
    val charlesDickensImpostor = new Writer("Charles", "Dickens", 2021)

    // 创建一个 Novel（小说）对象
    val novel = new Novel("Great Expectations", 1861, charlesDickens)
    // 复制一本新版本（不同年份）
    val newEdition = novel.copy(1871)

    // 输出作家的全名
    println(charlesDickens.fullName)
    // 输出小说出版时作者的年龄
    println(novel.authorAge)
    // 判断是否由同一位作者写的（这里应该是 false，因为年份不同代表不同对象）
    println(novel.isWrittenBy(charlesDickensImpostor)) // false
    // 这里是 true，因为是同一个对象
    println(novel.isWrittenBy(charlesDickens)) // true
    // 输出新版本小说时作者的年龄
    println(newEdition.authorAge)

    // 创建一个 Counter（计数器）对象，初始值为 0
    val counter = new Counter()
    counter.print() // 输出当前计数（0）
    counter.increment().print() // 增加 1 并打印（1）
    counter.increment() // 增加 1，但返回的是新对象
    counter.print() // 原对象未变化，仍为 0

    // 增加 10 次并打印
    counter.increment(10).print() // 10
    // 增加 20000 次并打印
    counter.increment(20000).print() // 20000
  }
}


class Writer(firstName: String, lastName: String, val yearOfBirth: Int) {
  // 定义方法 fullName，返回“名 + 空格 + 姓”
  def fullName: String = s"$firstName $lastName"
}

class Novel(title: String, yearOfRelease: Int, val author: Writer) {
  // 计算出版时作者的年龄
  def authorAge: Int = yearOfRelease - author.yearOfBirth
  // 判断该小说是否由给定的作者写的（直接比较对象引用）
  def isWrittenBy(author: Writer): Boolean = this.author == author
  // 返回一本新出版年份的小说对象
  def copy(newYear: Int): Novel = new Novel(title, newYear, author)
}

/**
 * 练习 #2: 实现一个不可变（immutable）的计数器类
 * - 使用初始计数值构造
 * - increment/decrement（增减）方法返回新对象
 * - increment(n)/decrement(n) 支持多次加减
 * - print() 打印当前计数
 *
 * 优点：
 * + 在分布式环境下更安全
 * + 程序逻辑更容易理解
 */
class Counter(count: Int = 0) { // 默认计数为 0
  // 增加 1：返回新的 Counter 实例
  def increment(): Counter =
    new Counter(count + 1)

  // 减少 1：如果 count 为 0，则返回当前对象（不再减）
  def decrement(): Counter =
    if (count == 0) this
    else new Counter(count - 1)

  // 增加 n 次：递归调用自身
  def increment(n: Int): Counter =
    if (n <= 0) this // 当 n <= 0 时结束递归
    else increment().increment(n - 1) // 每次 +1，直到完成（可能导致栈溢出）

  // 减少 n 次：与上类似
  def decrement(n: Int): Counter =
    if (n <= 0) this
    else decrement().decrement(n - 1)

  // 打印当前计数值
  def print(): Unit =
    println(s"Current count: $count")
}

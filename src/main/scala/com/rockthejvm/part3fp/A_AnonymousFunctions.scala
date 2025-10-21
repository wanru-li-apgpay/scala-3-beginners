package com.rockthejvm.part3fp

object A_AnonymousFunctions {

  // FunctionN 实例（这里是 Function1，代表接收一个参数的函数）
  // 这是创建函数对象的传统写法
  val doubler: Int => Int = new Function1[Int, Int] {
    // apply 方法定义了函数的行为
    override def apply(x: Int) = x * 2
  }

  // Lambda 表达式（匿名函数）的写法，更简洁
  // 等价于上面的 doubler 写法
  val doubler_v2: Int => Int = (x: Int) => x * 2

  // 接收两个参数的函数（Function2）
  // 这行等价于 new Function2[Int, Int, Int] { override def apply(x, y) = x + y }
  val adder: (Int, Int) => Int = (x: Int, y: Int) => x + y

  // 无参数函数（zero-arg function）
  // 返回一个常量值 45
  // 注意：这是函数对象本身，而不是调用结果
  val justDoSomething: () => Int = () => 45
  // 要执行函数，必须加上括号 ()
  val anInvocation = justDoSomething()

  // 使用花括号（{}）包裹的匿名函数写法
  // 常见于多行实现或需要更清晰结构的场景
  val stringToInt = { (str: String) =>
    // 函数体实现，可以包含多行逻辑
    str.toInt
  }

  // 类型推断
  // Scala 编译器可以自动根据上下文推断参数类型
  val doubler_v3: Int => Int = x => x * 2
  val adder_v2: (Int, Int) => Int = (x, y) => x + y

  // 最简写法（使用下划线 _）
  // 每个下划线代表一个参数，且不能重复使用
  val doubler_v4: Int => Int = _ * 2    // 等价于 x => x * 2
  val adder_v3: (Int, Int) => Int = _ + _ // 等价于 (x, y) => x + y

  // ❗注意：每个下划线代表不同的参数，不能像普通变量一样重用
  // 例如：_ + _ 合法，但 _ + _ + _ 是不合法的

  /**
   * 【练习】
   * 1️⃣ 在 LList（链表）实现中，将所有 FunctionN 实例替换为 lambda 写法。
   * 2️⃣ 将 WhatsAFunction 章节中的 "special adder" 也改写成 lambda。
   */

  // 传统写法：返回一个“函数的函数”
  // superAdder 是一个高阶函数：接收 Int，返回一个新的函数 Function1[Int, Int]
  val superAdder = new Function1[Int, Function1[Int, Int]] {
    override def apply(x: Int) = new Function1[Int, Int] {
      override def apply(y: Int) = x + y
    }
  }

  // Lambda 版本写法，更简洁
  // (x: Int) => (y: Int) => x + y
  // 理解：这是一个“柯里化函数”，即函数返回函数
  val superAdder_v2 = (x: Int) => (y: Int) => x + y

  // 调用方式 1：先固定第一个参数 2，得到一个新的函数 (y: Int) => 2 + y
  val adding2 = superAdder_v2(2)

  // 调用方式 2：执行新函数，传入 y = 43
  val addingInvocation = adding2(43) // 结果为 45

  // 调用方式 3：直接链式调用，相当于上面两步合并
  val addingInvocation_v2 = superAdder_v2(2)(43) // 同样输出 45

  def main(args: Array[String]): Unit = {
    // 注意区别：println(justDoSomething) 打印函数本身（引用）
    println(justDoSomething)
    // println(justDoSomething()) 执行函数并打印返回值（45）
    println(justDoSomething())
  }
}

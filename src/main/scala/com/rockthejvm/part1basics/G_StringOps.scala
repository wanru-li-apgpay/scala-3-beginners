package com.rockthejvm.part1basics

object G_StringOps {

  val aString: String = "Hello, I am learning Scala"

  // 字符串函数（常见操作）
  val secondChar = aString.charAt(1)         // 获取第 2 个字符（索引从 0 开始），结果为 'e'
  val firstWord = aString.substring(0, 5)    // 截取第 0 到第 4 个索引（不包含 5）→ "Hello"
  val words = aString.split(" ")             // 根据空格分割成单词 → Array("Hello,", "I", "am", "learning", "Scala")
  val multiSplit = aString.split("[ ,.!]+")  // 使用多个分隔符切割字符串，+ 表示可以匹配一个或多个连续的分隔符（例如多个空格或连续标点）
  val startsWithHello = aString.startsWith("Hello")  // 检查是否以 "Hello" 开头，结果为 true
  val allDashes = aString.replace(' ', '-')  // 将所有空格替换为连字符，结果为 "Hello,-I-am-learning-Scala"
  val allUppercase = aString.toUpperCase()   // 全部转换为大写（也可以用 toLowerCase() 转小写）
  val nChars = aString.length                // 字符串总长度（包含空格），结果为 26

  // 其他字符串操作
  val reversed = aString.reverse             // 字符串反转
  val aBunchOfChars = aString.take(10)       // 取前 10 个字符（从左开始）

  // 字符串转换为数字
  val numberAsString = "2"
  val number = numberAsString.toInt          // 将字符串 "2" 转换为整数 2

  // s 字符串插值（s-interpolation）：使用变量直接插入字符串
  val name = "Alice"
  val age = 12
  val greeting = "Hello, I'm " + name + " and I am " + age + " years old." // 普通写法
  val greeting_v2 = s"Hello, I'm $name and I'm $age years old."           // 使用 s 插值语法，更简洁
  val greeting_v3 = s"Hello, I'm $name and I will be turning ${age + 1} years old." // 可以在 ${} 中放入表达式进行计算

  // f 字符串插值（f-interpolation）：支持格式化数字，类似 printf
  val speed = 1.2f
  val myth = f"$name can eat $speed%2.5f burgers per minute."
  // %2.5f 表示保留 5 位小数，总宽度至少 2 个字符

  // raw 字符串插值（raw-interpolation）：不会解析转义字符
  val escapes = raw"This is a \n newline"
  // 输出：This is a \n newline，而不是换行

  def main(args: Array[String]): Unit = {
    println(myth)
    println(escapes)
  }
}

package com.rockthejvm.part3fp

import scala.util.Random

object F_Options {

  // ===============================
  // Option 类型简介
  // ===============================
  //
  // Option 是 Scala 用来处理“可能存在也可能不存在”值的容器类型。
  // 它相当于 C# 的 Nullable<T> 或者 TryGetValue + null 检查的安全替代方案。
  // Option[T] 有两种可能：
  // - Some(value): 表示有值
  // - None: 表示没有值（类似 null，但更安全）

  // 创建一个 Option，包含值 42
  val anOption: Option[Int] = Option(42) // Some(42)
  // 创建一个空的 Option
  val anEmptyOption: Option[Int] = Option.empty // None

  // 另一种写法（直接用构造）
  val aPresentValue: Option[Int] = Some(4)
  val anEmptyOption_v2: Option[Int] = None

  // ===============================
  // 常用 API
  // ===============================

  val isEmpty = anOption.isEmpty          // 检查 Option 是否为空
  val innerValue = anOption.getOrElse(90) // 如果有值取值，否则取默认值 90
  val anotherOption = Option(46)
  val aChainedOption = anEmptyOption.orElse(anotherOption)
  // 类似 C# 的 “a ?? b” （空合并运算符）

  // ===============================
  // 函数式操作：map / flatMap / filter
  // ===============================
  //
  // map: 对 Option 内部的值进行变换（如果有值）
  val anIncrementedOption = anOption.map(_ + 1) // Some(43)

  // filter: 仅当满足条件时保留值，否则变成 None
  val aFilteredOption = anIncrementedOption.filter(_ % 2 == 0) // None（43 不是偶数）

  // flatMap: 展开嵌套 Option（类似 LINQ 的 SelectMany）
  val aFlatMappedOption = anOption.flatMap(value => Option(value * 10)) // Some(420)

  // ===============================
  // 为什么要用 Option？
  // ===============================
  // 因为很多 API 会返回 null —— 在 Scala 中我们用 Option 来避免 NPE（空指针异常）

  def unsafeMethod(): String = null
  def fallbackMethod(): String = "some valid result"

  // 传统防御式写法（命令式风格）
  val stringLength = {
    val potentialString = unsafeMethod()
    if (potentialString == null) -1
    else potentialString.length
  }

  // Scala 风格（Option 风格）：
  // 使用 Option 包装，自动处理 null
  val stringLengthOption = Option(unsafeMethod()).map(_.length)
  // 如果 unsafeMethod 返回 null，则 Option(null) = None

  // 使用 orElse 提供备用方案（如果第一个为 None，则用第二个）
  val someResult = Option(unsafeMethod()).orElse(Option(fallbackMethod()))
  // 类似 C# 的：unsafeMethod() ?? fallbackMethod()

  // ===============================
  // 推荐设计：返回 Option 而不是 null
  // ===============================
  def betterUnsafeMethod(): Option[String] = None
  def betterFallbackMethod(): Option[String] = Some("A valid result")

  val betterChain = betterUnsafeMethod().orElse(betterFallbackMethod())
  // 最终结果是 Some("A valid result")

  // ===============================
  // Map.get 也返回 Option
  // ===============================
  val phoneBook = Map(
    "Daniel" -> 1234
  )

  val marysPhoneNumber = phoneBook.get("Mary") // None
  // 不需要检查 null，也不会抛异常

  // ===============================
  // 实际练习：连接配置
  // ===============================
  // 假设 config 是从外部读取的配置文件
  val config: Map[String, String] = Map(
    "host" -> "176.45.32.1",
    "port" -> "8081"
  )

  // 定义连接类
  class Connection {
    def connect(): String = "Connection successful"
  }

  object Connection {
    val random = new Random()

    // 以 50% 概率返回 Some(Connection)，否则 None
    def apply(host: String, port: String): Option[Connection] =
      if (random.nextBoolean()) Some(new Connection)
      else None
  }

  // ===============================
  // Java/C# 风格（防御式写法）
  // ===============================
  /*
    String host = config("host");
    String port = config("port");
    if (host != null)
      if (port != null)
        Connection conn = Connection.apply(host, port);
        if (conn != null)
          return conn.connect();
        // ... 很多层嵌套，很不优雅
  */

  // ===============================
  // Scala Option 风格
  // ===============================
  val host = config.get("host") // Option[String]
  val port = config.get("port") // Option[String]

  // flatMap 链式调用（避免嵌套 null 检查）
  val connection = host.flatMap(h => port.flatMap(p => Connection(h, p)))
  val connStatus = connection.map(_.connect())

  // 或者更紧凑的写法：
  val connStatus_v2 =
    config.get("host").flatMap(h =>
      config.get("port").flatMap(p =>
        Connection(h, p).map(_.connect())
      )
    )

  // ===============================
  // for 推导式（更易读）
  // ===============================
  //
  // for 语法糖可以替代多个 flatMap/map 调用，
  // 对于 Option、List、Future 等都适用。
  //
  // 类似于 C# LINQ 查询表达式：
  // from h in config["host"]
  // from p in config["port"]
  // from conn in Connection(h, p)
  // select conn.connect()
  val connStatus_v3 = for {
    h <- config.get("host")        // 从 Option 中取值（如果有值才继续）
    p <- config.get("port")
    conn <- Connection(h, p)
  } yield conn.connect()

  // connStatus_v3 是 Option[String]，可能是 Some("Connection successful") 或 None

  // ===============================
  // 最终示例：执行结果
  // ===============================
  val host1 = "localhost"
  val port1 = "8081"
  val myUrl = "rockthejvm.com"

  def main(args: Array[String]): Unit = {
    println(connStatus.getOrElse("Failed to establish connection"))
    // 如果连接成功：打印 "Connection successful"
    // 否则：打印 "Failed to establish connection"
  }
}

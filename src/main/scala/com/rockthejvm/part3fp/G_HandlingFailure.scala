package com.rockthejvm.part3fp

import scala.util.{Failure, Random, Success, Try}

object G_HandlingFailure {

  // ===============================
  // Try 简介
  // ===============================
  //
  // Try[T] 是 Scala 用来封装「可能抛出异常」的计算。
  // 它代表一次可能成功 (Success) 或失败 (Failure) 的操作。
  //
  // 等价于：
  // - C# 的 try/catch 包裹结果 + 一个 Result 类型返回；
  // - 或者类似于 “TryParse” 返回布尔值 + 输出参数的安全风格。

  // 创建一个成功的 Try（内部值为 42）
  val aTry: Try[Int] = Try(42)

  // 创建一个失败的 Try（会捕获异常）
  val aFailedTry: Try[Int] = Try(throw new RuntimeException)

  // ===============================
  // 手动构造 Try
  // ===============================
  val aTry_v2: Try[Int] = Success(42) // 显式成功
  val aFailedTry_v2: Try[Int] = Failure(new RuntimeException) // 显式失败

  // ===============================
  // Try 的基本 API
  // ===============================
  val checkSuccess = aTry.isSuccess // 是否成功
  val checkFailure = aTry.isFailure // 是否失败

  // orElse：如果失败，则使用备用 Try
  val aChain = aFailedTry.orElse(aTry)

  // ===============================
  // map / flatMap / filter
  // ===============================
  //
  // Try 也是一个 Monad（单子）——可以像 Option 一样用 map、flatMap 等链式操作。
  // map：成功时应用函数，失败时保持原样。
  val anIncrementedTry = aTry.map(_ + 1) // Success(43)

  // flatMap：展开嵌套的 Try（类似 LINQ 的 SelectMany）
  val aFlatMappedTry = aTry.flatMap(mol => Try(s"My meaning of life is $mol"))
  // => Success("My meaning of life is 42")

  // filter：仅保留满足条件的值，否则变成 Failure(NoSuchElementException)
  val aFilteredTry = aTry.filter(_ % 2 == 0) // Success(42)

  // ===============================
  // 为什么要用 Try？
  // ===============================
  // 因为在传统语言中（例如 Java、C#），异常会中断执行；
  // 而 Try 把“异常”转化成“值”，让程序继续可组合。

  // 一个不安全的方法，可能抛异常
  def unsafeMethod(): String =
    throw new RuntimeException("No string for you, buster!")

  // -------------------------------
  // 传统防御式写法（命令式风格）
  // -------------------------------
  val stringLengthDefensive = try {
    val aString = unsafeMethod()
    aString.length
  } catch {
    case e: RuntimeException => -1
  }

  // -------------------------------
  // 函数式写法：Try 包装
  // -------------------------------
  val stringLengthPure = Try(unsafeMethod()).map(_.length).getOrElse(-1)
  // Try 会捕获异常，如果失败则返回 Failure。
  // getOrElse(-1)：当失败时使用默认值 -1。

  // ===============================
  // 更好的设计：API 返回 Try
  // ===============================
  def betterUnsafeMethod(): Try[String] = Failure(new RuntimeException("No string for you, buster!"))
  def betterBackupMethod(): Try[String] = Success("Scala")

  val stringLengthPure_v2 = betterUnsafeMethod().map(_.length)
  val aSafeChain = betterUnsafeMethod().orElse(betterBackupMethod()).map(_.length)
  // 如果第一个失败，执行备用方案。

  // ===============================
  // 练习：网络连接例子
  // ===============================

  val host = "localhost"
  val port = "8081"
  val myDesiredURL = "rockthejvm.com/home"

  class Connection {
    val random = new Random()

    // get 可能成功也可能失败
    def get(url: String): String = {
      if (random.nextBoolean()) "<html>Success</html>"
      else throw new RuntimeException("Cannot fetch page right now.")
    }

    // getSafe: 返回 Try 封装结果
    def getSafe(url: String): Try[String] =
      Try(get(url))
  }

  object HttpService {
    val random = new Random()

    // 不安全的版本：可能抛异常
    def getConnection(host: String, port: String): Connection =
      if (random.nextBoolean()) new Connection
      else throw new RuntimeException("Cannot access host/port combination.")

    // 安全版本：返回 Try 封装结果
    def getConnectionSafe(host: String, port: String): Try[Connection] =
      Try(getConnection(host, port))
  }

  // -------------------------------
  // 命令式写法（防御式）
  // -------------------------------
  val finalHtml = try {
    val conn = HttpService.getConnection(host, port)
    val html = try {
      conn.get(myDesiredURL)
    } catch {
      case e: RuntimeException => s"<html>${e.getMessage}</html>"
    }
  } catch {
    case e: RuntimeException => s"<html>${e.getMessage}</html>"
  }

  // -------------------------------
  // 函数式写法（Try 链式组合）
  // -------------------------------
  val maybeConn = Try(HttpService.getConnection(host, port))
  val maybeHtml = maybeConn.flatMap(conn => Try(conn.get(myDesiredURL)))

  // fold：类似 C# 的 Match 或 switch 表达式
  val finalResult = maybeHtml.fold(
    e => s"<html>${e.getMessage}</html>", // 左侧：失败分支（异常处理）
    s => s                               // 右侧：成功分支（取出值）
  )

  // -------------------------------
  // 用 for-comprehension 语法糖
  // -------------------------------
  //
  // for 可以自动展开 Try / Option / Future 等类型，
  // 类似 C# LINQ 的 "from... in ... select ..."。
  val maybeHtml_v2 = for {
    conn <- HttpService.getConnectionSafe(host, port)
    html <- conn.getSafe(myDesiredURL)
  } yield html

  // 同样使用 fold 处理结果
  val finalResult_v2 = maybeHtml.fold(
    e => s"<html>${e.getMessage}</html>",
    s => s
  )

  // ===============================
  // 程序入口
  // ===============================
  def main(args: Array[String]): Unit = {
    println(finalResult)
    // 输出结果可能是：
    // <html>Success</html>
    // 或 <html>Cannot fetch page right now.</html>
  }
}

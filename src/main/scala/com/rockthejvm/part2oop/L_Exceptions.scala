package com.rockthejvm.part2oop

object L_Exceptions {

  // Scala 也有 NullPointerException（空指標例外）
  val aString: String = null
  // aString.length 會造成 NPE (NullPointerException)

  // 1️⃣ 拋出例外（throw exceptions）
  // throw 是一個「表達式」，它的型別是 Nothing（永不返回）
  // val aWeirdValue: Int = throw new NullPointerException // Nothing 可當作任何型別使用

  // 🧱 Exception 階層結構：
  //
  // Throwable:
  //   ├── Error（通常代表 JVM 錯誤，像 StackOverflowError、OutOfMemoryError）
  //   └── Exception（程式邏輯錯誤，如 NullPointerException、RuntimeException 等）

  def getInt(withExceptions: Boolean): Int =
    if (withExceptions) throw new RuntimeException("No int for you!") // 拋出例外
    else 42 // 正常回傳 Int

  // 2️⃣ 捕捉例外（try - catch - finally）
  val potentialFail = try {
    // 可能發生錯誤的程式區塊
    getInt(true) // 會丟出 RuntimeException
  } catch {
    // 按照例外類型由「最具體」到「最通用」順序匹配
    case e: NullPointerException => 35
    case e: RuntimeException => 54 // 返回 Int
    // 這裡也可以加入更多 case
  } finally {
    // finally 會「無論有無例外」都執行
    // 通常用來釋放資源（例如關閉檔案、連線）
    // finally 區塊的回傳值是 Unit，不會影響整體結果
  }

  // 3️⃣ 自訂例外（Custom Exceptions）
  class MyException extends RuntimeException {
    // 可以加入自訂欄位或方法
    override def getMessage = "MY EXCEPTION" // 覆寫錯誤訊息
  }

  val myException = new MyException

  /**
   * 💪 練習題：
   *
   * 1. 製造 StackOverflowError
   * 2. 製造 OutOfMemoryError
   * 3. 在自訂鏈結串列 LList 中找到符合條件的元素（進階挑戰）
   */

  // (1) 模擬 StackOverflowError（堆疊溢出）
  def soCrash(): Unit = {
    def infinite(): Int = 1 + infinite() // 無窮遞迴
    infinite()
  }

  // (2) 模擬 OutOfMemoryError（記憶體溢出）
  def oomCrash(): Unit = {
    def bigString(n: Int, acc: String): String =
      if (n == 0) acc
      else bigString(n - 1, acc + acc) // 每次 acc + acc 都讓字串變兩倍

    bigString(56175363, "Scala") // 🚨 這個數字太大會直接讓 JVM 當機
  }

  def main(args: Array[String]): Unit = {
    //println(aString.length)

    println(getInt(false))
    println(potentialFail) // 印出 54，因為 RuntimeException 被捕捉到了

    // val throwingMyException = throw myException
    // ↑ 如果打開這行，會直接拋出自訂例外並中止程式

    // soCrash() // 🚨 千萬不要同時呼叫這個，會 StackOverflow
    oomCrash()  // 🚨 這會造成 JVM 記憶體爆掉，建議改小數字測試
  }
}

package com.rockthejvm.part2oop

object H_Generics {

  // =======================================
  // 🎯 目標：讓程式可以「重複利用」在不同的型別上
  // =======================================

  // -------------------------------
  // ✅ 方案一：為每個型別都寫一份 List
  // -------------------------------
  abstract class IntList {
    def head: Int
    def tail: IntList
  }
  class EmptyIntList extends IntList {
    override def head = throw new NoSuchElementException
    override def tail = throw new NoSuchElementException
  }
  class NonEmptyIntList(override val head: Int, override val tail: IntList) extends IntList

  // 為 String 再寫一份（重複）
  abstract class StringList {
    def head: String
    def tail: StringList
  }
  class EmptyStringList extends StringList {
    override def head = throw new NoSuchElementException
    override def tail = throw new NoSuchElementException
  }
  class NonEmptyStringList(override val head: String, override val tail: StringList) extends StringList

  /*
    優點：
    ✅ 保留型別安全（IntList 只能放 Int，StringList 只能放 String）
    缺點：
    ❌ 重複大量樣板程式（boilerplate）
    ❌ 不可維護，一有新型別就要複製整份程式
   */


  // -------------------------------
  // ✅ 方案二：讓 List 存放「任何型別」(Any)
  // -------------------------------
  abstract class GeneralList {
    def head: Any
    def tail: GeneralList
  }
  class EmptyGeneralList extends GeneralList {
    override def head = throw new NoSuchElementException
    override def tail = throw new NoSuchElementException
  }
  class NonEmptyGeneralList(override val head: Any, override val tail: GeneralList) extends GeneralList

  /*
    優點：
    ✅ 不再有重複程式碼
    ✅ 可以支援任何型別
    缺點：
    ❌ 失去型別安全（不知道 head 是 Int 還是 String）
    ❌ 容易混入不同型別（例如 List 中同時有貓與狗）
   */


  // -------------------------------
  // ✅ 方案三（最佳解）：使用「泛型 (Generic)」
  // -------------------------------
  abstract class MyList[A] { // A 為「型別參數」，可理解為「占位符」
    def head: A
    def tail: MyList[A]
  }

  class Empty[A] extends MyList[A] {
    override def head: A = throw new NoSuchElementException
    override def tail: MyList[A] = throw new NoSuchElementException
  }

  class NonEmpty[A](override val head: A, override val tail: MyList[A]) extends MyList[A]

  // 這樣我們就可以針對不同型別建立清單
  val listOfIntegers: MyList[Int] =
    new NonEmpty[Int](1, new NonEmpty[Int](2, new Empty[Int]))

  // 編譯器會知道裡面是 Int，因此可以直接使用數字運算
  val firstNumber = listOfIntegers.head
  val adding = firstNumber + 3

  // -------------------------------
  // ✅ 多型別參數範例
  // -------------------------------
  trait MyMap[Key, Value]  // 類似 Java 的 Map<K, V>


  // -------------------------------
  // ✅ 泛型方法 (Generic Method)
  // -------------------------------
  object MyList {
    def from2Elements[A](elem1: A, elem2: A): MyList[A] =
      new NonEmpty[A](elem1, new NonEmpty[A](elem2, new Empty[A]))
  }

  // 呼叫泛型方法的方式
  val first2Numbers = MyList.from2Elements[Int](1, 2) // 顯式指定型別
  val first2Numbers_v2 = MyList.from2Elements(1, 2)   // 編譯器自動推斷為 Int
  val first2Numbers_v3 = new NonEmpty(1, new NonEmpty(2, new Empty))

  /**
   * 🧩 練習題：
   * 嘗試將之前的 LList (鏈表結構) 改寫成泛型版本。
   * 例如：
   *   MyList[String]、MyList[Double]
   * 都能共用同一套邏輯。
   */

  def main(args: Array[String]): Unit = {

  }
}

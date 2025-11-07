package com.rockthejvm.part3fp

object C_MapFlatMapFilterFor {

  // -----------------------------
  // 標準 List
  // -----------------------------
  val aList = List(1,2,3) // List 是 Scala 的不可變集合，元素按順序排列
  val firstElement = aList.head      // head 返回第一個元素 = 1
  val restOfElements = aList.tail    // tail 返回除了第一個元素之外的剩餘元素 = List(2,3)

  // -----------------------------
  // map
  // -----------------------------
  // map 用於將函數應用到集合的每個元素，返回一個新集合
  val anIncrementedList = aList.map(_ + 1) // [2,3,4]

  // -----------------------------
  // filter
  // -----------------------------
  // filter 用於保留符合條件的元素
  val onlyOddNumbers = aList.filter(_ % 2 != 0) // [1,3]

  // -----------------------------
  // flatMap
  // -----------------------------
  // flatMap = map + flatten，先 map 再把多層集合「扁平化」
  val toPair = (x: Int) => List(x, x + 1)  // List(List(1,2), List(2,3), List(3,4))
  val aFlatMappedList = aList.flatMap(toPair) // [1,2,2,3,3,4]
  // 每個元素被映射成 List(x, x+1)，最後所有 List 被扁平化成單一 List

  // -----------------------------
  // 所有可能組合的例子
  // -----------------------------
  val numbers = List(1, 2, 3, 4)
  val chars = List('a', 'b', 'c', 'd')
  val colors = List("black", "white", "red")

  /*
    lambda = num => chars.map(char => s"$num$char")
    flatMap 可以展平每個數字對應的 chars 結果
    最終效果：
    ["1a", "1b", "1c", "1d", "2a", "2b", "2c", "2d", ...]
  */
  val combinations = numbers
    .withFilter(_ % 2 == 0) // 保留偶數，類似 filter
    .flatMap(number =>
      chars.flatMap(char =>
        colors.map(color => s"$number$char - $color")
      )
    )
  // 最終得到偶數 + 字母 + 顏色的所有組合列表

  // -----------------------------
  // for-comprehension
  // -----------------------------
  // for-comprehension 是 flatMap + map 的語法糖
  val combinationsFor = for {
    number <- numbers if number % 2 == 0 // generator + 過濾條件
    char <- chars
    color <- colors
  } yield s"$number$char - $color" // 生成結果

  // for-comprehensions 可以直接用 foreach 做副作用操作（Unit）
  for { num <- numbers } println(num)  // 等同於 numbers.foreach(println)


  // -----------------------------
  // 練習用自定義 LList
  // -----------------------------
  import com.rockthejvm.practice.*

  val lSimpleNumbers: LList[Int] = Cons(1, Cons(2, Cons(3, Empty())))
  // map, flatMap, filter
  val incrementedNumbers = lSimpleNumbers.map(_ + 1)        // [2,3,4]
  val filteredNumbers = lSimpleNumbers.filter(_ % 2 == 0)   // [2]
  val toPairLList: Int => LList[Int] = (x: Int) => Cons(x, Cons(x + 1, Empty()))
  val flatMappedNumbers = lSimpleNumbers.flatMap(toPairLList) // [1,2,2,3,3,4]

  // map + flatMap = for-comprehension
  val combinationNumbers = for {
    number <- lSimpleNumbers if number % 2 == 0
    char <- Cons('a', Cons('b', Cons('c', Empty())))
  } yield s"$number-$char"
  // 最終結果 = ["2-a", "2-b", "2-c"]

  // -----------------------------
  // 主程式
  // -----------------------------
  def main(args: Array[String]): Unit = {
    println(numbers)                   // [1,2,3,4]
    for { num <- numbers } println(num) // 依次打印每個元素

    println(combinations)              // flatMap + map 的結果
    println(combinationsFor)           // for-comprehension 結果（相同）

    println(incrementedNumbers)        // LList map 結果
    println(filteredNumbers)           // LList filter 結果
    println(flatMappedNumbers)         // LList flatMap 結果
    println(combinationNumbers)        // LList for-comprehension 結果
  }
}

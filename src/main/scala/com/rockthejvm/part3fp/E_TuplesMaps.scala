package com.rockthejvm.part3fp

object E_TuplesMaps {

  // =========================
  // 元组（Tuple）与 Map（映射）
  // =========================
  //
  // 在 Scala 中：
  // - Tuple 是一组有限的、有序的数据集合，可以包含不同类型的元素。
  // - Map 是键值对集合（key -> value），类似于 C# 的 Dictionary<K,V>。
  // 这两个都是不可变（immutable）的数据结构。

  // 创建一个 Tuple（包含两个元素：Int 和 String）
  val aTuple = (2, "rock the jvm") // 类型：Tuple2[Int, String] == (Int, String)

  // 访问 Tuple 中的第一个元素（下标从 1 开始，不是 0）
  val firstField = aTuple._1 // 结果为 2

  // 使用 copy 创建新元组（不可变对象的拷贝，修改指定字段）
  val aCopiedTuple = aTuple.copy(_1 = 54) // 生成新的 Tuple(54, "rock the jvm")

  // Tuple 的另一种写法，使用 “箭头语法”：
  val aTuple_v2 = 2 -> "rock the jvm" // 等价于 (2, "rock the jvm")
  // 这种写法在 Map 中非常常见（key -> value）

  // =========================
  // Map（映射）
  // =========================

  // 创建一个空的 Map
  val aMap = Map()

  // 定义一个电话簿 Map（key 为 String，value 为 Int）
  val phonebook: Map[String, Int] = Map(
    "Jim" -> 555,
    "Daniel" -> 789,
    "Jane" -> 123
  ).withDefaultValue(-1) // 如果 key 不存在，默认返回 -1，而不会抛异常

  // 基础 API 操作
  val phonebookHasDaniel = phonebook.contains("Daniel") // 检查是否包含某个 key（类似 C# 的 ContainsKey）
  val marysPhoneNumber = phonebook("Mary") // 若没有定义 Mary，会抛异常（除非设置了 defaultValue）

  // 添加新键值对（返回一个新 Map，不会修改原 Map）
  val newPair = "Mary" -> 678
  val newPhonebook = phonebook + newPair // “+” 操作符会返回新集合（函数式风格）

  // 删除某个键（同样返回一个新的 Map）
  val phoneBookWithoutDaniel = phonebook - "Daniel"

  // =========================
  // List ↔ Map 相互转换
  // =========================

  // 从 List 创建 Map
  val linearPhonebook = List(
    "Jim" -> 555,
    "Daniel" -> 789,
    "Jane" -> 123
  )
  val phonebook_v2 = linearPhonebook.toMap // 转换为 Map[String, Int]

  // 从 Map 转回 List（或其他集合类型）
  val linearPhonebook_v2 = phonebook.toList // 可使用 toSeq, toVector, toArray, toSet 等

  // =========================
  // Map 的高阶操作（map、flatMap、filter）
  // =========================
  //
  // 注意：Scala 的 Map 也是“可遍历集合”，因此可以直接使用 map、filter 等函数式方法，
  // 类似于 C# 的 LINQ（Select、Where、ToDictionary 等）。

  // map：对每个键值对进行转换（pair 是一个 Tuple2）
  val aProcessedPhonebook = phonebook.map(pair =>
    (pair._1.toUpperCase(), pair._2)
  )
  // 上例将所有 key 转为大写（"Jim" -> 555）变成 ("JIM" -> 555)

  // filterKeys：筛选 key（只保留不以 J 开头的 key）
  val noJs = phonebook.view.filterKeys(!_.startsWith("J")).toMap
  // 注意：view 表示“惰性视图”，性能更高；最后用 toMap 实际化结果
  //view 只是一個包裝（wrapper），記錄下你要怎麼處理數據，但不馬上執行。
  //只有當真的取值或轉成具體集合（例如 .toMap、.toList）時，才會真正執行。

  // mapValues：仅对 value 做映射（例如添加区号前缀）
  val prefixNumbers = phonebook.view.mapValues(number => s"0255-$number").toMap
  // 结果示例: Map("Jim" -> "0255-555", "Daniel" -> "0255-789", "Jane" -> "0255-123")

  // =========================
  // 其他集合转 Map
  // =========================
  //
  // groupBy：根据某个规则分组（返回 Map[Key, List[元素]]）
  val names = List("Bob", "James", "Angela", "Mary", "Daniel", "Jim")

  // 按第一个字母分组
  val nameGroupings = names.groupBy(name => name.charAt(0))
  // 结果：Map(
  //   'B' -> List("Bob"),
  //   'J' -> List("James", "Jim"),
  //   'A' -> List("Angela"),
  //   'M' -> List("Mary"),
  //   'D' -> List("Daniel")
  // )

  // =========================
  // 程序入口
  // =========================
  def main(args: Array[String]): Unit = {
    println(phonebook)             // 打印初始电话簿
    println(phonebookHasDaniel)    // 检查是否包含 Daniel
    println(marysPhoneNumber)      // 获取 Mary 的号码（或默认值）
    println(nameGroupings)         // 打印按首字母分组的结果
  }
}

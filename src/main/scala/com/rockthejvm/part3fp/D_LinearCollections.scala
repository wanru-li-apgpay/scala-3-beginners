package com.rockthejvm.part3fp

import scala.util.Random

object D_LinearCollections {

  // ===========================
  // Seq：有序且可索引的序列（类似 C# 的 List<T> 或 IEnumerable<T>）
  // ===========================
  def testSeq(): Unit = {
    val aSequence = Seq(4,2,3,1)
    // Seq 是不可变的有序集合，可以用索引访问元素

    val thirdElement = aSequence.apply(2) // 获取索引2的元素 -> 结果是3
    // C# 对应：var thirdElement = aSequence[2];

    // 映射（map）：对每个元素做转换，返回新序列
    val anIncrementedSequence = aSequence.map(_ + 1) // [5,3,4,2]
    // C# 对应：aSequence.Select(x => x + 1)

    // flatMap：对每个元素返回一个子序列，然后扁平化
    val aFlatMappedSequence = aSequence.flatMap(x => Seq(x, x + 1)) // [4,5,2,3,3,4,1,2]
    // C# 对应：aSequence.SelectMany(x => new[]{x, x + 1})

    // filter：过滤出符合条件的元素
    val aFilteredSequence = aSequence.filter(_ % 2 == 0) // [4,2]
    // C# 对应：aSequence.Where(x => x % 2 == 0)

    // 其他常用操作
    val reversed = aSequence.reverse            // 反转序列
    val concatenation = aSequence ++ Seq(5,6,7) // 拼接序列，C# 对应：Concat
    val sortedSequence = aSequence.sorted       // 排序 [1,2,3,4]，C# 对应：OrderBy(x => x)
    val sum = aSequence.foldLeft(0)(_ + _)      // 累加求和，C# 对应：Aggregate(0, (acc, x) => acc + x)
    val stringRep = aSequence.mkString("[", ",", "]") // 转成字符串，C# 对应：string.Join(",", list)

    println(aSequence)
    println(concatenation)
    println(sortedSequence)
  }

  // ===========================
  // List：不可变单链表（头部操作快，尾部慢）
  // ===========================
  def testLists(): Unit = {
    val aList = List(1,2,3)  // 不可变链表，类似 C# 的 ImmutableList<int>

    val firstElement = aList.head  // 获取头部元素 -> 1
    val rest = aList.tail          // 剩余元素 -> List(2,3)
    // C# 类似：list.First() / list.Skip(1)

    // 在头部或尾部添加元素
    val aBiggerList = 0 +: aList :+ 4 // [0,1,2,3,4]
    // “+:” 表示在前面加，":+" 表示在后面加
    // C# 对应：list.Prepend(0).Append(4)（不可变链）

    val prepending = 0 :: aList // 同上，:: 是 List 专属的“在前面添加”语法糖
    // :: 相当于函数式语言中的 Cons 操作（O(1)）
    // C# 没有直接等价的操作，但类似于 new List<int>{0}.Concat(aList)

    // 快速创建重复列表
    val scalax5 = List.fill(5)("Scala") // ["Scala","Scala","Scala","Scala","Scala"]
  }

  // ===========================
  // Range：范围序列（自动生成连续整数）
  // ===========================
  def testRanges(): Unit = {
    val aRange = 1 to 10        // 包含 10（1,2,...,10）
    val aNonInclusiveRange = 1 until 10 // 不包含 10（1,2,...,9）

    // 遍历（foreach 类似 C# foreach）
    (1 to 10).foreach(_ => println("Scala"))
    // C# 对应：Enumerable.Range(1, 10).ToList().ForEach(_ => Console.WriteLine("Scala"));
  }

  // ===========================
  // Array：可变数组（JVM 原生 int[]）
  // ===========================
  def testArrays(): Unit = {
    val anArray = Array(1,2,3,4,5,6) // 底层是 JVM int[]，可变类型
    // C# 对应：int[] anArray = {1,2,3,4,5,6};

    // 将 Array 转为 Seq（适配为不可变视图）
    val aSequence: Seq[Int] = anArray.toIndexedSeq

    // 修改元素（in-place 更新）
    anArray.update(2, 30) // 第3个元素改成30，不会创建新数组
    // C# 对应：anArray[2] = 30;
  }

  // ===========================
  // Vector：适合大量数据的不可变序列
  // ===========================
  def testVectors(): Unit = {
    val aVector: Vector[Int] = Vector(1,2,3,4,5,6)
    // Vector 是不可变但支持高效随机访问的结构（内部使用树状分块结构）
    // 更新或访问操作接近 O(1)，比 List 快很多（List 更新是 O(n)）
    // C# 类似：ImmutableArray<int> 或 ImmutableList<int>
  }

  // ===========================
  // smallBenchmark：比较 List 与 Vector 的更新性能
  // ===========================
  def smallBenchmark(): Unit = {
    val maxRuns = 1000
    val maxCapacity = 1000000

    // 计算每次更新操作平均耗时
    def getWriteTime(collection: Seq[Int]): Double = {
      val random = new Random()
      val times = for {
        i <- 1 to maxRuns
      } yield {
        val index = random.nextInt(maxCapacity)
        val element = random.nextInt()

        val currentTime = System.nanoTime()
        // updated：返回一个修改后的新集合（原集合不变）
        val updatedCollection = collection.updated(index, element)
        // C# 类似：immutableList.SetItem(index, element)
        System.nanoTime() - currentTime
      }

      // 平均耗时
      times.sum * 1.0 / maxRuns
    }

    val numbersList = (1 to maxCapacity).toList
    val numbersVector = (1 to maxCapacity).toVector

    // List 的 updated 是 O(n)，Vector 是 O(log32 n)，差距很大
    println(getWriteTime(numbersList))
    println(getWriteTime(numbersVector))
  }

  // ===========================
  // Set：无重复、无序的集合
  // ===========================
  def testSets(): Unit = {
    val aSet = Set(1,2,3,4,5,4) // 重复元素自动去除 -> {1,2,3,4,5}
    // 默认是 HashSet（不可变）

    // 测试元素是否存在
    val contains3 = aSet.contains(3) // true
    val contains3_v2 = aSet(3)       // 同上，简写形式
    // C# 对应：hashSet.Contains(3)

    // 添加/删除元素（返回新集合）
    val aBiggerSet = aSet + 4  // 添加（已存在则不变）
    val aSmallerSet = aSet - 4 // 删除元素
    // C# 对应：hashSet.Add(4), hashSet.Remove(4)

    // 并集（Union）
    val anotherSet = Set(4,5,6,7,8)
    val muchBiggerSet = aSet.union(anotherSet)
    val muchBiggerSet_v2 = aSet ++ anotherSet // 同上
    val muchBiggerSet_v3 = aSet | anotherSet  // 同上
    // C# 对应：hashSet.UnionWith(anotherSet)

    // 差集（Difference）
    val aDiffSet = aSet.diff(anotherSet)
    val aDiffSet_v2 = aSet -- anotherSet
    // C# 对应：hashSet.ExceptWith(anotherSet)

    // 交集（Intersection）
    val anIntersection = aSet.intersect(anotherSet)
    val anIntersection_v2 = aSet & anotherSet
    // C# 对应：hashSet.IntersectWith(anotherSet)
  }

  def main(args: Array[String]): Unit = {
    smallBenchmark() // 演示 List vs Vector 性能差异
  }
}

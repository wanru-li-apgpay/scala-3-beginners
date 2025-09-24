package com.rockthejvm.interviewPractice

import scala.annotation.tailrec
import scala.util.Random

sealed abstract class RList[+T] {
  def head: T

  def tail: RList[T]

  def isEmpty: Boolean

  def ::[S >: T](elem: S): RList[S] = new::(elem, this)

  def apply(index: Int): T

  def length: Int

  def reverse: RList[T]

  def ++[S >: T](anotherList: RList[S]): RList[S]

  def removeAt(index: Int): RList[T]

  def map[S](f: T => S): RList[S]

  def flatMap[S](f: T => RList[S]): RList[S]

  def filter(f: T => Boolean): RList[T]

  def rle: RList[(T, Int)]

  def duplicateEach(n: Int): RList[T]

  def rotate(n: Int): RList[T]

  def sample(n: Int): RList[T]
  def insertionSorted[S >: T](ordering:Ordering[S]):RList[S]
//  def mergeSort[S >: T](ordering: Ordering[S]):RList[S]
}

case object RNil extends RList[Nothing] {
  override def head: Nothing = throw new NoSuchElementException

  override def tail: RList[Nothing] = throw new NoSuchElementException

  override def isEmpty: Boolean = true

  override def toString: String = "[]"

  override def apply(index: Int): Nothing = throw new NoSuchElementException

  override def length: Int = 0

  override def reverse: RList[Nothing] = RNil

  override def ++[S >: Nothing](anotherList: RList[S]): RList[S] = anotherList

  override def removeAt(index: Int): RList[Nothing] = RNil

  override def map[S](f: Nothing => S): RList[Nothing] = RNil

  override def flatMap[S](f: Nothing => RList[S]): RList[S] = RNil

  override def filter(f: Nothing => Boolean): RList[Nothing] = RNil

  override def rle: RList[(Nothing, Int)] = RNil

  override def duplicateEach(n: Int): RList[Nothing] = RNil

  override def rotate(n: Int): RList[Nothing] = RNil

  override def sample(n: Int): RList[Nothing] = RNil

  override def insertionSorted[S >: Nothing](ordering: Ordering[S]): RList[S] = RNil

//  override def mergeSort[S >: Nothing](ordering: Ordering[S]): RList[S] = RNil
}

case class ::[+T](override val head: T, override val tail: RList[T]) extends RList[T] {
  override def isEmpty: Boolean = false

  override def toString: String = {
    @tailrec
    def toStringTailrec(remaining: RList[T], result: String): String = {
      if (remaining.isEmpty) result
      else if (remaining.tail.isEmpty) {
        s"$result${remaining.head}"
      }
      else toStringTailrec(remaining.tail, s"$result${remaining.head}, ")
    }

    "[" + toStringTailrec(this, "") + "]"
  }

  override def apply(index: Int): T = {
    @tailrec
    def applyTailrec(remaining: RList[T], currentIndex: Int): T = {
      if (currentIndex == index) remaining.head
      else applyTailrec(remaining.tail, currentIndex + 1)
    }

    if (index < 0) throw new NoSuchElementException
    else applyTailrec(this, 0)
  }

  override def length: Int = {
    @tailrec
    def lengthCalculateTailrec(remaining: RList[T], count: Int): Int = {
      if (remaining.isEmpty) count
      else lengthCalculateTailrec(remaining.tail, count + 1)
    }

    lengthCalculateTailrec(this, 0)
  }

  override def reverse: RList[T] = {
    @tailrec
    def reverseTailrec(remainingList: RList[T], result: RList[T]): RList[T] = {
      if (remainingList.isEmpty) result
      else reverseTailrec(remainingList.tail, remainingList.head :: result)
    }

    reverseTailrec(this, RNil)
  }

  override def ++[S >: T](anotherList: RList[S]): RList[S] = {
    @tailrec
    def concatTailrec(remainingList: RList[S], acc: RList[S]): RList[S] = {
      if (remainingList.isEmpty) acc
      else concatTailrec(remainingList.tail, remainingList.head :: acc)
    }

    concatTailrec(anotherList, this.reverse).reverse
  }

  override def removeAt(index: Int): RList[T] = {
    @tailrec
    def removeAtTailrec(remaining: RList[T], currentIndex: Int, acc: RList[T]): RList[T] = {
      if (currentIndex == index) {
        acc ++ remaining.tail
      }
      else removeAtTailrec(remaining.tail, currentIndex + 1, remaining.head :: acc)
    }

    removeAtTailrec(this, 0, RNil)
  }

  override def map[S](f: T => S): RList[S] = {
    @tailrec
    def mapTailrec(remaining: RList[T], acc: RList[S]): RList[S] = {
      if (remaining.isEmpty) acc.reverse
      else mapTailrec(remaining.tail, f(remaining.head) :: acc)
    }

    mapTailrec(this, RNil)
  }

  override def flatMap[S](f: T => RList[S]): RList[S] = {
    @tailrec
    def flatMapTailrec(remaining: RList[T], acc: RList[S]): RList[S] = {
      if (remaining.isEmpty) acc.reverse
      else flatMapTailrec(remaining.tail, f(remaining.head) ++ acc)
    }

    flatMapTailrec(this, RNil)
  }

  override def filter(f: T => Boolean): RList[T] = {
    @tailrec
    def filterTailrec(remaining: RList[T], acc: RList[T]): RList[T] = {
      if (remaining.isEmpty) acc.reverse
      else if (f(remaining.head)) filterTailrec(remaining.tail, remaining.head :: acc)
      else filterTailrec(remaining.tail, acc)
    }

    filterTailrec(this, RNil)

  }

  override def rle: RList[(T, Int)] = {
    @tailrec
    def rleTailrec(remaining: RList[T], currentTuple: (T, Int), acc: RList[(T, Int)]): RList[(T, Int)] = {
      if (remaining.isEmpty && currentTuple._2 == 0) acc.reverse
      else if (remaining.isEmpty) (currentTuple :: acc).reverse
      else if (remaining.head == currentTuple._1) rleTailrec(remaining.tail, currentTuple.copy(_2 = currentTuple._2 + 1), acc)
      else rleTailrec(remaining.tail, (remaining.head, 1), currentTuple :: acc)
    }

    rleTailrec(this.tail, (this.head, 1), RNil)
  }

  override def duplicateEach(n: Int): RList[T] = {
    @tailrec
    def duplicateTailrec(remaining: RList[T], currentElement: T, count: Int, acc: RList[T]): RList[T] = {
      if (remaining.isEmpty && count == n) acc.reverse
      else if (count == n) duplicateTailrec(remaining.tail, remaining.head, 0, acc)
      else duplicateTailrec(remaining, currentElement, count + 1, currentElement :: acc)
    }

    duplicateTailrec(this.tail, this.head, 0, RNil)
  }

  override def rotate(n: Int): RList[T] = {
    @tailrec
    def rotateTailrec(remaining: RList[T], currentIndex: Int, reverseList: RList[T]): RList[T] = {
      if (remaining.isEmpty) reverseList.reverse
      else if (currentIndex < n) rotateTailrec(remaining.tail, currentIndex + 1, remaining.head :: reverseList)
      else remaining ++ reverseList.reverse
    }

    rotateTailrec(this, 0, RNil)
  }

  override def sample(n: Int): RList[T] = {
    val random = new Random(System.currentTimeMillis())
    val maxIndex = this.length

    @tailrec
    def sampleTailrec(count: Int, acc: RList[T]): RList[T] = {
      if (count >= n) acc
      else {
        val index = random.nextInt(maxIndex)
        val newNumber = this (index)
        sampleTailrec(count + 1, newNumber :: acc)
      }
    }

    sampleTailrec(0, RNil)
  }

  override def insertionSorted[S >: T](ordering: Ordering[S]): RList[S] = {
    @tailrec
    def insertAccTailrec(element: T, before:RList[S], after:RList[S]):RList[S]={
      if(after.isEmpty || ordering.lteq(element, after.head)) before.reverse ++ (element ::after)
      else insertAccTailrec(element, after.head :: before, after.tail)
    }
    @tailrec
    def insertSortTailrec(remaining:RList[T], acc: RList[S]):RList[S]={
      if(remaining.isEmpty) acc
      else insertSortTailrec(remaining.tail, insertAccTailrec(remaining.head,RNil,acc))
    }

    insertSortTailrec(this, RNil)
  }
}

object RList {
  def from[T](iterable: Iterable[T]): RList[T] = {
    @tailrec
    def convertToRListTailrec(remaining: Iterable[T], acc: RList[T]): RList[T] = {
      if (remaining.isEmpty) acc
      else convertToRListTailrec(remaining.tail, remaining.head :: acc)
    }

    convertToRListTailrec(iterable, RNil).reverse
  }
}

object ListProblems extends App {
  val aSmallList: RList[Int] = 1 :: 2 :: 3 :: RNil
  val aSmallList2: RList[Int] = 1 :: 2 :: 3 :: 4 :: 5 :: 6 :: RNil
  val aSmallList3: RList[Int] = 5 :: 2 :: 1 :: 4 :: 8 :: 7 :: RNil
  //  println(aSmallList)            // 印出列表
  //  println(aSmallList.apply(1))   // DEBUG log + 印出元素 2
  //  println(aSmallList.length)
  //  println(aSmallList.reverse)
  //  println(RList.from(1 to 100))
  //
  //  println(aSmallList ++ aSmallList2)
  //  println(aSmallList.removeAt(1))
  //  println(aSmallList.map(x => x + 1))
  //  println(aSmallList.flatMap(x => (2 * x) :: RNil))
  //  println(aSmallList.filter(x => x == 2))
  //  val aList:RList[Int] = 1::1::1::1::2::3::4::5::RNil
  //  println(aList.rle)
  //println(aSmallList.duplicateEach(3))
  //  println(aSmallList2.rotate(0))
  //println(aSmallList2.sample(3))

  val ordering= Ordering.fromLessThan[Int](_<_)
  println(aSmallList3.insertionSorted(ordering))
}

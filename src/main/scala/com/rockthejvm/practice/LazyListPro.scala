package com.rockthejvm.practice

import scala.annotation.tailrec

abstract class LzList[A] {
  def isEmpty: Boolean

  def head: A

  def tail: LzList[A]

  def #::(element: A): LzList[A]

  infix def ++(another: => LzList[A]): LzList[A]

  def foreach(f: A => Unit): Unit

  def map[B](f: A => B): LzList[B]

  def flatMap[B](f: A => LzList[B]): LzList[B]

  def filter(predicate: A => Boolean): LzList[A]

  def withFilter(predicate: A => Boolean): LzList[A] = filter(predicate)

  def take(n: Int): LzList[A]

  def takeAsList(n: Int): List[A] = take(n).toList

  def toList: List[A] = {
    @tailrec
    def toListAux(remaining: LzList[A], acc: List[A]): List[A] =
      if (remaining.isEmpty) acc.reverse
      else toListAux(remaining.tail, remaining.head :: acc)

    toListAux(this, List())
  }
}

case class LzEmpty[A]() extends LzList[A] {
  def isEmpty: Boolean = true

  def head: A = throw new NoSuchElementException

  def tail: LzList[A] = throw new NoSuchElementException

  def #::(element: A): LzList[A] = new LzCons[A](element, this)

  infix def ++(another: => LzList[A]): LzList[A] = another

  def foreach(f: A => Unit): Unit = ()

  def map[B](f: A => B): LzList[B] = LzEmpty()

  def flatMap[B](f: A => LzList[B]): LzList[B] = LzEmpty()

  def filter(predicate: A => Boolean): LzList[A] = this

  def take(n: Int): LzList[A] =
    if ((n == 0)) this
    else throw new RuntimeException("Cannot take $n elements from an empty lazy list.")
}

class LzCons[A](hd: => A, tl: => LzList[A]) extends LzList[A] {
  def isEmpty: Boolean = false

  override lazy val head: A = hd
  override lazy val tail: LzList[A] = tl

  def #::(element: A): LzList[A] = new LzCons(element, this)

  infix def ++(another: => LzList[A]): LzList[A] =
    new LzCons(head, tail ++ another)

  def foreach(f: A => Unit): Unit = {
    @tailrec
    def foreachTail(lzList: LzList[A]): Unit =
      if (lzList.isEmpty) ()
      else {
        f(lzList.head)
        foreachTail(lzList.tail)
      }

    foreachTail(this)
  }

  def map[B](f: A => B): LzList[B] =
    LzCons(f(head), tail.map(f))

  def flatMap[B](f: A => LzList[B]): LzList[B] =
    f(head) ++ tail.flatMap(f)

  def filter(predicate: A => Boolean): LzList[A] =
    if (predicate(head)) new LzCons(head, tail.filter(predicate))
    else tail.filter(predicate)

  def take(n: Int): LzList[A] = {
    if (n <= 0) LzEmpty()
    else if (n == 1) new LzCons(head, LzEmpty())
    else new LzCons(head, tail.take(n - 1))
  }
}

object LzList {
  def empty[A]: LzList[A] = LzEmpty()

  def generate[A](start: A)(generator: A => A): LzList[A] =
    new LzCons(start, LzList.generate(generator(start))(generator))

  def from[A](list: List[A]): LzList[A] = list.reverse.foldLeft(LzList.empty) { (currentLzList, newElement) =>
    new LzCons(newElement, currentLzList)
  }

  def apply[A](values: A*): LzList[A] = LzList.from(values.toList)

  def fibonacci: LzList[BigInt] = {
    def fibo(first: BigInt, second: BigInt): LzList[BigInt] =
      new LzCons[BigInt](first, fibo(second, first + second))

    fibo(1, 2)
  }

}

object LazyListPro {

}

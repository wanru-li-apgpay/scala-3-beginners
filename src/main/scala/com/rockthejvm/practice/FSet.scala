package com.rockthejvm.practice

abstract class FSet[A] extends (A => Boolean){
  def contains(elem: A): Boolean
  def apply(elem: A):Boolean = contains(elem)
  infix def ++(anotherSet: FSet[A]):FSet[A]

  def map[B](f:A => B):FSet[B]
  def flatMap[B](f:A => FSet[B]):FSet[B]
  def filter(predicate: A => Boolean): FSet[A]
  def foreach(f:A => Unit):Unit
}

//case class Empty[A]() extends FSet[A]{
//  override def contains(elem: A): Boolean = false
//  infix def + (elem:A):FSet[A] = Cons(elem,this)
//  infix def ++(anotherSet: FSet[A]): FSet[A] = anotherSet
//
//  override def map[B](f: A => B): FSet[B] = Empty()
//
//  override def filter(predicate: A => Boolean): FSet[A] = this
//
//  override def flatMap[B](f: A => FSet[B]): FSet[B] = Empty()
//
//  override def foreach(f: A => Unit): Unit =()
//
//}


//case class Cons[A](head: A, tail:FSet[A]) extends FSet[A]{
//  override def contains(elem: A): Boolean = elem == head || tail.contains(elem)
//
//  infix def +(elem: A): FSet[A] = Cons(elem, this)
//
//  infix def ++(anotherSet: FSet[A]): FSet[A] = tail ++ anotherSet + head
//
//  override def map[B](f: A => B): FSet[B] = Empty()
//
//  override def filter(predicate: A => Boolean): FSet[A] = this
//
//  override def flatMap[B](f: A => FSet[B]): FSet[B] = Empty()
//
//  override def foreach(f: A => Unit): Unit = ()
//}

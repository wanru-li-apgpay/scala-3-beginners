package com.rockthejvm.practice

import scala.annotation.tailrec

object Recap{
  val aCondition=false
  val onIfExpression= if (aCondition) 42 else 55
  val theUnit= println("Hello")
  def aFunction(x:Int):Int = x+1

  @tailrec def factorial(n:Int, acc:Int):Int={
    if(n<0) acc
    else factorial(10,1)
  }

  class Animal
  class Dog extends Animal
  
  def main(args:Array[String]):Unit={}
}


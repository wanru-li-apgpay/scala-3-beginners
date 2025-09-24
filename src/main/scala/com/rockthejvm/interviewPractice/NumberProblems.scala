package com.rockthejvm.interviewPractice

import scala.annotation.tailrec

object NumberProblems extends App{
  def isPrime(n:Int):Boolean={
    @tailrec
    def PrimeTailrec(currentDivisor: Int):Boolean={
      if(currentDivisor > Math.sqrt(Math.abs(n)))true
      else n % currentDivisor !=0 && PrimeTailrec(currentDivisor+1)
    }

    PrimeTailrec(2)
  }

  def decompose(n: Int):List[Int] ={
    assert(n>0)
    @tailrec
    def decomposeTailrec(remaining: Int, currentDivisor:Int, accumulator:List[Int]):List[Int]={
      if(currentDivisor > Math.sqrt(remaining)) remaining::accumulator
      else if (remaining % currentDivisor == 0) decomposeTailrec(remaining/currentDivisor, currentDivisor, currentDivisor :: accumulator)
      else decomposeTailrec(remaining, currentDivisor+1, accumulator)
    }
    decomposeTailrec(n,2,List())
  }

  println(isPrime(2))
  println(isPrime(15))
  println(isPrime(2003))
  println(isPrime(13))
  println(isPrime(0))

  println(decompose(16))
}

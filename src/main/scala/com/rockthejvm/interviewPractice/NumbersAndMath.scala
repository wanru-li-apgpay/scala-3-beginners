package com.rockthejvm.interviewPractice

import scala.annotation.tailrec

object NumbersAndMath {
  def largestNumber(numbers: List[Int]): String = {
    implicit val newOrdering: Ordering[Int] = Ordering.fromLessThan { (a, b) =>
      val aString = a.toString
      val bString = b.toString
      (aString + bString).compareTo(bString + aString) >= 0
    }
    val largest = numbers.sorted.mkString("")
    if (numbers.isEmpty || largest.charAt(0) == '0') "0"
    else largest
  }

  def reverseInteger(number: Int): Int = {
    //    val nString=number.toString
    //    nString.reverse.toInt
    @tailrec
    def reverseTailrec(remaining: Int, acc: Int): Int = {
      if (remaining == 0) acc
      else {
        val digit = remaining % 10
        val tentativeResult = acc * 10 + digit

        //over int max 時會變成負數
        if ((acc >= 0) != (tentativeResult >= 0)) 0
        else reverseTailrec(remaining / 10, tentativeResult)
      }
    }

    if (number == Int.MinValue) 0
    else if (number > 0) reverseTailrec(number, 0)
    else -reverseTailrec(-number, 0)
  }

  // ignore spaces
  // sign character
  // if number exceeds the int range, return either Int.MinValue or int.MaxValue
  def parseInteger(string: String): Int = {
    val WHITESPACE = ' '
    val PLUS = '+'
    val MINUS = '-'
    val DIGITS = "0123456789".toSet

    def integerRangeEnd(sign: Int): Int = if (sign >= 0) Int.MaxValue else Int.MinValue

    @tailrec
    def parseIntegerTailrec(remaining: String, sign: Int, acc: Int): Int = {
      if (remaining.isEmpty || !DIGITS.contains(remaining.charAt(0))) acc
      else {
        val newDigit = remaining.charAt(0) - '0'
        val tentativeResult = acc * 10 + newDigit * sign

        if ((sign >= 0) != (tentativeResult >= 0)) integerRangeEnd(sign)
        else parseIntegerTailrec(remaining.substring(1), sign, tentativeResult)
      }
    }

    if (string.isEmpty) 0
    else if (string.charAt(0) == WHITESPACE) parseInteger(string.substring(1))
    else if (string.charAt(0) == PLUS) parseIntegerTailrec(string.substring(1), 1, 0)
    else if (string.charAt(0) == MINUS) parseIntegerTailrec(string.substring(1), -1, 0)
    else parseIntegerTailrec(string, 1, 0)
  }

  def uglyNumber(number:Int):Boolean={
    val uglyFactors=List(2,3,5)
    @tailrec
    def factorTailrec(remaining:Int, n:Int, isUgly:Boolean):Boolean={
      if(remaining < n  || remaining == 0) isUgly
      else if (remaining %n ==0){
        if(uglyFactors.contains(n)) factorTailrec(remaining/n,n,true)
        else false
      }
      else factorTailrec(remaining, n+1, isUgly)
    }
    factorTailrec(number, 2, false)
  }
  def duplicates(list:List[Int]):Int={
    def naive(remainder:List[Int]):Int={
      if(remainder.isEmpty) throw new IllegalArgumentException("list is empty")
      else{
        val element = remainder.head
        val elementCount = list.count(_==element)

        if (elementCount == 1) element
        else naive(remainder.tail)
      }
    }

    naive(list)
  }

  def main(args: Array[String]): Unit = {
    //    println(largestNumber(List(10,2)))
    //    println(largestNumber(List(3,30,5,9,34)))
    //    println(largestNumber(List(1)))
    //    println(largestNumber(List()))
    //    println(largestNumber(List(0)))

//    println(reverseInteger(123))
//    println(reverseInteger(987654))
//    println(reverseInteger(1567890))
//    println(reverseInteger(-9))
//    println(reverseInteger(-53))
//    println(reverseInteger(Int.MinValue))

//      println(parseInteger(" 1234"))
//      println(parseInteger("+1234"))
//      println(parseInteger("-1234"))
//      println(parseInteger("-fewfwefewf"))
//      println(parseInteger(""))

//        println(uglyNumber(6))
//        println(uglyNumber(25))
//        println(uglyNumber(1))
//        println(uglyNumber(14))
    //
        println(duplicates(List(1)))
        println(duplicates(List(1,2,1)))
        println(duplicates(List(1,2,1,3,1,2,1)))
  }
}

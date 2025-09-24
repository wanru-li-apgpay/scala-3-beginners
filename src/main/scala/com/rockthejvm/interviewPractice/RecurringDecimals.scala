package com.rockthejvm.interviewPractice

object RecurringDecimals extends App{
  def fractionToRecurringDecimals(numerator:Int, denominator:Int):String={
    if(numerator >0 && denominator < 0) fractionToRecurringDecimals(numerator, -denominator)
    else if (numerator <0 && denominator >0 ) fractionToRecurringDecimals(-numerator, denominator)
    else{
      val quotient = numerator/denominator
      val remainder = numerator % denominator
      if(remainder == 0)
        return s"$quotient"
      else s"$quotient.${fractionToRecurringDecimals(remainder, denominator)}"
    }
  }

}

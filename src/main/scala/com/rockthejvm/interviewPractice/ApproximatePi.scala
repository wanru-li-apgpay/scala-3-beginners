package com.rockthejvm.interviewPractice

import scala.util.Random

object ApproximatePi extends App {
  val random = Random(System.currentTimeMillis())
  def approximatePi(nPoints: Int):Double={
    val nPointInsodeCircle =(1 to nPoints).map{ _=>
      val x = random.nextDouble();
      val y = random.nextDouble()
      x*x+y*y
    }.count(distance => distance <1)
    nPointInsodeCircle*4.0/nPoints
  }
}

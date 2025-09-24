package com.rockthejvm.playground

object Playground {

  def concatenator: (String,String) => String = new Function2[String,String,String]{
    override def apply(v1: String, v2: String): String = v1+v2
  }


  def main(args: Array[String]): Unit = {
    println("Running Scala 3! I can't wait to learn Scala in this course...")
  }
}



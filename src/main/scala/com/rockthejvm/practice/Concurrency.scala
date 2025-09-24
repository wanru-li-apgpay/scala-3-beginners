package com.rockthejvm.practice

import java.lang
import scala.annotation.tailrec

object Concurrency {
  def inception(maxThread: Int, i: Int = 1): Thread = {
    new Thread(() => {
      if (i < maxThread) {
        val newThread = inception(maxThread, i + 1)
        newThread.start()
        newThread.join()
      }
      println("Hello from thread $i")
    })
  }

  def main(args: Array[String]): Unit = {
    inception(10).start()
  }
}

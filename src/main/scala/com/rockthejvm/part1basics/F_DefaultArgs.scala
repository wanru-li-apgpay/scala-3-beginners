package com.rockthejvm.part1basics

import scala.annotation.tailrec

object F_DefaultArgs {

  // 帶默認值的尾遞歸函數：accumulator 默認為 0
  @tailrec
  def sumUntilTailrec(x: Int, accumulator: Int = 0): Int =
    if (x <= 0) accumulator
    else sumUntilTailrec(x - 1, accumulator + x)

  // 調用時只傳入一個參數，第二個參數使用默認值 0
  val sumUntil100 = sumUntilTailrec(100) // 默認參數會自動填入

  // 如果一個函數大多數時候都用相同的值，可以設置默認參數
  def savePicture(
                   dirPath: String,
                   name: String,
                   format: String = "jpg",   // 默認圖片格式為 jpg
                   width: Int = 1920,        // 默認寬度為 1920 像素
                   height: Int = 1080        // 默認高度為 1080 像素
                 ): Unit =
    println("正在以格式 " + format + " 保存圖片，路徑為：" + dirPath)

  def main(args: Array[String]): Unit = {
    // 使用默認參數：只傳入必要的兩個參數，其他自動補上
    savePicture("/users/daniel/photos", "myphoto")

    // 顯式傳入第三個參數，其他使用默認值
    savePicture("/users/daniel/photos", "myphoto", "png")

    // 使用具名參數（named arguments）來設定後面的參數
    savePicture("/users/daniel/photos", "myphoto", width = 800, height = 600)

    // 使用具名參數可以交換順序，不受限制
    savePicture("/users/daniel/photos", "myphoto", height = 600, width = 800)
  }
}

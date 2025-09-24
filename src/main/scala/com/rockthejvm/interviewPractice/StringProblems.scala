package com.rockthejvm.interviewPractice

import scala.annotation.tailrec

object StringProblems {

  def countCharacters(s: String): Map[Char, Int] = {
    @tailrec
    def countCharacterTailrec(remaining: String, acc: Map[Char, Int]): Map[Char, Int] = {
      if (remaining.isEmpty) acc
      else if (acc.contains(remaining.head)) {
        val currentChar = remaining.head
        val currentOccurrences = acc(currentChar)
        //已經存在的 key 再做 + (key -> value)，Scala 不會「新增一個重複 key」，而是 更新原本的 value
        countCharacterTailrec(remaining.tail, acc + (currentChar -> (currentOccurrences + 1)))
      }
      else countCharacterTailrec(remaining.tail, acc + (remaining.head -> 1))
    }

    countCharacterTailrec(s, Map())
  }

  def checkAnagrams(sa: String, sb: String): Boolean = {
    val saCharacter = countCharacters(sa)
    val sbCharacter = countCharacters(sb)
    saCharacter == sbCharacter

    //m2
    //sa.sorted == sb.sorted
  }

  def hasValidParentheses(string: String): Boolean = {
    @tailrec
    def validParensTailrec(remaining: String, openParens: Int): Boolean = {
      if (remaining.isEmpty) openParens == 0
      else if (openParens == 0 && remaining.head == ')') false
      else if (remaining.head == '(') validParensTailrec(remaining.tail, openParens + 1)
      else validParensTailrec(remaining.tail, openParens - 1)
    }

    validParensTailrec(string, 0)
  }

  /*
    n = 1 => List("()")
    n = 2 => List("()()","(())")
    n = 3 => List("()()()","((()))", "()(())", "(())()", "(()())")
  */

  def generateAllValidParentheses(n: Int): List[String] = {
    def genParensTailrec(nRemainingParens: Int, currentString: Set[String]): Set[String] = {
      /*
      prepend () =()()
      inject () =(())
      append () = ()()
       */
      if (nRemainingParens == 0) currentString
      else {
        val newString = for {
          string <- currentString
          index <- 0 until string.length
        } yield {
          val (before, after) = string.splitAt(index)
          s"$before()$after"
        }

        genParensTailrec(nRemainingParens - 1, newString)
      }
    }

    if (n == 0) List()
    else genParensTailrec(n - 1, Set("()")).toList
  }

  def justify(text: String, width: Int): String = {
    def createSpaces(n: Int): String = (1 to n).map(_ => " ").mkString("")

    @tailrec
    def pack(words: List[String], currentRow: List[String], currentCharCount: Int, result: List[List[String]]): List[List[String]] = {
      if (words.isEmpty && currentRow.isEmpty) {
        result
      }
      else if (words.isEmpty) {
        result :+ currentRow
      }
      else if (currentRow.isEmpty && words.head.length > width) {
        val (partOne, partTwo) = currentRow.head.splitAt(width - 2)
        pack(partTwo :: words.tail, List(), 0, result :+ List(partOne + "-"))
      }
      else if (words.head.length + currentCharCount > width) {
        pack(words, List(), 0, result :+ currentRow)
      }
      else {
        pack(words.tail, currentRow :+ words.head, currentCharCount + 1 + words.head.length, result)
      }

    }

    def justifyRow(row: List[String]): String = {
      if(row.length == 1) row.head
      else{
        // I,love,Scala
        // nSpacesAvailable = 5 => 15-10
        val nSpacesAvailable = width - row.map(_.length).sum // 總長-所有字串長
        //nIntervals 2 => 3-1
        val nIntervals = row.length -1 // 中間有幾個間距
        // nSpacesPerInterval 5/2 = 2
        val nSpacesPerInterval = nSpacesAvailable/nIntervals
        // nExtraSpace 5%2 =1
        val nExtraSpace = nSpacesAvailable % nIntervals

        if (nExtraSpace == 0) row.mkString(createSpaces(nSpacesPerInterval))
        else {
          //nWordsWithBiggerIntervals = 1+1
          val nWordsWithBiggerIntervals = nExtraSpace +1
          val wordWithBiggerIntervals = row.take(nWordsWithBiggerIntervals)
          val firstPart = wordWithBiggerIntervals.mkString(createSpaces(nSpacesPerInterval+1))
          val secondPart = row.drop(nWordsWithBiggerIntervals).mkString(createSpaces(nSpacesPerInterval))
          firstPart+ " " + secondPart
        }
      }
    }

    assert(width > 2)
    // split text into words
    val words = text.split(" ").toList
    // pack the words into rows
    val unjustifiedRows = pack(words, List(),0, List())
    // justify the rows
    // init 會回傳除了最後一個元素以外的所有元素
    // 最後一行不用補齊
    val justifyRows = unjustifiedRows.init.map(justifyRow) :+ unjustifiedRows.last.mkString(" ")
    // rebuild the justified text
    justifyRows.mkString("\n")
  }

  //val nums = List(1,2,3,4)
  //val sum = nums.foldLeft(0)((acc, n) => acc + n)
  //從左開始累積
  def ransomNote(note:String, magazine: String):Boolean={
    //產生 MapView（延遲計算，只算一次）
    def buildMap(string:String) : Map[Char,Int]= string.groupBy(c=>c).view.mapValues(_.length).toMap
//    {
//      string.foldLeft(Map[Char,Int]()){
//        case (map,char) => map + ( char -> (map.getOrElse(char,0) + 1))
//      }
//    }

    val noteMap= buildMap(note)
    val magazineMap = buildMap(magazine)
    noteMap.keySet.forall(char => noteMap.getOrElse(char,0) <= magazineMap.getOrElse(char, 0 ))

  }

  // example" 0.9 < 1.0.3.4 < 1.1.0 < 2.0 < 2.1 == 2.01
  // output -1 : v1 <v2 , 0 : v1== v2, 1 : v1 > v2
  def compareVersionNumbers(v1:String, v2:String):Int={
    @tailrec
    def compareVersion(r1:List[Int], r2:List[Int]):Int={
      if(r1.isEmpty && r2.isEmpty) {
        0
      }
      else if (r1.isEmpty){
        if(r2.exists(_ != 0 )) -1
        else 0
      }
      else if (r2.isEmpty){
        if (r1.exists(_ != 0)) 1
        else 0
      }
      else if (r1.head > r2.head) 1
      else if (r1.head < r2.head) -1
      else compareVersion(r1.tail,r2.tail)
    }

    val v1String = v1.split("\\.").toList.map(_.toInt)
    val v2String = v2.split("\\.").toList.map(_.toInt)
    compareVersion(v1String,v2String)
  }

  // multiply two numbers represented as string, of arbitrary length
//  def multiplyStrings(a:String,b:String):String ={
//   // a =123 , b456
//
//   val digitsA = a.reverse.map(c => c -'0').toList
//   val digitsB = b.reverse.map(c => c -'0').toList
//  }

  // "aaabc" -> "abaca","aabca"......
  // "aaa" -> "" can't reorganize return ""
  // rearrange chars so that no two adjacent chars are identical
  def reorganizeString(string: String):String={
    @tailrec
    def organizeTailrec(charMap: Map[Char,Int], forbiddenChar:Char, acc: String):String={
      if(charMap.isEmpty) acc
      else {
       val filterList= charMap.filter(_._1 != forbiddenChar)
        val newC= filterList.maxBy(_._2)
        val newCount =
          if(newC._2 == 1) charMap - newC._1
          else charMap + (newC._1 -> (newC._2 - 1 ))

        organizeTailrec(newCount, newC._1, acc + newC._1)
      }
    }

    val stringMap = string.groupBy(c=>c).view.mapValues(_.length).toMap
    if(stringMap.values.exists(_ > (string.length+1) / 2)) ""
    else organizeTailrec(stringMap,' ',"")
  }

  def reverseWords(string:String):String ={
    @tailrec
    def concatString(remaining: List[String], acc: String) : String={
      if(remaining.isEmpty) acc
      else if (remaining.head == "") concatString(remaining.tail, acc)
      else concatString(remaining.tail, remaining.head+" " +acc)
    }

    val s= string.split(" ").toList
    concatString(s, "")


    // simple way
    // string.split(" ").filter(!_.isEmpty).reverse.mkString(" ")
  }

  def main(args: Array[String]): Unit = {
    //    println(countCharacters("Scala"))
    //    println(countCharacters("QQQ123"))

    //    println(checkAnagrams("desserts","stressed"))
    //    println(checkAnagrams("Scala","stressed"))
    //    println(hasValidParentheses("()"))
    //    println(hasValidParentheses("((()))"))
    //    println(hasValidParentheses("))(("))

//    println(generateAllValidParentheses(1))
//    println(generateAllValidParentheses(2))
//    println(generateAllValidParentheses(3))
//    println(generateAllValidParentheses(10))

//    println(justify("An inventory of system components that are in scope for PCI DSS, including a description of function/use, must be maintained and kept current.", 35))
//
//    println(ransomNote("abc", "cba"))
//    println(ransomNote("help me", "people make helpers everywhere"))
//    println(ransomNote("need money", "send me home"))

//    println(compareVersionNumbers("0.9","1.0"))
//    println(compareVersionNumbers("1.0.0.0","1.0"))
//    println(compareVersionNumbers("1.0.4.0","1.0"))
//
//    println(reverseWords("I Love Scala"))
//    println(reverseWords("I Love           Scala"))

    println(reorganizeString("aaabc"))
    println(reorganizeString("aaaab"))
  }
}

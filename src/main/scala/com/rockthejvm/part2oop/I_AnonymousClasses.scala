package com.rockthejvm.part2oop

object I_AnonymousClasses {

  // =======================================
  // 🎯 匿名類（Anonymous Class）
  // 當我們只需要「建立一次特定行為的實例」時，
  // 無須額外宣告一個類別，可直接使用匿名類。
  // =======================================

  // 抽象類：定義介面但不提供實作
  abstract class Animal {
    def eat(): Unit
  }

  // ---------------------------------------
  // ✅ 傳統方式：先定義一個具體子類
  // ---------------------------------------
  class SomeAnimal extends Animal {
    override def eat(): Unit = println("I'm a weird animal")
  }

  val someAnimal = new SomeAnimal
  // ↑ 雖然可以運作，但若只需要用一次，寫這個類太冗長（boilerplate）


  // ---------------------------------------
  // ✅ 簡潔方式：使用「匿名類」
  // ---------------------------------------
  val someAnimal_v2 = new Animal { // ← 注意：大括號中是匿名類的內容
    override def eat(): Unit = println("I'm a weird animal")
  }

  /*
    這一段程式在編譯後會自動產生一個「編譯器生成的類」：
    （類似下面這樣）

    class I_AnonymousClasses$anon$1 extends Animal {
      override def eat(): Unit = println("I'm a weird animal")
    }

    val someAnimal_v2 = new I_AnonymousClasses$anon$1
   */

  // 匿名類可以用於任何類別（無論是否為抽象類）或 trait
  // ---------------------------------------

  class Person(name: String) {
    def sayHi(): Unit = println(s"Hi, my name is $name")
  }

  // 使用匿名類建立「特製版本」的 Person
  val jim = new Person("Jim") {
    override def sayHi(): Unit = println("MY NAME IS JIM!") // 改寫方法
  }

  // ---------------------------------------
  // 🧩 使用情境
  // ---------------------------------------
  // 匿名類常用在：
  // - 只需要一次的自訂實作（例如回呼 callback、臨時邏輯）
  // - 測試時快速替換行為
  // - 傳遞具體物件給函式參數（例如 Runnable、Listener）

  def main(args: Array[String]): Unit = {
    someAnimal.eat()    // I'm a weird animal
    someAnimal_v2.eat() // I'm a weird animal
    jim.sayHi()         // MY NAME IS JIM!
  }
}

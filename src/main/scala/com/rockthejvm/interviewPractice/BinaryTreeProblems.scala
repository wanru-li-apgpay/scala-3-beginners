package com.rockthejvm.interviewPractice

import scala.annotation.tailrec

sealed abstract class BTree[+T] {
  def value: T

  def left: BTree[T]

  def right: BTree[T]

  def isEmpty: Boolean

  def isLeaf: Boolean

  def collectLeaves: List[BTree[T]]

  def leafCount: Int

  def size: Int

  def collectNodes(level: Int): List[BTree[T]]

  def mirror: BTree[T]

  def sameShapeAs[S >: T](that: BTree[S]):Boolean

  def isSymmetrical :Boolean

  def toList: List[T]
}

case object BEnd extends BTree[Nothing] {
  override def value: Nothing = throw new NoSuchElementException

  override def left: BTree[Nothing] = throw new NoSuchElementException

  override def right: BTree[Nothing] = throw new NoSuchElementException

  override def isEmpty: Boolean = true

  override def isLeaf: Boolean = false

  override def collectLeaves: List[BTree[Nothing]] = List()

  override def leafCount: Int = 0

  override val size: Int = 0

  override def collectNodes(level: Int): List[BTree[Nothing]] = List()

  override def mirror: BTree[Nothing] = BEnd

  override def sameShapeAs[S >: Nothing](that: BTree[S]): Boolean = false

  override def isSymmetrical: Boolean = true

  override def toList: List[Nothing] = List()
}

case class BNode[+T](override val value: T, override val left: BTree[T], override val right: BTree[T]) extends BTree[T] {
  override def isEmpty: Boolean = false

  override def isLeaf: Boolean = left.isEmpty && right.isEmpty

  override def collectLeaves: List[BTree[T]] = {
    @tailrec
    def collectLeavesTailrec(todo: List[BTree[T]], leaves: List[BTree[T]]): List[BTree[T]] = {
      if (todo.isEmpty) leaves
      else if (todo.head.isEmpty) collectLeavesTailrec(todo.tail, leaves)
      else if (todo.head.isLeaf) collectLeavesTailrec(todo.tail, todo.head :: leaves)
      else {
        val node = todo.head
        collectLeavesTailrec(node.left :: node.right :: todo.tail, leaves)
      }
    }

    collectLeavesTailrec(List(this), List())
  }

  override def leafCount: Int = collectLeaves.length

  //  override def size: Int = {
  //    @tailrec
  //    def countSizeTailrec(tree:List[BTree[T]], count:Int):Int={
  //      if(tree.isEmpty) count
  //      else if (tree.head.isEmpty) countSizeTailrec(tree.tail, count)
  //      else if (tree.head.isLeaf) countSizeTailrec(tree.tail, count+1)
  //      else {
  //        val node = tree.head
  //        countSizeTailrec(node.left :: node.right :: tree.tail, count+1)
  //      }
  //    }
  //    countSizeTailrec(List(this),0)
  //  }
  override val size: Int = 1 + left.size + right.size

  override def collectNodes(level: Int): List[BTree[T]] = {
    @tailrec
    def collectNodeTailrec(currentLevel: Int, currentNodes: List[BTree[T]]): List[BTree[T]] = {
      if (currentNodes.isEmpty) List()
      else if (currentLevel == level) currentNodes
      else {
        val expandedNode = for {
          node <- currentNodes
          child <- List(node.left, node.right) if !child.isEmpty
        } yield child

        collectNodeTailrec(currentLevel + 1, expandedNode)
      }

    }

    if (level < 0) List()
    collectNodeTailrec(0, List(this))
  }

  //     1             1
  //   2   3         3    2
  // 4 5  7  8     8  7  5  4
  //    6               6
  override def mirror: BTree[T] = BNode(value, right.mirror, left.mirror)

  override def sameShapeAs[S >: T](that: BTree[S]): Boolean = {
    @tailrec
    def shapeTailrec(a:List[BTree[S]], b:List[BTree[S]]):Boolean={
      if(a.isEmpty) b.isEmpty
      else if (b.isEmpty) a.isEmpty
      else{
        val aNode = a.head
        val bNode = b.head
        if(((aNode.isEmpty) && aNode.isEmpty == bNode.isEmpty) || ((aNode.isLeaf)&& aNode.isLeaf == bNode.isLeaf)) {
          shapeTailrec(a.tail, b.tail)
        }
        else if (aNode.isEmpty || bNode.isEmpty || aNode.isLeaf || bNode.isLeaf) false
        else shapeTailrec(
          aNode.left :: aNode.right :: a.tail,
          bNode.left :: bNode.right :: b.tail
        )
      }
    }
    if(this.isEmpty || that.isEmpty) false
    shapeTailrec(List(this), List(that))
  }

  override def isSymmetrical: Boolean = {
    sameShapeAs(this.mirror)
  }

  override def toList: List[T] = {
    @tailrec
    def listTailrec(remaining: List[BTree[T]], acc: List[T]):List[T]={
      if( remaining.isEmpty) acc
      else {
       val node= remaining.head
        if(node.isEmpty) listTailrec(remaining.tail, acc)
        else if (node.isLeaf) listTailrec(remaining.tail, acc:+ node.value)
        else listTailrec(node.left:: node.right :: remaining.tail, acc:+ node.value)
      }
    }
    listTailrec(List(this),List())
  }
}


object BinaryTreeProblems extends App {
  val tree = BNode(1,
    BNode(2,
      BNode(3, BEnd, BEnd),
      BNode(4, BEnd, BNode(5, BEnd, BEnd)
      )
    ),
    BNode(6,
      BNode(7, BEnd, BEnd),
      BNode(8, BEnd, BEnd)
    )
  )
  val tree2 = BNode(1,
    BNode(2,
      BNode(3, BEnd, BEnd),
      BNode(4, BEnd, BNode(5, BEnd, BEnd)
      )
    ),
    BNode(6,
      BNode(7, BEnd, BEnd),
      BNode(8, BEnd, BEnd)
    )
  )
  val tree3 = BNode(1,
    BNode(2,
      BNode(3, BEnd, BEnd),
      BEnd
    ),
    BNode(6,
      BNode(7, BEnd, BEnd),
      BNode(8, BEnd,  BNode(4, BEnd, BNode(5, BEnd, BEnd)
      ))
    )
  )
//  println(tree.collectLeaves.map(_.value))
//  println(tree.size)
//  println(tree.collectNodes(2))
  //println(tree.sameShapeAs(tree2))
 // println(tree2.sameShapeAs(tree3))
  println(tree.toList)
  println(tree3.toList)
}

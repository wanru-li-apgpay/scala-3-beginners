package com.rockthejvm.interviewPractice

import scala.annotation.tailrec
import scala.collection.immutable.Queue

object TreeProblems {

  //           1
  //      2         6
  //   3    4     7   8
  //          5
  //  sum of a path from root to a leaf, if equal to the target return true
  def hasPathSum(tree:BTree[Int], target:Int):Boolean ={
    // 1 not leaf [1],[],[] go left
    // 2 not lead [2,1],[],[] go left
    // 3 is left [2,1],[3],[6]
    // 2 left done go right
    // 4 not leaf [4,2,1],[3],[6]
//    def pathSumTailrec(remaining: List[BTree[Int]],pending:List[Int],done:List[T], sumResult:List[T]):List[T]={
//      if(remaining.isEmpty)sumResult
//      else{
//        val node=remaining.head
//        if(node.isLeaf){
//          val sum=pending.sum + node.value
//          pathSumTailrec(remaining.tail,pending,done:+ node.value, sum)
//        }
//        else if (node.isEmpty) pathSumTailrec(remaining.tail,pending,done, sumResult)
//        else {
//          pathSumTailrec(node.left :: node.right :: remaining.tail, node.value::pending, done, sumResult)
//        }
//
//      }
//    }
    def pathSumStack(tree:BTree[Int], remaining:Int):Boolean={
      if(tree.isEmpty) remaining == 0
      else if (tree.isLeaf) remaining == tree.value
      else if (tree.left.isEmpty) pathSumStack(tree.right, remaining-tree.value)
      else pathSumStack(tree.left, remaining- tree.value)
    }
    
    @tailrec
    def pathSumTailrec(nodes: Queue[BTree[Int]], targets:Queue[Int]):Boolean={
      if(nodes.isEmpty) false
      else{
        val node= nodes.head
        val target = targets.head
        if (node.isLeaf && node.value == target) true
        else{
          val children = List(node.left, node.right).filter(!_.isEmpty)
          val childrenTarget = children.map(_ => target - node.value)
          pathSumTailrec(nodes.tail ++ children, targets.tail ++ childrenTarget)
        }
      }
    }
    pathSumTailrec(Queue(tree), Queue(target))
    //pathSumStack(tree, target)
  }

  def main(args:Array[String]):Unit={
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
    println(hasPathSum(tree,6))
    println(hasPathSum(tree,7))
  }
}

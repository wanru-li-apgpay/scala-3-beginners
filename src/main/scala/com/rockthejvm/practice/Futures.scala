package com.rockthejvm.practice

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Promise}
import scala.util.Try

object Futures {

  def immediatelyFuture[A](value: A): Future[A] = Future(value)

  def immediatelyFuture_2[A](value: A): Future[A] = Future.successful(value)

  def inSequence[A,B](first:Future[A], second: Future[B]):Future[B]={
    first.flatMap(_=>second)
  }

  def first[A](f1: Future[A],f2: Future[A]):Future[A]={
    val promise = Promise[A]()
    f1.onComplete(r1=> promise.complete(r1))
    f2.onComplete(r2=> promise.complete(r2))

    promise.future
  }

  def last[A](f1:Future[A], f2:Future[A]):Future[A]={
    val bothPromise =Promise[A]()
    val lastPromise =Promise[A]()
    def checkAndComplete(result :Try[A]):Unit={
      if(!bothPromise.tryComplete(result))
        lastPromise.complete(result)
    }
    f1.onComplete(checkAndComplete)
    f2.onComplete(checkAndComplete)
    lastPromise.future
  }
}

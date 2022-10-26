package amv

import scala.annotation.tailrec

/*
  Implement a lazily evaluated, singly linked Stream of Elements
  Streams are specific kind of collection in that the head of the stream is always evaluated
  but the tail of the stream is always lazily evaluated and available on demand


  naturals = MyStream.from(1)(x => x + 1) stream of natural numbers (Potentially infinite)
  naturals.take(100).foreach(println) //lazily evaluated stream of the first 100 naturals (finite Stream)
  naturals.foreach(println) // will crash - infinite
  naturals.map(_ * 2) // stream of all even numbers (potentially infinite)
   */


abstract class MyStream[+A] {
  def isEmpty: Boolean
  def head: A
  def tail: MyStream[A]

  def #::[B >: A](element: B): MyStream[B] // prepend operator
  def ++[B >: A](anotherStream: => MyStream[B]): MyStream[B] // concatenate

  def foreach(f: A => Unit): Unit
  def map[B](f: A => B): MyStream[B]
  def flatMap[B](f: A => MyStream[B]): MyStream[B]
  def filter(predicate: A => Boolean): MyStream[A]

  def take(n: Int): MyStream[A] // takes the first n elements out of the stream
  def takeAsList(n: Int): List[A] = take(n).toList()

  @tailrec
  final def toList[B >: A](acc: List[B] = Nil): List[B] =
    if(isEmpty) acc.reverse
    else tail.toList(head :: acc)
}


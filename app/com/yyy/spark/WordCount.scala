package com.yyy.spark

import org.apache.spark.{SparkConf, SparkContext}

object WordCount extends App {
  val conf = new SparkConf().setAppName("yyy").setMaster("local").set("spark.testing.memory", "2147480000")
  val sc = new SparkContext(conf)
  val input = "words"
  println(conf)
  println(sc)
  println(sc.textFile(input))
  val texts = sc.textFile(input).map(_.split(" "))
    .flatMap(words => words.map(word => (word.stripMargin, 1)))
  val counts = texts.reduceByKey(_ + _)
  counts.collect.foreach { case (word, num) => println(s"word: $word, num: $num") }
}

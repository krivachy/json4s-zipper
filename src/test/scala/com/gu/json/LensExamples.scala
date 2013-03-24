package com.gu.json

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite
import org.json4s._
import org.json4s.native.JsonMethods._

import scalaz._, Scalaz._

import Lenses._


class LensExamples extends FunSuite with ShouldMatchers {

  val json = parse("""
      {
        "type":"image",
        "assets":[
          {
            "type":"image/jpeg",
            "file":"foo.jpg"
          },
          {
            "type":"image/png",
            "file":"foo.png"
          }
        ]
      }
    """)

  test("Replacing a value") {

    field("assets").mod(_ => JArray(Nil), json) should be (parse("""
      {
        "type":"image",
        "assets":[]
      }
    """))

  }

  test("Modifying a value") {

    val firstFilename = field("assets") >>> elem(0) >>> field("file") >>> strVal

    firstFilename.mod(_.stripSuffix(".jpg"), json) should be (parse("""
      {
        "type":"image",
        "assets":[
          {
            "type":"image/jpeg",
            "file":"foo"
          },
          {
            "type":"image/png",
            "file":"foo.png"
          }
        ]
      }
    """))

  }

}

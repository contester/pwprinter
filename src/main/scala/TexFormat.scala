package org.stingray.contester.pwprinter

import play.twirl.api._

import scala.collection.immutable.Seq
import scala.collection.parallel.immutable

class Tex(val body: String) extends Appendable[Tex] with Content {
  override def contentType: String = "application/x-latex"
}

object TexFormat extends Format[Tex] {
  override def raw(text: String): Tex = new Tex(text)

  private val textEntities = Map(
    '%' -> "\\%",
  '$' -> "\\$",
  '_' -> "\\_",
  '{' -> "\\{",
  '#' -> "\\#"
  ).withDefault(_.toString)

  override def escape(text: String): Tex =
    new Tex(text.map(textEntities).mkString)

  override def empty: Tex = new Tex("")

  override def fill(elements: Seq[Tex]): Tex =
    new Tex(elements.map(_.body).mkString)
}
package org.stingray.contester.pwprinter

import play.twirl.api.{BufferedContent, Format, MimeTypes, Appendable}

import scala.collection.immutable.Seq
import scala.collection.parallel.immutable

class Tex(elements: Seq[Tex], text: String) extends BufferedContent[Tex](elements, text) {
  override def contentType: String = "application/x-latex"

  def this(elements: Seq[Tex]) = this(elements, "")
  def this(text: String) = this(Nil, text)
}

object TexFormat extends Format[Tex] {
  override def raw(text: String): Tex = new Tex(value)

  override def escape(text: String): Tex = ???

  override def empty: Tex = ???

  override def fill(elements: Seq[Tex]): Tex = ???
}
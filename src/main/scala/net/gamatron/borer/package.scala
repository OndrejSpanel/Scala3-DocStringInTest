package net.gamatron

import enumeratum.EnumEntry
import io.bullet.borer.*

package object borer {

  // adapted from io.circe decodeEnumeration

  final def decodeEnumeration[E <: Enumeration](enumeration: E): Decoder[enumeration.Value] = implicitly[Decoder[String]].map(s => enumeration.withName(s))

  final def encodeEnumeration[E <: Enumeration](enumeration: E): Encoder[enumeration.Value] = implicitly[Encoder[String]].contramap(_.toString)

  final def decodeEnumeratum[A <: EnumEntry, E <: enumeratum.Enum[A]](values: E): Decoder[A] = implicitly[Decoder[String]].map(s =>
    values.withNameOption(s).getOrElse(values.values.head)
  )
  final def encodeEnumeratum[A <: EnumEntry]: Encoder[A] = implicitly[Encoder[String]].contramap(_.toString)

  trait GetTransient[T] {
    def get: T
  }

  /** value not serialized, obtained from some global source instead */
  case class Transient[T: GetTransient](value: T)

  object Transient {
    implicit def decoder[T: GetTransient]: Decoder[Transient[T]] = Decoder { r =>
      val value = implicitly[GetTransient[T]].get
      Transient(value)
    }
    implicit def encoder[T]: Encoder[Transient[T]] = new Encoder.PossiblyWithoutOutput[Transient[T]] {
      def producesOutputFor(value: Transient[T]): Boolean = false
      override def write(w: Writer, value: Transient[T]) = w
    }
  }
}

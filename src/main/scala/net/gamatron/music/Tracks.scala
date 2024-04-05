package net.gamatron.music

import enumeratum.*
import io.bullet.borer.*
import io.bullet.borer.derivation.MapBasedCodecs.*
import net.gamatron.borer.*

object Tracks {

  sealed trait Mood extends EnumEntry
  object Mood extends Enum[Mood] {
    override def values: IndexedSeq[Mood] = findValues
    case object merry extends Mood
    case object sad extends Mood
    case object quiet extends Mood
    case object neutral extends Mood

    implicit val decoder: Decoder[Mood] = decodeEnumeratum(Mood)

  }

  sealed trait Location extends EnumEntry
  object Location extends Enum[Location] {
    override def values: IndexedSeq[Location] = findValues
    case object pasture extends Location
    case object field extends Location
    case object forest extends Location
    case object village extends Location

    implicit val decoder: Decoder[Location] = decodeEnumeratum(Location)
  }

  case class Transitions(list: Seq[(String, Seq[String])] = Seq.empty) {
    def following(segment: String) = {
      list.find(_._1 == segment).map(_._2).getOrElse(Seq.empty)
    }
  }

  object Transitions {
    implicit val decoder: Decoder[Transitions] = implicitly[Decoder[Seq[(String, Seq[String])]]].map(Transitions.apply)
  }

  case class Track(
    mood: Mood = Mood.neutral, keywords: Seq[String] = Seq.empty, locations: Seq[Location] = Seq.empty,
    segments: Seq[(String, Int)] = Seq.empty,
    transitions: Transitions = Transitions()
  )

  object Track {
    implicit val decoder: Decoder[Track] = deriveDecoder
  }

  implicit val decoder: Decoder[Tracks] = deriveDecoder
}

import net.gamatron.music.Tracks.*

/** my doc string */
case class Tracks(tracks: Seq[(String, Track)])

import net.gamatron.music.Tracks
import org.opengrabeso.airframe.surface.Surface

object Test {
  def main(args: Array[String]): Unit = {
    val surface = Surface.of[Tracks]
    println(surface.docString)
  }
}

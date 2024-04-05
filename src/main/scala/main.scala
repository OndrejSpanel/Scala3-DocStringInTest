import net.gamatron.music.Tracks
import org.opengrabeso.airframe.surface.Surface

@main
def main(): Unit = {
  val surface = Surface.of[Tracks]
  println(surface.docString)
}

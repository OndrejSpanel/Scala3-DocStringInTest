/**
 * my doc string for Main
 *
 * @param tracks track listing
 * */

class Main(tracks: String)

object Main {
  def main(args: Array[String]): Unit = {
    val docString = Doc.of[Main]
    println(docString)
  }
}

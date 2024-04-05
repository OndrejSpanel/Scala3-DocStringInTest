/**
 * Doc of Test
 * */
class Test

object Test {
  def main(args: Array[String]): Unit = {
    val docString = Doc.of[Main]
    println(docString)
    val docStringTest = Doc.of[Test]
    println(docStringTest)

    if (docString.isEmpty || docStringTest.isEmpty) System.exit(1)
  }
}

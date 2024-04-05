ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.3"
//ThisBuild / scalaVersion := "2.13.12"

def getGithubToken: String = {
  val homeDir = System.getProperty("user.home")
  val gitConfig = new java.io.File(homeDir, ".gitconfig")
  if (gitConfig.exists()) {
    val source = scala.io.Source.fromFile(gitConfig)
    try {
      val lines = source.getLines().toList
      val githubSectionIndex = lines.indexWhere(_.trim == "[github]")
      if (githubSectionIndex != -1) {
        val tokenLineIndex = lines.indexWhere(_.trim.startsWith("token"), githubSectionIndex)
        if (tokenLineIndex != -1) {
          val tokenLine = lines(tokenLineIndex)
          val token = tokenLine.split("=")(1).trim
          return token
        }
      }
    } finally {
      source.close()
    }
  }
  sys.env.getOrElse("GITHUB_TOKEN", "")
}

resolvers += "GitHub Packages" at "https://maven.pkg.github.com/opengrabeso/_"

credentials += Credentials("GitHub Packages", "maven.pkg.github.com", "", getGithubToken)

lazy val root = (project in file("."))
  .settings(
    name := "Sandbox",
    libraryDependencies += "org.opengrabeso" %% "light-surface" % "0.5.3",
    libraryDependencies += "com.beachape" %% "enumeratum" % "1.7.2",
    libraryDependencies += "io.bullet" %% "borer-core" % "1.14.0",
    libraryDependencies += "io.bullet" %% "borer-derivation" % "1.14.0",
    scalacOptions ++= Seq(
      "-feature",
      "-deprecation",
      "-Xcheck-macros",
    ),

  )

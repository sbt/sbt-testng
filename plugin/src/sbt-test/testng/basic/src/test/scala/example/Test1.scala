package example;

import org.testng.annotations.Test
import java.nio.file.Files
import java.nio.file.Paths
import scala.util.Properties

class Test1 {

  @Test
  def foo(): Unit = {
    val version = Properties.versionNumberString
    val path = Paths.get(version + ".txt")
    Files.write(path, version.getBytes("UTF-8"))
  }

}

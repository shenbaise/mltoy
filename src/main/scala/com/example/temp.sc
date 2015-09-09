import annotation.tailrec
import collection.GenSeq
import collection.parallel.immutable.ParSeq
import java.io.File
val resourceDirectory = new File(".")

implicit class Dir(val file:File) extends AnyVal {
  def files: List[File] = if(file.isFile) List(file) else file.listFiles.toList

  def allFiles: List[File] = {
    def allFilesAcc(acc:List[File], f:File): List[File] = {
      if (f.isFile) acc :+ f
      else {
        var acc2 = acc
        for {
          subfile <- f.files
        } yield acc2 = allFilesAcc(acc2, subfile)
        acc2
      }
    }
    allFilesAcc(List(), file)
  }

  def allFiles(p: File => Boolean): List[File] = allFiles.filter(p)
}

implicit class FileOps(val file:File) extends AnyVal {
  def startsWith(prefix: String): Boolean = file.getName.startsWith(prefix)
  def endsWith(suffix: String): Boolean = file.getName.endsWith(suffix)
  def extension: String = file.getName.dropWhile(_ != '.') match {
    case "" => ""
    case suffix => suffix.tail
  }
}
println(resourceDirectory.getAbsolutePath)
//resourceDirectory.files.filter(f => !f.startsWith(".")).map(f => f.getPath).foreach(println)
//resourceDirectory.files.size
//resourceDirectory.allFiles.map(f => f.getPath).foreach(println)
//val extentions = List("html","css","png","scala")
def withExtensions(extensions: List[String]) = (f:File) => extensions.contains(f.extension)
resourceDirectory.allFiles(withExtensions(List("scala"))).mkString


























































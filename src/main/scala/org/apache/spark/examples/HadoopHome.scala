package org.apache.spark.examples

import org.omg.CORBA.Object

/**
 * @author zhaoziqiang1
 */
class HadoopHome() {
  
  def setHadoopDir(hadoop_dir:String){
    System.setProperty("hadoop.home.dir", hadoop_dir)
  }
}

object HadoopHome {
  System.setProperty("hadoop.home.dir", "D:\\hadoop-2.6.0")
  def apply() = new HadoopHome()
}
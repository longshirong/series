package cn.edu.cug.cs.gtl.series

import cn.edu.cug.cs.gtl.common.Pair
import cn.edu.cug.cs.gtl.config.Config
import cn.edu.cug.cs.gtl.io.File
import cn.edu.cug.cs.gtl.series.common.SeriesBuilder
import cn.edu.cug.cs.gtl.series.common.Series
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.*


class TimeSeriesTest {
    var testFileName:String =""
    @Before
    fun setUp() {
        Config.setConfigFile("series.properties")
        testFileName = (Config.getTestOutputDirectory()
                + File.separator + "test.series")
    }

    @Test
    fun copy() {
        val xs = doubleArrayOf(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0)
        val ys = doubleArrayOf(50.0, 10.0, 20.0, 30.0, 40.0, 70.0, 90.0, 10.0, 30.0, 40.0)
        val s1 = SeriesBuilder.build("test","value",xs,ys, Pair("label","1"))
        val s2 = SeriesBuilder.build("test","value",ys, Pair("label","1"))
        try {
            val f = FileOutputStream(testFileName)
            s1.write(f)
            f.close()
            val f2 = FileInputStream(testFileName)
            val s3 = SeriesBuilder.parseFrom(f2)
            Assert.assertArrayEquals(ys, s3.values, 0.001)
            Assert.assertArrayEquals(ys, s2.values, 0.001)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Test
    fun load() {
        val xs = doubleArrayOf(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0)
        val ys = doubleArrayOf(50.0, 10.0, 20.0, 30.0, 40.0, 70.0, 90.0, 10.0, 30.0, 40.0)
        val s1 = SeriesBuilder.build("test","value",xs,ys, Pair("label","1"))
        val s2 = SeriesBuilder.build("test","value",ys, Pair("label","1"))
        try {
            val baos = ByteArrayOutputStream()
            s1.write(baos)
            val bytes = baos.toByteArray()
            baos.close()
            val bais = ByteArrayInputStream(bytes)
            val s3 = SeriesBuilder.parseFrom(bais)
            Assert.assertArrayEquals(ys, s3.values, 0.001)
            Assert.assertArrayEquals(ys, s2.values, 0.001)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Test
    fun store() {
        val xs = doubleArrayOf(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0)
        val ys = doubleArrayOf(50.0, 10.0, 20.0, 30.0, 40.0, 70.0, 90.0, 10.0, 30.0, 40.0)
        val s1 = SeriesBuilder.build("test","value",xs,ys, Pair("label","1"))
        val s2 = SeriesBuilder.build("test","value",ys, Pair("label","1"))
        try {
            val bytes = s1.storeToByteArray()
            val s3 =SeriesBuilder.parseFrom(bytes)
            Assert.assertArrayEquals(ys, s3.values, 0.001)
            Assert.assertArrayEquals(ys, s2.values, 0.001)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}

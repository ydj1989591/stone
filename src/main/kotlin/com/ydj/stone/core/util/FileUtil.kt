package com.ydj.stone.core.util

import com.ydj.stone.core.enums.CoreExceptionEnum
import com.ydj.stone.core.exception.ServiceException
import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.channels.FileChannel

object  FileUtil {

    private val log = LoggerFactory.getLogger(FileUtil::class.java)

    fun toByteArray(filename: String): ByteArray {

        val f = File(filename)
        if (!f.exists()) {
            log.error("文件未找到！$filename")
            throw ServiceException(CoreExceptionEnum.FILE_NOT_FOUND)
        }
        var channel: FileChannel? = null
        var fs: FileInputStream? = null
        try {
            fs = FileInputStream(f)
            channel = fs.channel
            val byteBuffer = ByteBuffer.allocate(channel!!.size().toInt())
            while (channel.read(byteBuffer) > 0) {
                // do nothing
                // System.out.println("reading");
            }
            return byteBuffer.array()
        } catch (e: IOException) {
            throw ServiceException(CoreExceptionEnum.FILE_READING_ERROR)
        } finally {
            try {
                channel!!.close()
            } catch (e: IOException) {
                throw ServiceException(CoreExceptionEnum.FILE_READING_ERROR)
            }

            try {
                fs!!.close()
            } catch (e: IOException) {
                throw ServiceException(CoreExceptionEnum.FILE_READING_ERROR)
            }

        }
    }
}
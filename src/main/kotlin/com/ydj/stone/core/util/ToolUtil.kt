package com.ydj.stone.core.util

import cn.hutool.core.bean.BeanUtil
import cn.hutool.core.bean.copier.CopyOptions
import cn.hutool.core.date.DateUtil
import com.ydj.stone.config.properties.AppNameProperties
import com.ydj.stone.core.enums.CoreExceptionEnum
import com.ydj.stone.core.exception.ServiceException
import org.slf4j.LoggerFactory
import java.io.IOException
import java.io.PrintWriter
import java.io.StringWriter
import java.math.BigDecimal
import java.net.*
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*

class ToolUtil {
    companion object {
        /**
         * 默认密码盐长度
         */
        val SALT_LENGTH = 6

        /**
         * 获取随机字符,自定义长度
         *
         * @author fengshuonan
         * @Date 2018/3/18 21:55
         */
        fun getRandomString(length: Int): String {
            val base = "abcdefghijklmnopqrstuvwxyz0123456789"
            val random = Random()
            val sb = StringBuffer()
            for (i in 0 until length) {
                val number = random.nextInt(base.length)
                sb.append(base[number])
            }
            return sb.toString()
        }

        /**
         * md5加密(加盐)
         *
         * @author fengshuonan
         * @Date 2018/3/18 21:56
         */
        fun md5Hex(password: String, salt: String): String {
            return md5Hex(password + salt)
        }

        /**
         * md5加密(不加盐)
         *
         * @author fengshuonan
         * @Date 2018/3/18 21:56
         */
        fun md5Hex(str: String): String {
            try {
                val md5 = MessageDigest.getInstance("MD5")
                val bs = md5.digest(str.toByteArray())
                val md5StrBuff = StringBuffer()
                for (i in bs.indices) {
                    if (Integer.toHexString(0xFF and bs[i] as Int).length == 1)
                        md5StrBuff.append("0").append(Integer.toHexString(0xFF and bs[i] as Int))
                    else
                        md5StrBuff.append(Integer.toHexString(0xFF and bs[i] as Int))
                }
                return md5StrBuff.toString()
            } catch (e: Exception) {
                throw ServiceException(CoreExceptionEnum.ENCRYPT_ERROR)
            }

        }

        /**
         * 过滤掉掉字符串中的空白
         *
         * @author fengshuonan
         * @Date 2018/3/22 15:16
         */
        fun removeWhiteSpace(value: String): String {
            return if (ValidateUtil.isEmpty(value)) {
                ""
            } else {
                value.replace("\\s*".toRegex(), "")
            }
        }

        /**
         * 获取某个时间间隔以前的时间 时间格式：yyyy-MM-dd HH:mm:ss
         *
         * @author stylefeng
         * @Date 2018/5/8 22:05
         */
        fun getCreateTimeBefore(seconds: Int): String {
            val currentTimeInMillis = Calendar.getInstance().timeInMillis
            val date = Date(currentTimeInMillis - seconds * 1000)
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            return sdf.format(date)
        }

        /**
         * 获取异常的具体信息
         *
         * @author fengshuonan
         * @Date 2017/3/30 9:21
         * @version 2.0
         */
        fun getExceptionMsg(e: Throwable): String {
            val sw = StringWriter()
            try {
                e.printStackTrace(PrintWriter(sw))
            } finally {
                try {
                    sw.close()
                } catch (e1: IOException) {
                    e1.printStackTrace()
                }

            }
            return sw.buffer.toString().replace("\\$".toRegex(), "T")
        }

        /**
         * 获取应用名称
         *
         * @author fengshuonan
         * @Date 2018/5/12 上午10:24
         */
        fun getApplicationName(): String? {
            try {
                val appNameProperties = SpringContextHolder.getBean(AppNameProperties::class.java)
                return appNameProperties?.let { appNameProperties.name  }
            } catch (e: Exception) {
                val logger = LoggerFactory.getLogger(ToolUtil::class.java)
                logger.error("获取应用名称错误！", e)
                return ""
            }

        }

        /**
         * 获取ip地址
         *
         * @author yaoliguo
         * @Date 2018/5/15 下午6:36
         */
        fun getIP(): String? {
            try {
                val IFCONFIG = StringBuilder()
                val en = NetworkInterface.getNetworkInterfaces()
                while (en.hasMoreElements()) {
                    val intf = en.nextElement()
                    val enumIpAddr = intf.inetAddresses
                    while (enumIpAddr.hasMoreElements()) {
                        val inetAddress = enumIpAddr.nextElement()
                        if (!inetAddress.isLoopbackAddress && !inetAddress.isLinkLocalAddress && inetAddress.isSiteLocalAddress) {
                            IFCONFIG.append(inetAddress.hostAddress.toString() + "\n")
                        }

                    }
                }
                return IFCONFIG.toString()

            } catch (ex: SocketException) {
                ex.printStackTrace()
            }

            try {
                return InetAddress.getLocalHost().hostAddress
            } catch (e: UnknownHostException) {
                e.printStackTrace()
            }

            return null
        }

        /**
         * 拷贝属性，为null的不拷贝
         *
         * @author fengshuonan
         * @Date 2018/7/25 下午4:41
         */
        fun copyProperties(source: Any, target: Any) {
            BeanUtil.copyProperties(source, target, CopyOptions.create().setIgnoreNullValue(true))
        }

        /**
         * 判断是否是windows操作系统
         *
         * @author stylefeng
         * @Date 2017/5/24 22:34
         */
        fun isWinOs(): Boolean {
            val os = System.getProperty("os.name")
            return if (os.toLowerCase().startsWith("win")) {
                true
            } else {
                false
            }
        }

        /**
         * 获取临时目录
         *
         * @author stylefeng
         * @Date 2017/5/24 22:35
         */
        fun getTempPath(): String {
            return System.getProperty("java.io.tmpdir")
        }

        /**
         * 把一个数转化为int
         *
         * @author fengshuonan
         * @Date 2017/11/15 下午11:10
         */
        fun toInt(`val`: Any): Int? {
            if (`val` is Double) {
                val bigDecimal = BigDecimal(`val`)
                return bigDecimal.toInt()
            } else {
                return Integer.valueOf(`val`.toString())
            }

        }

        /**
         * 是否为数字
         *
         * @author fengshuonan
         * @Date 2017/11/15 下午11:10
         */
        fun isNum(obj: Any): Boolean {
            try {
                Integer.parseInt(obj.toString())
            } catch (e: Exception) {
                return false
            }

            return true
        }

        /**
         * 获取项目路径
         *
         * @author fengshuonan
         * @Date 2017/11/15 下午11:10
         */
        fun getWebRootPath(filePath: String): String {
            try {
                var path = ToolUtil::class.java.classLoader.getResource("")!!.toURI().path
                path = path.replace("/WEB-INF/classes/", "")
                path = path.replace("/target/classes/", "")
                path = path.replace("file:/", "")
                return if (ValidateUtil.isEmpty(filePath)) {
                    path
                } else {
                    "$path/$filePath"
                }
            } catch (e: URISyntaxException) {
                throw RuntimeException(e)
            }

        }

        /**
         * 获取文件后缀名 不包含点
         *
         * @author fengshuonan
         * @Date 2017/11/15 下午11:10
         */
        fun getFileSuffix(fileWholeName: String): String {
            if (ValidateUtil.isEmpty(fileWholeName)) {
                return "none"
            }
            val lastIndexOf = fileWholeName.lastIndexOf(".")
            return fileWholeName.substring(lastIndexOf + 1)
        }

        /**
         * 判断一个对象是否是时间类型
         *
         * @author stylefeng
         * @Date 2017/4/18 12:55
         */
        fun dateType(o: Any): String {
            return if (o is Date) {
                DateUtil.formatDate(o)
            } else {
                o.toString()
            }
        }

        /**
         * 当前时间
         *
         * @author stylefeng
         * @Date 2017/5/7 21:56
         */
        fun currentTime(): String {
            return DateUtil.formatDateTime(Date())
        }
    }
}
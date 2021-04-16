package com.zjzhd.springsecurity.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

/**
 * 加密工具
 * @author fanlz
 * @date 2021-04-14 10:50
 **/
public class Md5Util {

    private static final Logger logger = LoggerFactory.getLogger(Md5Util.class);

    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 获取 16位的MD5摘要，就是截取32位结果的中间部分
     */
    public static String MD5_16(String origin, String charset) {
        return MD5_32(origin, charset).substring(8,24);
    }

    /**
     * MD5算法，统一返回大写形式的摘要结果
     * String origin ：需要进行MD5计算的字符串
     * String charset ：MD5算法的编码
     */
    public static String MD5_32(String origin, String charset) {
        String resultString = null;
        try {
            // 1，创建MessageDigest对象
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (StringUtils.isBlank(charset)) {
                md.update(origin.getBytes());
            } else {
                md.update(origin.getBytes(charset));
            }
            // 3，计算摘要
            byte[] bytesResult = md.digest();
            // 4,将字节数组转换为16进制位
            resultString = byteArrayToHexString( bytesResult );
        } catch (Exception e) {
            logger.info("加密处理失败");
        }
        // 统一返回大写形式的字符串摘要
        return resultString.toUpperCase();

    }

    /**
     * 将字节数组里每个字节转成2个16进制位的字符串后拼接起来
     */
    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++){
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    /**
     * 将1个字节（1 byte = 8 bit）转为 2个十六进制位
     * 1个16进制位 = 4个二进制位 （即4 bit）
     * 转换思路：最简单的办法就是先将byte转为10进制的int类型，然后将十进制数转十六进制
     */
    private static String byteToHexString(byte b) {
        // byte类型赋值给int变量时，java会自动将byte类型转int类型，从低位类型到高位类型自动转换
        int n = b;
        // 将十进制数转十六进制
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        // d1和d2通过访问数组变量的方式转成16进制字符串；比如 d1 为12 ，那么就转为"c"; 因为int类型不会有a,b,c,d,e,f等表示16进制的字符
        return hexDigits[d1] + hexDigits[d2];
    }
}

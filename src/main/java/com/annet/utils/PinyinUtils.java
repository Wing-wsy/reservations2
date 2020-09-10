package com.annet.utils;

import net.sourceforge.pinyin4j.PinyinHelper;

/**
 * @Author: wsy
 * @Date: 2020/04/10
 * @Version: 1.0
 * @Description: 汉字首字母工具类
 */
public class PinyinUtils {

    public static String getPinYinHeadChar(String str) {
        StringBuilder convert = new StringBuilder();
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert.append(pinyinArray[0].charAt(0));
            } else {
                convert.append(word);
            }
        }
        return convert.toString().toUpperCase();
    }
}

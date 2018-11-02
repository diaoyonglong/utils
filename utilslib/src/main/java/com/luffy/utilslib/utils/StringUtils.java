package com.luffy.utilslib.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lvlufei on 2018/1/1
 *
 * @desc 字符串-辅助工具
 */
public class StringUtils {

    private StringUtils() {
    }

    public static StringUtils getInstance() {
        return StringUtilsHelper.mStringUtils;
    }

    private static class StringUtilsHelper {
        private static StringUtils mStringUtils;

        static {
            mStringUtils = new StringUtils();
        }
    }

    /**
     * 格式化简介内容
     *
     * @param str 需要处理的string
     * @return 处理后的数据
     */
    public String formatSummary(String str) {
        if (str != null) {
            str = str.replaceAll("【*", "");//替换来自服务器上的，特殊空格
            str = str.replaceAll("】", "");//替换来自服务器上的，特殊空格
            str = str.replaceAll("", "");//替换来自服务器上的，特殊空格
            str = str.replaceAll("】", "");//替换来自服务器上的，特殊空格
            str = str.replaceAll("  *", "");//
            str = str.replaceAll("——", "");//
            str = str.replaceAll("-", "");//
            str = str.replaceAll("<br />", "");//
            str = str.replaceAll("　　", "");//
            str = str.replaceAll("\r\n\r\n", "\n");//
            str = str.replace("\n  ", "");
            str = str.replace("\n\n", "\n");
            return str;
        }
        return "";
    }

    /**
     * @param input 输入的内容
     * @return 正则匹配取出中文, 返回数组
     */
    public List<String> getReplaceAll(String input) {
        if (input == null) return null;
        List<String> stringList = new ArrayList<>();
        Pattern p = Pattern.compile("([\u4e00-\u9fa5]+)");
        Matcher m = p.matcher(input);
        while (m.find()) {
            stringList.add(m.group(0).trim());
        }
        return stringList;
    }
}

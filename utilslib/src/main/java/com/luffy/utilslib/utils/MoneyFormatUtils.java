package com.luffy.utilslib.utils;

import java.text.DecimalFormat;

/**
 * Created by lvlufei on 2018/1/1
 *
 * @desc 金额转换-辅助工具
 */
public class MoneyFormatUtils {

    private MoneyFormatUtils() {
    }

    public static MoneyFormatUtils getInstance() {
        return MoneyFormatUtilsHelper.mMoneyFormatUtils;
    }

    private static class MoneyFormatUtilsHelper {
        private static MoneyFormatUtils mMoneyFormatUtils;

        static {
            mMoneyFormatUtils = new MoneyFormatUtils();
        }
    }

    /**
     * double转String,保留小数点后两位
     *
     * @param money
     * @return
     */
    public String doubleToString(double money) {
        return new DecimalFormat("0.00").format(money);
    }

    /**
     * double转String,保留小数点后几位，自己定义
     *
     * @param money
     * @param pattern 模式
     * @return
     */
    public String doubleToString(double money, String pattern) {
        return new DecimalFormat(pattern).format(money);
    }
}

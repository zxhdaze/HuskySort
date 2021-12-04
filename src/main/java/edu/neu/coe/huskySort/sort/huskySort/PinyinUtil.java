package edu.neu.coe.huskySort.sort.huskySort;

import java.util.Arrays;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtil {

    private static final HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();

    /**
     * 获取中文字的拼音（多音字，拼音上的符号代表第几声）
     * 如：空 -> kōng kòng
     *
     * @param word
     * @return
     */
    public static String[] getPinYinWithToneMark(char word) throws BadHanyuPinyinOutputFormatCombination {
        format.setToneType(HanyuPinyinToneType.WITH_TONE_NUMBER);
        format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
        return PinyinHelper.toHanyuPinyinStringArray(word, format);
    }

    public static String getPinYinByName(String name) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            try {
                sb.append(getPinYinWithToneMark(name.charAt(i))[0]);
                
            } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                badHanyuPinyinOutputFormatCombination.printStackTrace();
            }
        }
        //System.out.println(sb.toString());
        return sb.toString();
    }

    /**
     * get pinyin in V char type and tone number.
     * @param Chinese Chinese
     * @return string array of pinyin
     *         张三 -> ["zhang1", "san1"]
     */
    public static String[] getPinyinWithVWithTone(String Chinese) {
        // set format
        format.setToneType(HanyuPinyinToneType.WITH_TONE_NUMBER);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);

        String[] pinyin = new String[Chinese.length()];
        for (int i = 0; i < Chinese.length(); i++) {
            try {
                pinyin[i] = PinyinHelper.toHanyuPinyinStringArray(Chinese.charAt(i), format)[0];
            } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                badHanyuPinyinOutputFormatCombination.printStackTrace();
            }
        }

        return pinyin;
    }

    public static void main(String[] args) {
        String[] zhongwen = {"张三", "李四", "赵武"};
        Arrays.stream(zhongwen).forEach(name -> {
            String[] pinyin = getPinyinWithVWithTone(name);
            Arrays.stream(pinyin).forEach(s -> System.out.print(s + " "));
            System.out.println();
        });
    }

}


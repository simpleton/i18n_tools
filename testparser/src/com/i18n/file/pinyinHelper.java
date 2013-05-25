package com.i18n.file;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class pinyinHelper {

	public static String converterEname(String name)
			throws BadHanyuPinyinOutputFormatCombination {
		StringBuilder succeedPinyin = new StringBuilder();
		HanyuPinyinOutputFormat pin = new HanyuPinyinOutputFormat();
		pin.setCaseType(HanyuPinyinCaseType.UPPERCASE);// 大小写输出

		pin.setToneType(HanyuPinyinToneType.WITH_TONE_NUMBER);// 音调设置
		pin.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);// 音调
		char[] ar = name.toCharArray();
		for (int i = 0; i < ar.length; i++) {
			String[] a = PinyinHelper.toHanyuPinyinStringArray(ar[i], pin);
			if (null != a)
				succeedPinyin.append(a[0]);
			else
				succeedPinyin.append(ar[i]);
		}
		return succeedPinyin.toString();
	}

	public static String converterfirstStr(String str) {
		StringBuilder succeedPinyin = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(ch);
			if (null != pinyins)
				succeedPinyin.append(String.valueOf(pinyins[0].charAt(0)));
			else
				succeedPinyin.append(ch);
		}
		return succeedPinyin.toString();
	}
//
//	public static void main(String[] args) {
//		try {
//			System.out.println(converterEname("张解封口袋里1")); // 带音调输出：zhāng
//			System.out.println(converterfirstStr("你是张1吗？"));// 只取首字母
//
//		} catch (BadHanyuPinyinOutputFormatCombination e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}

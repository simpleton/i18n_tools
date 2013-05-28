package com.i18n.keymaker;

import com.i18n.parser.util.ILog;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class pinyinHelper implements IKeyMakerHelper{
	
	private static HanyuPinyinOutputFormat pin; 

	public String converterEname(String name)
			throws BadHanyuPinyinOutputFormatCombination {
		getPinyin();

		StringBuilder succeedPinyin = new StringBuilder();
		
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

	private HanyuPinyinOutputFormat getPinyin() {
		synchronized (pinyinHelper.class) {
			if (pin == null) {
				pin = new HanyuPinyinOutputFormat();
				pin.setCaseType(HanyuPinyinCaseType.UPPERCASE);// 大小写输出

				pin.setToneType(HanyuPinyinToneType.WITH_TONE_NUMBER);// 音调设置
				pin.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);// 音调
			}
		}
		return pin;		
	}
	
	public String converterfirstStr(String str) {
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

	@Override
	public String convert(String key) throws BadHanyuPinyinOutputFormatCombination {
		key = converterEname(key).replaceAll("(\\r|\\n|\"|\\\\| )", "").trim();
		return key;
	}
}

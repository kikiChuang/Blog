package com.arawn.util;

import java.util.ArrayList;
import java.util.List;

/**
 * �ַ���������
 * @author 
 *
 */
public class StringUtil {

	/**
	 * �ж��Ƿ��ǿ�
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if(str==null || "".equals(str.trim())) {
			return true;
		}
		return false;
	}
	
	/**
	 * �ж��Ƿ��ǿ�
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		if((str!=null) && !"".equals(str.trim())) {
			return true;
		}
		return false;
	}
	
	/**
	 * �ж��ַ����Ƿ����������
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		return str.matches("[0-9]+");
	}
	
	/**
	 * ��ʽ��ģ����ѯ
	 * @param str
	 * @return
	 */
	public static String formatLike(String str) {
		if(isNotEmpty(str)) {
			return "%"+str+"%";
		}
		return null;
	}
	
	/**
	 * ���˵�������Ŀո�
	 * @param list
	 * @return
	 */
	public static List<String> filterWhite(List<String> list) {
		List<String> resultList = new ArrayList<String>();
		for(String l:list) {
			if(isNotEmpty(l)) {
				resultList.add(l);
			}
		}
		return resultList;
	}

}

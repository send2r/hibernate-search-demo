/*
 * Copyright (c) 2007 Pyramid Consulting. All rights reserved.
 * Created on Jul 18, 2007
 * $Id: Utils.java,v 1.3 2008/10/27 04:26:39 son.nguyencong Exp $
 *
 */
package demo.hibernatesearch.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



/**
 * <Briefly describing the purpose of the class/interface...>
 * @author $tientrinh.nguyen$
 */
public class Utils {
	private static Log log = LogFactory.getLog(Utils.class);
	public static Integer parseToInteger(Object value) {
		Integer returnValue;
		if (value == null) {
			return new Integer(0);
		}
		try {
			returnValue = Integer.parseInt(String.valueOf(value));
		} catch (Exception ex) {
			return new Integer(0);
		}
		return returnValue;
	}
	/* Convert a string to ArrayList<String> */
    public static ArrayList<String> stringToArray(String inputVal, String delimiter) {
        if (inputVal == null || inputVal.length() <= delimiter.length()) {
            return null;
        }

        if (inputVal.indexOf(delimiter) == 0) {
            inputVal = inputVal.substring(delimiter.length());
        }
        if (inputVal.indexOf(delimiter) == inputVal.length() - delimiter.length()) {
            inputVal = inputVal.substring(0, inputVal.length() - delimiter.length());
        }
        String[] vals = inputVal.split(delimiter);
        ArrayList<String> returnList = new ArrayList<String>();
        try {
            for (int i = 0; i < vals.length; i++) {
            	returnList.add(vals[i].trim());
            }
        } catch (Exception ex) {
        	returnList = null;
        }
        return returnList;
    }

	
    public static String convertFromISO88591ToUTF8(String originalString){
        String result = "";
        if((originalString == null)||("".equals(originalString.trim()))){
            return result;
        }

        try {
            byte[] temp = originalString.getBytes("ISO-8859-1");
            result = new String (temp, "UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
	public static ArrayList mergeList(ArrayList firstList, ArrayList secondList) {
		ArrayList result = new ArrayList();
		for (int i = 0; i < firstList.size(); i++) {
			result.add(firstList.get(i));
		}
		for (int i = 0; i < secondList.size(); i++) {
			result.add(secondList.get(i));
		}
		return result;
	}

	public static String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);

		for (i = 0; i < src.length(); i++) {

			j = src.charAt(i);

			if (Character.isDigit(j) || Character.isLowerCase(j)
					|| Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append("%");
				if (j < 16)
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src
							.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src
							.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}
	
}

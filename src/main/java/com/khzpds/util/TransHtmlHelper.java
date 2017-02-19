package com.khzpds.util;

import java.beans.PropertyDescriptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.NotReadablePropertyException;

public class TransHtmlHelper {
	private static final Log log = LogFactory.getLog(TransHtmlHelper.class);
	/**
     * 把VO中的关键字值一律替换成ASCII码表示，同时把null换为""
     * 
     * @param obj 输入一个VO
     * @return 操作次数
     */
    public static int replaceToHtml(Object obj) {
        return replaceToHtml(obj, null);
    }

    /**
     * 把VO中的关键字值一律替换成ASCII码表示，同时把null换为""
     * 
     * @param obj 输入一个VO
     * @param ignoreName 
     * @return 操作次数
     */
    public static int replaceToHtml(Object obj, final String[] ignoreName) {
        return accessVo(obj, new ITransctVoField() {
            public int transctVo(BeanWrapper bw, PropertyDescriptor pd) {
                if (!pd.getName().equals("class")) {
					if (ignoreName != null && ignoreName.length > 0 && arrayContainString(ignoreName, pd.getName())) {
                        return 0;
                    }
                    String tempValue = (String) bw.getPropertyValue(pd.getName());
                    if (tempValue == null && "java.lang.String".equals(pd.getPropertyType().getName())) {
                        bw.setPropertyValue(pd.getName(), "");
                        return 1;
                    } else if("java.lang.String".equals(pd.getPropertyType().getName())){
                        bw.setPropertyValue(pd.getName(), replaceStringToHtml(tempValue));
                        return 1;
                    } else {
                        return 0;
                    }
                } else {
                    return 0;
                }
            }

        });

    }
    
    /**
     * 借助BeanWrapper循环Vo
     * 
     * @param obj 输入一个VO
     * @return 被替换的值个数
     */
    public static int accessVo(Object obj, ITransctVoField transctVoField) {
        int returnCount = 0;
        try {
            BeanWrapper bw = new BeanWrapperImpl(obj);
            PropertyDescriptor pd[] = bw.getPropertyDescriptors();
            for (int i = 0; i < pd.length; i++) {
                try {
                    returnCount += transctVoField.transctVo(bw, pd[i]);
                } catch (ClassCastException e) {
                    //log.error(e.getMessage(),e);
                    continue;
                } catch (NotReadablePropertyException e) {
                    //log.error(e.getMessage(),e);
                    continue;
                }
            }
        } catch (Exception e) {
        	log.error(e.getMessage(),e);
        }
        return returnCount;
    }
    
    /**
	 * 判断一个数组是否包含一个字符串
	 * 
	 * @param arrayString
	 * @param str
	 * @return
	 */
	public static boolean arrayContainString(String[] arrayString, String str) {
		if (arrayString == null || arrayString.length == 0) {
			return false;
		}
		for (int i = 0; i < arrayString.length; i++) {
			if (arrayString[i].equals(str))
				return true;
		}
		return false;
	}
	/**
	 * 过滤Html页面中的敏感字符
	 * 
	 * @param value
	 * @return
	 */
	public static String replaceStringToHtml(String value) {
		return replaceStringByRule(value, new String[][] { { "<", "&lt;" },
				{ ">", "&gt;" }, { "&", "&amp;" }, { "\"", "&quot;" },
				{ "'", "&#39;" }, { "\n", "<br/>" }, { "\r", "<br/>" } });
	}
	
	/**
	 * 过滤Html页面中的敏感字符，接受过滤的字符列表和转化后的值
	 * 
	 * @param value
	 * @return
	 */
	public static String replaceStringByRule(String value, String[][] aString) {
		if (value == null) {
			return ("");
		}
		char content[] = new char[value.length()];
		value.getChars(0, value.length(), content, 0);
		StringBuffer result = new StringBuffer(content.length + 50);

		for (int i = 0; i < content.length; i++) {
			boolean isTransct = false;
			for (int j = 0; j < aString.length; j++) {
				if (String.valueOf(content[i]).equals(aString[j][0])) {
					result.append(aString[j][1]);
					isTransct = true;
					break;
				}
			}
			if (!isTransct) {
				result.append(content[i]);
			}
		}
		return result.toString();
	}
}

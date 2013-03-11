package org.page;

import java.util.ArrayList;

/**
 * 列表模型粗提取类
 * @author Lius
 */
public class BaseList {
	/**
	 * 匹配所有以sa开始，以sb结束的字符串
	 * @param sa 开始标志字符串
	 * @param sb 结束标志字符串
	 * @param pa 匹配起点
	 * @return 多组
	 */
	public static String[] indexAll(String html,String sa,String sb)
	{
		int inta,intb=0;
		ArrayList<String> list=new ArrayList<String>();
		while((inta=html.indexOf(sa,intb))>=0&&(intb=html.indexOf(sb,inta))>0)
		{
			list.add(html.substring(inta+sa.length(), intb));
		}
		String rs[]=new String[list.size()];
		int i=0;
		for(String s:list)rs[i++]=s;
		return rs;
	}


}

package org.page;

import java.util.ArrayList;

/**
 * �б�ģ�ʹ���ȡ��
 * @author Lius
 */
public class BaseList {
	/**
	 * ƥ��������sa��ʼ����sb�������ַ���
	 * @param sa ��ʼ��־�ַ���
	 * @param sb ������־�ַ���
	 * @param pa ƥ�����
	 * @return ����
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

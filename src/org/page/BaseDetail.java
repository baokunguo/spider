package org.page;

/**
 * ��ϸģ�ʹ���ȡ��
 * @author Lius
 *
 */
public class BaseDetail {
	/**TODO ��ĳ����ҳĳ���ֲ�����ʱ
	 * ƥ����ɢ�ɶԱ�־
	 * @param tags:tags��ÿ�����ݶε�ͷβ
	 * @return [tags.length/2]
	 */
	public static String[] indexOf(String html,String[] tags){
		String[] rs=new String[tags.length/2];
		int i=0,ia=0,ib=0;
		while(i+1<tags.length){
			ia=html.indexOf(tags[i],ib);
			if(ia<0)break;
			ia+=tags[i].length();
			
			ib=html.indexOf(tags[i+1],ia);
			if(ib<0)break;
			rs[i/2]=html.substring(ia, ib);
			
			ib+=tags[i+1].length();
			i+=2;
		}
		return rs;
	}
	
	/**
	 * ƥ��һ����sa��ʼ����sb�������ַ���
	 * @param sa ��ʼ��־�ַ���
	 * @param sb ������־�ַ���
	 * @param pa ƥ�����
	 * @return һ��
	 */
	public String indexOf(String html,String sa,String sb,int pa){
		int inta,intb=pa;
		if((inta=html.indexOf(sa,intb))>=0&&(intb=html.indexOf(sb,inta))>0){
			return html.substring(inta+sa.length(), intb);
		}
		return "";
	}
}

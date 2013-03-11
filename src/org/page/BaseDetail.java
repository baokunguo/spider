package org.page;

/**
 * 详细模型粗提取类
 * @author Lius
 *
 */
public class BaseDetail {
	/**TODO 当某个网页某部分不存在时
	 * 匹配离散成对标志
	 * @param tags:tags是每个数据段的头尾
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
	 * 匹配一组以sa开始，以sb结束的字符串
	 * @param sa 开始标志字符串
	 * @param sb 结束标志字符串
	 * @param pa 匹配起点
	 * @return 一组
	 */
	public String indexOf(String html,String sa,String sb,int pa){
		int inta,intb=pa;
		if((inta=html.indexOf(sa,intb))>=0&&(intb=html.indexOf(sb,inta))>0){
			return html.substring(inta+sa.length(), intb);
		}
		return "";
	}
}

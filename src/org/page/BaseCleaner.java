package org.page;

import org.url.URLTool;

public class BaseCleaner {
	 /**
	  * TODO 把keywords 切开，进入队列才进行关键字判断，前期做非法字符判断
	  * 过滤、精取：list中找出属性为name所对应的值
	  * @param list list值以引用形式传入，注意备份数据
	  * @param name 属性名称
	  * @return !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!注意在注释、js中的结果
	  */
	public static String[] getValues(String list[],String name,String[] keywords , String[] illegals)
	{
		if(list.length==0)return list;
		int count=0;
		
		//提取目标元素，并直接使用list暂存
		String s;
		int inta=0,intb=0,intt;
		for(int i=0;i<list.length;i++)
		{
			s=list[i];
			try
			{
				if((inta=s.indexOf(name))>=0)
				{
					inta=s.indexOf("=", inta);//->等号应紧跟name后
					if(inta<0){list[i]="true";count++;continue;}
					while(s.charAt(++inta)==' ');//去掉空格
					//TODO 去掉头尾的换行(常见)，空格，制表等字符
					if(s.charAt(inta)=='"'){inta++;intb=s.indexOf("\"",inta);}	//"
					else if(s.charAt(inta)=='\''){inta++;intb=s.indexOf("'",inta);}	//'
					else	//无字符
					{
						intb=s.indexOf('>',inta);
						intt=s.indexOf(' ',inta);
						if(intt>0){if(intb>intt||intb<0)intb=intt;}
						else if(intb<0) intb=s.length();
					}
					if(intb>inta){
						list[i]=s.substring(inta, intb);
						count++;
					}else list[i]=null;
				}else {list[i]=null;}
			}catch(Exception ex){ list[i]=null; }
		}
		
		//过滤掉不含关键字的条目
		if(keywords!=null&&keywords.length>0){
			int k=0;
			for(int i=0;i<list.length;i++){
				if(list[i]==null)continue;
				while(list[i].indexOf(keywords[k])<0&&++k<keywords.length);
				if(k==keywords.length) {//与下逻辑判断不同
					list[i]=null;
					count--;
				}
				k=0;
			}
			
		}
		//过滤掉含非法字符的条目
		if(illegals!=null&&illegals.length>0){
			int k=0;
			for(int i=0;i<list.length;i++){
				if(list[i]==null)continue;
				while(list[i].indexOf(illegals[k])<0&&++k<illegals.length);
				if(k<illegals.length) {//与上逻辑判断不同
					list[i]=null;
					count--;
				}
				k=0;
			}
		}
		
		//转化成数组
		int k=0;
		String rs[]=new String[count];
		for(String str:list)
		{
			if(str!=null){
				rs[k++]=str;
			}
		}
		return rs;
	}
	
	/**
	 * 装配：将相对路径转化为绝对路径
	 * @param list 资源路径（相对+绝对）
	 * @param url 资源所在页面，转化为绝对路径用
	 */
	public static void replace(String[] list,String url){
		int i=0;
		String home=URLTool.getHome(url);
		for(String str:list){
			if(str.charAt(0)=='/')list[i]=home+str;
			else if(str.charAt(0)=='h'&&str.charAt(1)=='t'&&str.charAt(2)=='t'&&str.charAt(3)=='p')list[i]=str;
			else if(str.charAt(0)=='.'&&str.charAt(1)=='.'&&str.charAt(2)=='/'){
				list[i]=URLTool.getFather(url);
				if(list[i].length()<home.length())list[i]=home;
				list[i]+=str.substring(2);
			}else{
				int index=url.length();
				index=url.lastIndexOf('/', index);
				list[i]=url.substring(0, index);
				if(list[i].length()<home.length())list[i]=home;
				list[i]=list[i]+"/"+str;
			}
			i++;
		}
	}
	

	

}

package org.page;

import org.url.URLTool;

public class BaseCleaner {
	 /**
	  * TODO ��keywords �п���������вŽ��йؼ����жϣ�ǰ�����Ƿ��ַ��ж�
	  * ���ˡ���ȡ��list���ҳ�����Ϊname����Ӧ��ֵ
	  * @param list listֵ��������ʽ���룬ע�ⱸ������
	  * @param name ��������
	  * @return !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ע����ע�͡�js�еĽ��
	  */
	public static String[] getValues(String list[],String name,String[] keywords , String[] illegals)
	{
		if(list.length==0)return list;
		int count=0;
		
		//��ȡĿ��Ԫ�أ���ֱ��ʹ��list�ݴ�
		String s;
		int inta=0,intb=0,intt;
		for(int i=0;i<list.length;i++)
		{
			s=list[i];
			try
			{
				if((inta=s.indexOf(name))>=0)
				{
					inta=s.indexOf("=", inta);//->�Ⱥ�Ӧ����name��
					if(inta<0){list[i]="true";count++;continue;}
					while(s.charAt(++inta)==' ');//ȥ���ո�
					//TODO ȥ��ͷβ�Ļ���(����)���ո��Ʊ���ַ�
					if(s.charAt(inta)=='"'){inta++;intb=s.indexOf("\"",inta);}	//"
					else if(s.charAt(inta)=='\''){inta++;intb=s.indexOf("'",inta);}	//'
					else	//���ַ�
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
		
		//���˵������ؼ��ֵ���Ŀ
		if(keywords!=null&&keywords.length>0){
			int k=0;
			for(int i=0;i<list.length;i++){
				if(list[i]==null)continue;
				while(list[i].indexOf(keywords[k])<0&&++k<keywords.length);
				if(k==keywords.length) {//�����߼��жϲ�ͬ
					list[i]=null;
					count--;
				}
				k=0;
			}
			
		}
		//���˵����Ƿ��ַ�����Ŀ
		if(illegals!=null&&illegals.length>0){
			int k=0;
			for(int i=0;i<list.length;i++){
				if(list[i]==null)continue;
				while(list[i].indexOf(illegals[k])<0&&++k<illegals.length);
				if(k<illegals.length) {//�����߼��жϲ�ͬ
					list[i]=null;
					count--;
				}
				k=0;
			}
		}
		
		//ת��������
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
	 * װ�䣺�����·��ת��Ϊ����·��
	 * @param list ��Դ·�������+���ԣ�
	 * @param url ��Դ����ҳ�棬ת��Ϊ����·����
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

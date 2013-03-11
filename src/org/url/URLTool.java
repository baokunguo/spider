package org.url;

public class URLTool {

	public static String getTail(String source,char ch){
		int index=source.lastIndexOf(ch)+1;
		String file="";
		if(index>0){
			file=source.substring(index);
		}
		return file;
	}

	public static String getDomain(String url){
		String domain;
		int inta, intb;
		if((inta=url.indexOf("://"))<0){
			inta=0;
		}else{
			inta+=3;
		}
		intb=url.indexOf('/',inta);
		if(intb>0)domain=url.substring(inta,intb);
		else domain=url.substring(inta);
		return domain;
	}
	
	public static String getHome(String url){
		String home;
		boolean noHttp=false;
		int inta, intb;
		if((inta=url.indexOf("://"))<0){
			noHttp=true;
			inta=0;
		}else{
			inta+=3;
		}
		intb=url.indexOf('/',inta);
		if(intb>0)home=url.substring(0,intb);
		else home=url.substring(0);
		if(noHttp)home="http://"+home;
		return home;
		
	}
	
	public static String getFather(String url){
		int index=url.length();
		index=url.lastIndexOf('/', index);
		if(index>0){ 
			index=url.lastIndexOf("/",--index);
			return index>0 ? url.substring(0, index) : "";
		}
		return "";
		
		
	}
	public static String getName(String url){
		String name;
		int inta;
		if((inta=url.indexOf("://"))<0){
			inta=0;
		}else{
			inta+=3;
		}
		inta=url.indexOf('/',inta);
		if(inta>0){
			name=url.substring(inta+1);
			return name.replaceAll("[/?*:]","_");
		}
		else return null;
	}

}

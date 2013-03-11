package mvc;

import java.io.File;

import org.url.URLTool;


public class Rule {
	
	public int widthMin=0;	//图片最小宽度
	public int heightMin=0;	//图片最小高度
	public int widthMax;	//图片最小宽度
	public int heightMax;	//图片最小高度
	
	public boolean isBreadthFirst=true;
	public int depth=0;
	public int threadCount=1;	//125M Java heap space

	public String[] hrefKeywords=null;
	public String[] hrefIllegals=null;
	public String[] imageKeywords=null;
	public String[] imageIllegals=null;
	public String[] urls=null;
	
	public String domain=null;
	public String savePath=null;
	public String logPath="apps"+ File.separator +"logs"+File.separator;
	public String database=logPath+"log.mdb";
	
	public Rule(String[] urls){
		this.urls=urls;
		this.domain=URLTool.getDomain(urls[0]);
		this.savePath="apps"+ File.separator+"images"+File.separator+domain;
	}
	
	// 记录rule日志
	public void log(){
		
	}
	
	// 读取rule日志
	public static Rule readRule(String path){
		return null;
	}
	
	

}

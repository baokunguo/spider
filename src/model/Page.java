package model;

import org.page.BaseCleaner;
import org.page.BaseList;
import org.page.HtmlPage;


/**
 * <!-- <script 问题
 * 建立网页构建类，提供提取的[超链接][图片地址][个性化标签]的函数
 * @author Lius
 */
public class Page extends HtmlPage {
	public Page(String url,String charset)throws Exception {
		super(url,charset);
	}

	public String[] getLinks(String[] hrefKeywords, String[] hrefIllegals){
		String[] hrefsa=getTags("<a " , ">" , "href"  , hrefKeywords , hrefIllegals);	//TODO temp
		String[] hrefsA=getTags("<A " , ">" , "href"  , hrefKeywords , hrefIllegals);	//TODO temp
		String[] hrefs=new String[hrefsa.length+hrefsA.length];
		int index=0;
		for(int i=0;i<hrefsa.length;i++){
			hrefs[index++]=hrefsa[i];
		}
		for(int i=0;i<hrefsA.length;i++){
			hrefs[index++]=hrefsA[i];
		}
		if(hrefs.length==0){
			hrefs=getTags("<A " , ">" , "Href"  , hrefKeywords , hrefIllegals);
			if(hrefs.length==0){
				hrefs=getTags("<A " , ">" , "HREF"  , hrefKeywords , hrefIllegals);
			}
		}
		return hrefs;
	}
	public String[] getImages(String[] imageKeywords, String[] imageIllegals){
		String[] srcsi=getTags("<img " , ">" , "src"  , imageKeywords , imageIllegals);
		String[] srcsI=getTags("<IMG " , ">" , "src"  , imageKeywords , imageIllegals);
		String[] srcs=new String[srcsi.length+srcsI.length];
		int index=0;
		for(int i=0;i<srcsi.length;i++){
			srcs[index++]=srcsi[i];
		}
		for(int i=0;i<srcsI.length;i++){
			srcs[index++]=srcsI[i];
		}
		if(srcs.length==0){
			srcs=getTags("<Img " , ">" , "Src"  , imageKeywords , imageIllegals);
			if(srcs.length==0){
				srcs=getTags("<IMG " , ">" , "SRC"  , imageKeywords , imageIllegals);
			}
		}
		return srcs;
	}
	public String[] getTags(String taga,String tagb,String name,String[] keywords,String[] illegals)
	{
		try{
			if(super.html==null)super.getHtml();
			String[] list=BaseList.indexAll(html.toString(),taga,tagb);
			String[] values=BaseCleaner.getValues(list,name,keywords,illegals);
			BaseCleaner.replace(values,this.url);
			return values;
		}catch(Exception ex){ return null;}
	}
	

}

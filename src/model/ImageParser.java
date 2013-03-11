package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.url.Tree;
import org.url.URLTool;


public class ImageParser {
	private Tree hrefTree=null;	//��ַ�Ƿ���ʱ����
	private Tree imgTree=null;	//ͼƬ�Ƿ���ʹ�
	
	private Page page;
	//private boolean hasNextDepth;
	List<String> links=null;
	Map<String,String> images=null;

	public ImageParser(Page page,Tree hrefTree,Tree imgTree){
		this.page=page;
		this.hrefTree=hrefTree;
		this.imgTree=imgTree;
		this.links=new ArrayList<String>();
		this.images=new HashMap<String,String>();
		//System.out.println("ImageParser.init :"+page.getUrl());
	}

	public Map<String,String> getText(String[] imgIllegals) {
		String[] srcs=page.getImages(null , imgIllegals);
		
		//����ͼƬӦ��������
		//int count=0;
		for(String src:srcs){
			if(!imgTree.exist(src)){
				this.images.put(src , this.page.getUrl());
			}//else count++;
		}
		//System.out.println(" *"+count+" picture's size below");
		return this.images;
	}

	public List<String> getLinks(String[] hrefKeywords,String[] hrefIllegals) {
		//!-> doImages(page);
		String[] hrefs=page.getLinks(hrefKeywords , hrefIllegals);
		for(String href:hrefs){
			String hrefExtesion=URLTool.getTail(href, '.').toLowerCase();
			if(hrefExtesion.equals("jpg")&&!imgTree.exist(href)){	//������href�е�ͼƬ
				this.images.put(href , page.getUrl());
			}else if(!hrefTree.exist(href)){
				links.add(href);	//���浱ǰ��������ַ
			}
		}
		return this.links;
	}



}

package org.page;

/**
 * Detail-Link Page
 * @author Lius
 *
 */
public class DLPage extends HtmlPage {
	
	public DLPage(String url, String charset) throws Exception {
		super(url, charset);
		//��������listҳ���ҳ����ʵ�ַ
		//ȡ��ÿ�����ʵ�ַ�е�detail�ɷ�
	}
	/*
	private boolean isDetailFirst=true;
	private String detailBegin=null;
	private String detailEnd=null;
	private String[] detailTags=null;
	
	private String listBegin=null;
	private String listEnd=null;
	private String listTagBegin=null;
	private String listTagEnd=null;
	
	private ArrayList<String> urls=new ArrayList<String>();
	private ArrayList<String[]> details=new ArrayList<String[]>();
	

	public void setDetail(String da,String db,String[] tags){
		this.detailBegin=da;
		this.detailEnd=db;
		this.detailTags=tags;
	}
	public void setList(String la,String lb,String taga,String tagb){
		this.listBegin=la;
		this.listEnd=lb;
		this.listTagBegin=taga;
		this.listTagEnd=tagb;
	}
	public void setDetailFirst(boolean isDetailFirst){ this.isDetailFirst=isDetailFirst; }
	
	//���ظ���detailҳ����Ϣ
	public ArrayList<String[]> getText()throws Exception{
		return details;
	}
	
	//����list��ַ!->������һҳ�𣿣�
	public ArrayList<String> getUrls(){
		return urls;
	}
	*/

}

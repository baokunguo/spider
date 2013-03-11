package org.page;

public class DetailPage extends HtmlPage {
	public DetailPage(String url,String charset)throws Exception{
		super(url,charset);
	}
	public String[] getText(String[] tags)throws Exception{
		return BaseDetail.indexOf(super.getHtml().toString(), tags);
	}
}

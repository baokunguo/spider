package app.wanfang;

import java.net.URLEncoder;
import java.util.HashMap;

import org.http.Browser;
import org.page.BaseDetail;
import org.page.BaseList;
import org.page.DetailPage;

public class SearchPage {
	private final static String domain = "s.g.wanfangdata.com.cn";
	private final static String searchTag = "/Paper.aspx?q=";
	private final static String lunTag = "%20DBID%3AWF_XW&userid=hzdzkj";
	private final static String pageTag = "&p=";
	
	private final static String tagBegin = "<p class=\"p_style1\">";
	private final static String tagEnd = "</p>";
	
	private final static String titleBegin = "</table>";
	private final static String titleEnd = "<p class=\"pager_space\">";
	
	private final static String countBegin = "<t>¹²</t>&nbsp;";
	private final static String countEnd = "&nbsp;<t>Ò³</t>";
	
	private HashMap<String,String> indexMap;
	private HashMap<String,String> titleMap;
	private StringBuilder indexHtml;
	private StringBuilder titleHtml;
	private StringBuilder pageHtml;
	private int currentPage;

	public SearchPage(String keyword , int currentPage) throws Exception {
		this.currentPage = currentPage;
		keyword = URLEncoder.encode(keyword,"UTF-8");
		String path = searchTag+keyword+lunTag+pageTag+currentPage;

		String tags[] = {tagBegin,tagEnd,titleBegin,titleEnd,countBegin,countEnd};

		Browser b = new Browser(domain,path);
		StringBuilder html = b.getHtml();
		String ds[] = BaseDetail.indexOf(html.toString(), tags);
		
		int ti;
		String ts;
		
		String indexs[] = BaseList.indexAll(ds[0], "<a href='?q=", "</a>");
		indexMap = new HashMap<String,String>();
		indexHtml = new StringBuilder();
		for(int i=0;i<indexs.length;i++){
			ti = indexs[i].indexOf('\'');
			ts = indexs[i].substring(0,ti);
			indexMap.put(i+"", ts);
			indexHtml.append("<a href='?category="+i+"'>");
			indexHtml.append(indexs[i].substring(ti+3)+"</a>\n");
		}
		System.out.println(indexHtml);

		String titles[]=  BaseList.indexAll(ds[1], "<ul class=\"list_ul\">", "</ul>");
		titleMap = new HashMap<String,String>();
		titleHtml = new StringBuilder();
		String titleTags[];
		for(int i=0;i<titles.length;i++){
			tags[0] = "target=\"_blank\">";
			tags[1] = "</a>";
			tags[2] = "href='";
			tags[3] = "' ";
			tags[4] = "</li>";
			tags[5] = "<span style=''>";
			titleTags = BaseDetail.indexOf(titles[i], tags);
			titleMap.put(i+"", titleTags[1]);
			titleHtml.append("<a href='?title="+i+"'>");
			titleHtml.append(titleTags[0]+"</a><br/>\n");
			titleHtml.append(titleTags[2]+"\n");
		}
		System.out.println(titleHtml.toString());
		
		pageHtml = new StringBuilder();
		pageHtml.append("<t>¹²</t>&nbsp;"+ ds[2] +"&nbsp;<t>Ò³</t>\n");
		int totallPage = Integer.parseInt(ds[2]);
		int firstPage = currentPage-5;
		if(firstPage<1)firstPage = 1;
		int lastPage = firstPage+9;
		if(lastPage>totallPage)lastPage = totallPage;
		for(int i=firstPage;i<=lastPage;i++){
			pageHtml.append("<a href='?page="+i+"'>"+i);
			pageHtml.append("</a>&nbsp;&nbsp;\n");
		}
		System.out.println(pageHtml);
	}

}

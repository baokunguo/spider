package app.wanfang;

import java.net.URLDecoder;
import java.net.URLEncoder;

import org.data.Download;
import org.http.Browser;



public class Wanfang {
	public Wanfang() throws Exception{
		String domain="f.g.wanfangdata.com.cn";
		String path="/Fulltext.ashx?";
		path += "fileId=Periodical_xtgcllysj201011007&type=download";
		path += "&transaction=%7b%22ExtraData%22%3a%5b%5d%2c%22";
		path += "Transaction%22%3a%7b%22DateTime%22%3a%22%5c%2f";
		path += "Date(1312892814232%2b0800)%5c%2f%22%2c%22Id%22%3a%22a9178472-b7fb-41ea-bbc7-9f3a0150faaf%22%2c%22ProductDetail%22%3a%22Periodical_xtgcllysj201011007%22%2c%22SessionId%22%3a%2266e4d3d3-afeb-4f7a-9603-fbdd859e32eb%22%2c%22Signature%22%3a%22RA3cusQuqIqsnAP03FYpbqQUV9NbmMK6dPx0OJLPOnHVc8gpioi%5c%2fUWriq5jJ5M0n%22%2c%22TransferIn%22%3a%7b%22AccountType%22%3a%22Income%22%2c%22Key%22%3a%22PeriodicalFulltext%22%7d%2c%22TransferOut%22%3a%7b%22AccountType%22%3a%22GTimeLimit%22%2c%22Key%22%3a%22hzdzkj%22%7d%2c%22Turnover%22%3a3.00000%2c%22User%22%3anull%7d%2c%22";
		path += "TransferOutAccountsStatus%22%3a%5b%5d%7d";
		
		//Browser b = new Browser(domain,path);
		//b.getFile();
		//http://f.g.wanfangdata.com.cn
		//domain="f.g.wanfangdata.com.cn";
		//path="/view/政府不同应急管理模式下群体性突发事件的演化分析.aspx?ID=Periodical_xtgcllysj201011007";
		
		Browser b = new Browser(domain,path);
		b.getFile();
		//new SearchPage("政府",20);
		
	}

}

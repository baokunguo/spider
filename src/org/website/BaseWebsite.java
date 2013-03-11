package org.website;

/**
 * 
 * @author Lius
 * TODO 配置过滤器，日志管理器，数据处理器
 */
public class BaseWebsite {
	protected String url=null;
	protected String charset=null;
	public BaseWebsite(String url,String charset){
		this.url=url;
		this.charset=charset;
	}
}

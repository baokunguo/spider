package org.url;

import java.util.List;

public interface URLManager {
	/* 控制数据弹出的顺序，可控制深度优先还是宽度优先 */
	String[] next();
	void add(String url,int depth);
	void addAll(List<String> list,int depth);

}

package org.url;

import java.util.List;

public interface URLManager {
	/* �������ݵ�����˳�򣬿ɿ���������Ȼ��ǿ������ */
	String[] next();
	void add(String url,int depth);
	void addAll(List<String> list,int depth);

}

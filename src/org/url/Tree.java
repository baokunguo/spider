package org.url;

import java.util.ArrayList;
import java.util.HashMap;

class Property{
	int indegree;	//入度，0表示非可访问节点
	HashMap<String,Property> child;	//Set,Map不能存放重复内容，因此可以解决用建树片面重复的问题
	public Property(){
		this.indegree = 0;
		this.child = null;
	}
}

public class Tree {
	private Property property;	//存储头结点及其兄弟节点，visit和indegree不用
	
	
	public Tree(String s)
	{
		this.property = new Property();
		Property root0 = new Property();
		root0.indegree = 1;
		property.child = new HashMap<String,Property>();
		property.child.put(s, root0);
	}

	public synchronized int insert(String url)
	{
		String[] address=url.split("/");
		HashMap<String, Property> m = property.child;
		Object obj = null;
		int i;
		for(i=0;i<address.length;i++)
		{
			//返回指定键所映射的值；如果对于该键来说，此映射不包含任何映射关系，则返回 null。
			obj = m.get(address[i]);
			if(obj != null)	//找到,继续往下找
				m = ((Property)obj).child;
			else 
				break;
		}//for
		
		Property p = null;
		if(i == address.length)	//找到
			p = (Property)obj;
		else{	//没找到该元素则插入剩余元组
			for(;i<address.length;i++){
				p = new Property();
				p.child = new HashMap<String,Property>();
				m.put(address[i], p);
				m = p.child;
			}
		}
		return ++p.indegree;
	}

	public void print()
	{
		this.printf(0);
	}
	private void printf(int space)
	{
		System.out.print(space+1);
		for(int i=0;i<=space;i++)System.out.print(" + ");
		Tree tree=this;
		do{
			System.out.print(tree.info+"|"+tree.visit+" ");
			tree=tree.brother;
		}while(tree!=null);
		System.out.println();
		
		if(this.brother!=null) this.brother.printf(space+1);
		if(this.child!=null) this.child.printf(space+1);
	}
	
	
	public static void testThis()
	{
		Tree tree=new Tree("");
		String ua="www.gznc.edu.cn/yxsz/jjglxy/book/Java_api/java/lang/String.html";
		String ub="www.gznc.edu.cn/yxsz/jjglxy/book/Java_api/java/lang/Strin.html";
		String uc="www.gznc.edu.cn/yxsz/jjglxy/bodok/Java_api/java/lang/String.html";
		String ud="www.gznc.edu.cn/yxsz/jjglxy/book/Java_api/java/lang/String.html";//
		String uf="www.gznc.edu.cn";///yxsz/jjglxy/book/Java_api/java/lang/String.html
		String ss=ua;
		System.out.println(tree.insert(ss));
		ss=ub;
		System.out.println(tree.insert(ss));
		ss=uc;
		System.out.println(tree.insert(ss));
		ss=ud;
		System.out.println(tree.insert(ss));
		ss=uf;
		System.out.println(tree.insert(ss));
		tree.print();
	}

}

package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 数据输出处理实体类：图片，访问网址，异常
 * @author Lius
 * TODO 控制图片处理线程数
 */
public class Log  {
	Connection conn=null;
	Statement stmt=null;
	public Log(String database) throws Exception{
		//ImageDesktop desktop=new ImageDesktop();
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");   
		String dburl ="jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ="+database;//此为NO-DSN方式   
		conn=DriverManager.getConnection(dburl);   
		stmt=conn.createStatement(); 
	}

	public void addVisitedLog(String url,int outdegree) {	//http://www.baidu.com/
		try {
			String sql="insert into visited_url ([url],[outdegree]) values ('";
			sql+=url+"','";
			sql+=outdegree+"')";
			stmt.executeUpdate(sql);
		} catch (Exception sqlex) {
			System.out.println("Log.addVisitedLog:" + sqlex.toString());
		}
	}
	
	public void addException(String from , Exception ex) {
	    try {
	    	String sql="insert into exception ([from],[exception]) values ('";
	    	sql+=from+"','";
	    	sql+=ex.toString()+"')";
	    	stmt.executeUpdate(sql);
		} catch (Exception sqlex) {
			System.out.println("Log.addException:" + sqlex.toString());
		}
	}

	public void addSaveSuccessLog(String src,String url,int size,String path){
		try {
	    	String sql="insert into image_success ([src],[url],[size],[path]) values ('";
	    	sql+=src+"','";
	    	sql+=url+"','";
	    	sql+=size+"','";
	    	sql+=path+"')";
	    	stmt.executeUpdate(sql);
		} catch (Exception sqlex) {
			System.out.println("Log.addSaveSuccessLog:" + sqlex.toString());
		}
	}
	public void addSaveSmallLog(String src,String url,int size){
		if(size<=0)return;
		try {
	    	String sql="insert into image_small ([src],[url],[size]) values ('";
	    	sql+=src+"','";
	    	sql+=url+"','";
	    	sql+=size+"')";
	    	stmt.executeUpdate(sql);
		} catch (Exception sqlex) {
			System.out.println("Log.addSaveSmallLog:" + sqlex.toString());
		}
	}

	public void close(){
		try{
			stmt.close();   
			conn.close();
		}
		catch(SQLException sqlex) {
			System.out.println("Log.close:"+sqlex.toString());
		}
	}

	public String getTime(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	    return df.format(new Date());
	}
	
}

package org.data;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class Access {
	protected String database=null;
	public Access(String database)
	{
		this.database=database;//初始化数据库名称
	}
	
	/**
	 * 更新数据库
	 * @param s 操作数据库语句
	 * @return 更新是否成功
	 */
	public synchronized boolean doCommand(String s)
	{
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");   
			String dburl ="jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ="+this.database+".mdb";//此为NO-DSN方式   
			Connection conn=DriverManager.getConnection(dburl);   
			Statement stmt=conn.createStatement(); 
	        stmt.executeUpdate(s);
			stmt.close();   
			conn.close();
		}
		catch(Exception ex)
		{
			System.out.println("Db.doCommand:"+ex.toString());
			return false;
		}
		return true;
	}
	
	/**
	 * 更新数据库
	 * @param list 多条操作数据库语句
	 * @return 更新是否成功
	 */
	public synchronized boolean doCommand(String[] list)
	{
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");   
			String dburl ="jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ="+this.database+".mdb";//此为NO-DSN方式   
			Connection conn=DriverManager.getConnection(dburl);   
			Statement stmt=conn.createStatement();
			for(String s:list)stmt.executeUpdate(s);
			stmt.close();   
			conn.close();
		}
		catch(Exception ex)
		{
			System.out.println("Db.doCommand:"+ex.toString());
			return false;
		}
		return true;
	}
	
	
	/**
	 * 查询数据库
	 * @param s 查询语句
	 * @return 查询结果
	 */
	public String[][] getTable(String s)
	{
		Statement stmt=null;
		Connection conn=null;
		ResultSet rs=null;
		String ss[][]=null;
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");   
			String dburl ="jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ="+this.database+".mdb";   
			conn=DriverManager.getConnection(dburl);   
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs=stmt.executeQuery(s);
			ResultSetMetaData rsmd = rs.getMetaData() ; 
			rs.last(); 
			int rows=rs.getRow(); 
			int columns=rsmd.getColumnCount();
			ss=new String[rows][columns];
			rs.beforeFirst();
			int row=0;
			while(rs.next())
			{
				for(int column=1;column<=columns;column++)
				{
					ss[row][column-1]=rs.getString(column);
				}
				row++;
			}	
			rs.close();   
			stmt.close();   
			conn.close();
		}
		catch(Exception ex)
		{
			System.out.println("Db.getTable:"+ex.toString());
		}
		return ss;
	}
	
	public boolean saveImage(String sql,String filename)
	{
		 try {
			   Class.forName("org.gjt.mm.mysql.Driver").newInstance();
			   String url = "jdbc:mysql://localhost/img?user=root&password=root&useUnicode=true&characterEncoding=gbk";
			   Connection conn = DriverManager.getConnection(url);
			   Statement stmt = conn.createStatement();
			   //stmt.execute("insert   into   imgt (id)   values   (5)");
			   stmt.close();
			   PreparedStatement pstmt = null;
			   File file = new File(filename);
			   InputStream photoStream = new FileInputStream(file);
			     
			   sql = "INSERT INTO imgtable  (img) VALUES (?)";//////////
			   
			   pstmt = conn.prepareStatement(sql);
			   pstmt.setBinaryStream(1, photoStream, (int) file.length());
			   pstmt.executeUpdate();
			   pstmt.close();
			   conn.close();
			  } catch (Exception e) {
			   e.printStackTrace();
			  }
		return true;
	}
}

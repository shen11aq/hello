import java.sql.*;
import java.util.*;

public class SearchAction {
	
	//作者的 所有图书
	private HashMap<String,String> allBooks = new HashMap<String,String>();
	public HashMap<String,String> getAllBooks()
	{
		return allBooks;
	}
	//查询使用的 作者名
	private String authorName;
	public void setAuthorName(String author)
	{
		this.authorName = author;
	}
	public String getAuthorName()
	{
		return authorName;
	}

	//@@function: 查询作者所有图书
	//@@parameter : authorName --- search.jsp
	//@output : allBooks  --- bookinfo.jsp
	public String execute()
	{
		try{
			Class.forName("com.mysql.jdbc.Driver");
			//Connection con = DriverManager.getConnection("jdbc:mysql://w.rdc.sae.sina.com.cn:3307/app_bmmp","carpela","214317918605");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BookDB","root","123456");
	        //获得 AuthorID 查询Book表
	        Statement stm = con.createStatement();
	        String id = null;
	        ResultSet rs = stm.executeQuery("select * from Author where name=\""+authorName+"\"");
	        while(rs.next())
	        {
	        	id = rs.getString(1);
	        }
	        rs.close();
	        stm.close();
	        System.out.println(id);
	        //获得 该作者所有图书题目
	        stm = con.createStatement();
	        rs = stm.executeQuery("select * from Book where AuthorID=\""+id+"\"");
	        while(rs.next())
	        {
	        	//System.out.println(rs.getString(2));
	        	//图书 Title
		        allBooks.put(rs.getString(1),rs.getString(2));
	        }
	        rs.close();
		}catch(SQLException e)
		{
			System.out.println("Error:数据库连接或执行错误!");
			return "FAILURE";
		}catch(ClassNotFoundException e)
		{
			System.out.println("Error:数据库驱动加载错误!");
			return "FAILURE";
		}
        return "SUCCESS";
	}
}

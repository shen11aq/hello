import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DetailAction {

	private Author au = null;
	private Book  bo = null;
	public Author getAu()
	{
		return au;
	}
	public Book getBo()
	{
		return bo;
	}
	
	private String title;
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	private String author;
	public String getAuthor()
	{
		return author;
	}
	public void setAuthor(String author)
	{
		this.author = author;
	}
	public String execute()
	{
	try{
		Class.forName("com.mysql.jdbc.Driver");
		//Connection con = DriverManager.getConnection("jdbc:mysql://w.rdc.sae.sina.com.cn:3307/app_bmmp","carpela","214317918605");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BookDB","root","123456");
        //获取 作者 详细信息
        Statement stm = con.createStatement();
        System.out.println(title);
        System.out.println(author);
        ResultSet rs = stm.executeQuery("select * from Author where Name="+"\""+author+"\"");
        au = new Author();
        while(rs.next())
        {
        	au.setAuthrID(rs.getString(1));
        	au.setName(rs.getString(2));
            au.setCountry(rs.getString(3));
        }
        rs.close();
        stm.close();
        //获取 图书 详细信息
        stm = con.createStatement();
        rs = stm.executeQuery("select * from Book where Title="+"\""+title+"\" and AuthorID="+"\""+au.getAuthorID()+"\"");
        bo = new Book();
        while(rs.next())
        {
        	bo.setISBN(rs.getString(1));
	        bo.setTitle(rs.getString(2));
	        bo.setAuthorID(rs.getString(3));
	        bo.setPublisher(rs.getString(4));
	        bo.setPublishDate(rs.getString(5));
	        bo.setPrice(rs.getFloat(6));
        }
        rs.close();
        stm.close();
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
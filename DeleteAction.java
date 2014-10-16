import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteAction {

	private String isbn;
	public String getIsbn()
	{
		return isbn;
	}
	public void setIsbn(String isbn)
	{
		this.isbn = isbn;
	}
	public String execute()
	{
	try{
		Class.forName("com.mysql.jdbc.Driver");
		//Connection con = DriverManager.getConnection("jdbc:mysql://w.rdc.sae.sina.com.cn:3307/app_bmmp","carpela","214317918605");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BookDB","root","123456");
        //获取 作者 详细信息
        Statement stm = con.createStatement();
        System.out.println(isbn);
        int result = stm.executeUpdate("delete from Book where ISBN="+"\""+isbn+"\"");
        System.out.println(result);
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
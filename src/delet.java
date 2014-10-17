import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class delet {
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
		Connection con = DriverManager.getConnection("jdbc:mysql://w.rdc.sae.sina.com.cn:3307/app_hitzhanghang","1j1mn12znl","x05kwili103y5lkiiwm0y4xh1myjlxz45izx4wjm");
		Statement stm = con.createStatement();
        //System.out.println(isbn);
		System.out.println(isbn);
        int result = stm.executeUpdate("delete from book where ISBN="+"\""+isbn+"\"");
        if (result==0){
        	return "FAILURE";
        }
        System.out.println(result);
        stm.close();
	}catch(SQLException e)
	{
		//System.out.println("Error:数据库连接或执行错误!");
		e.printStackTrace();
		return "FAILURE";
	}catch(ClassNotFoundException e)
	{
		e.printStackTrace();
		return "FAILURE";
	}
	return "SUCCESS";
}

}

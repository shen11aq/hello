import java.sql.*;
import java.util.*;


public class search {
	private List<book> baas=new ArrayList<book>();
	private String Name;
	private book one=new book();
	public List<book> getBaas() {
		return baas;
	}
	public void setBaas(List<book> baas) {
		this.baas = baas;
	}
	
	public book getOne(){
		return one;
	}
	public void setOne(book one){
		this.one = one;
	}
	public String getName(){
		return Name;
	}
	public void setName(String Name){
		this.Name = Name;
	}
	public String execute ()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://w.rdc.sae.sina.com.cn:3307/app_hitzhanghang","1j1mn12znl","x05kwili103y5lkiiwm0y4xh1myjlxz45izx4wjm");
			Statement stm = con.createStatement();
			String sql;
			sql = "SELECT book.ISBN AS isbn,book.Title AS title,book.Publisher AS publisher,book.PublishDate AS publishdate,book.Price AS price,author.Name AS name FROM book JOIN author  ON author.AuthorID = book.AuthorID where name = '"+Name+"'";
			ResultSet rs = stm.executeQuery(sql);
			
			//book baa = null;
			//int i=0;
			while(rs.next()) {
				//baa = new book();
				one.setISBN(rs.getString("isbn"));
				one.setName(rs.getString("name"));
				one.setTitle(rs.getString("title"));
				one.setPublisher(rs.getString("publisher"));
				one.setPublishDate(rs.getString("publishdate"));
				one.setPrice(rs.getFloat("price"));
				baas.add(one);
				//i++;
			}
				rs.close();
		        stm.close();
		}catch(SQLException e)
		{
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

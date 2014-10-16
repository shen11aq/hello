
public class Book {
	private String title;
	private String authorID;
	private String ISBN;
	private String publisher;
	private String publishDate;
	private float price;
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getTitle()
	{
		return title;
	}
	
	public void setAuthorID(String authorID)
	{
		this.authorID = authorID;
	}
	public String getAuthorID()
	{
		return authorID;
	}
	
	public void setISBN(String ISBN)
	{
		this.ISBN = ISBN;
	}
	public String getISBN()
	{
		return ISBN;
	}
	
	public void setPublisher(String publisher)
	{
		this.publisher = publisher;
	}
	public String getPublisher()
	{
		return publisher;
	}
	
	public void setPublishDate(String publishDate)
	{
		this.publishDate = publishDate;
	}
	public String getPublishDate()
	{
		return publishDate;
	}
	
	public void setPrice(float price)
	{
		this.price = price;
	}
	public float getPrice()
	{
		return price;
	}
}

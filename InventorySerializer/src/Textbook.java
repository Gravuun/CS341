import java.io.Serializable;

public class Textbook implements Serializable{
	private static final long serialVersionUID = 156789L;
	
	private Integer sku;
	private String title;
	private Double price;
	private Integer quantity;
	
	public Textbook(Integer sku, String title, Double price, Integer quantity) {
		this.sku = sku;
		this.title = title;
		this.price = price;
		this.quantity = quantity;
	}
	
	public Integer getSKU() {
		return this.sku;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public Double getPrice() {
		return this.price;
	}
	
	public int getQuantity() {
		return this.quantity;
	}
	
	public void setQuantity(Integer q) {
		this.quantity = q;
	}
	
	public void addQuantity(Integer q) {
		this.quantity += q;
	}
	
	public void setPrice(Double p) {
		this.price = p;
	}
	
	public String print() {
		return Integer.toString(this.sku) + "\t" + this.title + "\t$" + this.price + "\t" + this.quantity + " units\n";
	}
}

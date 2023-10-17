package model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "product")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer productId;
	private String  productName;
	private Integer productAmount;
	public Integer getId() {
		return id;
	}
	public Integer getProductId() {
		return productId;
	}
	public String getProductName() {
		return productName;
	}
	public Integer getProductAmount() {
		return productAmount;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public void setProductAmount(Integer productAmount) {
		this.productAmount = productAmount;
	}

	
	
}

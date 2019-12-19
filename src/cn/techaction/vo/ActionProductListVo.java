package cn.techaction.vo;

import java.math.BigDecimal;

public class ActionProductListVo {
	private Integer id;
	private String name;
	private BigDecimal price;
	private Integer status;
	private String statusDesc;
	private String productCategory;
	private String partsCategory;
	private String iconUrl;
	private Integer hot;
	private String hotDesc;
	
	
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	public Integer getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public Integer getStatus() {
		return status;
	}
	public String getProductCategory() {
		return productCategory;
	}
	public String getPartsCategory() {
		return partsCategory;
	}
	public Integer getHot() {
		return hot;
	}
	public String getHotDesc() {
		return hotDesc;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
	public void setPartsCategory(String partsCategory) {
		this.partsCategory = partsCategory;
	}
	public void setHot(Integer hot) {
		this.hot = hot;
	}
	public void setHotDesc(String hotDesc) {
		this.hotDesc = hotDesc;
	}
	
}

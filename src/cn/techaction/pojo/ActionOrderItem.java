package cn.techaction.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class ActionOrderItem {
	private Integer id;
	private Integer uid;
	private long order_no;
	private int goods_id;
	private String goods_name;
	private String icon_url;
	private BigDecimal price;
	private Integer quantity;
	private BigDecimal total_price;
	private Date updated;
	private Date created;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public long getOrderNo() {
		return order_no;
	}
	public void setOrderNo(long order_no) {
		this.order_no = order_no;
	}
	public int getGoodsId() {
		return goods_id;
	}
	public void setGoodsId(int goods_id) {
		this.goods_id = goods_id;
	}
	public String getGoodsName() {
		return goods_name;
	}
	public void setGoodsName(String goods_name) {
		this.goods_name = goods_name;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getTotalPrice() {
		return total_price;
	}
	public void setTotalPrice(BigDecimal total_price) {
		this.total_price = total_price;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getIconUrl() {
		return icon_url;
	}
	public void setIconUrl(String icon_url) {
		this.icon_url = icon_url;
	}
	
}

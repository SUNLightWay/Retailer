package cn.techaction.pojo;

import java.util.Date;
import java.util.List;


public class ActionParam {

	private Integer id;
	private Integer parent_id;
	private String name;
	private boolean status;
	private Integer sort_order;
	private Integer level;
	private Date created;
	private Date updated;
	private List<ActionParam> chlidren;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getParent_id() {
		return parent_id;
	}
	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Integer getSort_order() {
		return sort_order;
	}
	public void setSort_order(Integer sort_order) {
		this.sort_order = sort_order;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	public List<ActionParam> getChlidren() {
		return chlidren;
	}
	public void setChlidren(List<ActionParam> chlidren) {
		this.chlidren = chlidren;
	}
	
	
	
	
}

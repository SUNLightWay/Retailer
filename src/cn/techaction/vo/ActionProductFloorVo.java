package cn.techaction.vo;

import java.util.List;

import cn.techaction.pojo.ActionProduct;

public class ActionProductFloorVo {

	private List<ActionProduct> oneFloor; 
	private List<ActionProduct> twoFloor; 
	private List<ActionProduct> threeFloor; 
	private List<ActionProduct> fourFloor;
	
	public List<ActionProduct> getOneFloor() {
		return oneFloor;
	}
	public void setOneFloor(List<ActionProduct> oneFloor) {
		this.oneFloor = oneFloor;
	}
	public List<ActionProduct> getTwoFloor() {
		return twoFloor;
	}
	public void setTwoFloor(List<ActionProduct> twoFloor) {
		this.twoFloor = twoFloor;
	}
	public List<ActionProduct> getThreeFloor() {
		return threeFloor;
	}
	public void setThreeFloor(List<ActionProduct> threeFloor) {
		this.threeFloor = threeFloor;
	}
	public List<ActionProduct> getFourFloor() {
		return fourFloor;
	}
	public void setFourFloor(List<ActionProduct> fourFloor) {
		this.fourFloor = fourFloor;
	} 
	
	
}

package cn.techaction.dao;

import cn.techaction.pojo.ActionAddress;

public interface ActionAddressDao {
	/**
	 * 根据id获得地址信息
	 * @param id
	 * @return
	 */
	public ActionAddress findAddrById(Integer id);
}

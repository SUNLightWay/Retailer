package cn.techaction.dao;

import cn.techaction.pojo.ActionAddress;

public interface ActionAddressDao {
	/**
	 * ����id��õ�ַ��Ϣ
	 * @param id
	 * @return
	 */
	public ActionAddress findAddrById(Integer id);
}

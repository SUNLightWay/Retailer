package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionAddress;

public interface ActionAddressDao {
	/**
	 * ����id��õ�ַ��Ϣ
	 * @param id
	 * @return
	 */
	public ActionAddress findAddrById(Integer id);
	/**
	 * ����id��ѯ�ջ��˵�ַ��Ϣ
	 * @param addrId
	 * @return
	 */
	public ActionAddress findAddrsById(Integer addrId);
	/**
	 * ��ѯ�Ƿ����Ĭ�ϵ�ַ
	 * @param uid
	 * @return
	 */
	public int findDefaultAddrByUserId(Integer userId);
	/**
	 * �����ջ��˵�ַ��Ϣ
	 * @param addr
	 * @return
	 */
	public int insertAddress(ActionAddress addr);
	/**
	 * �����ռ��˵�ַ��Ϣ
	 * @param addr
	 * @return
	 */
	public int updateAddress(ActionAddress addr);
	/**
	 * ��ѯ�û����ռ��˵�ַ��Ϣ
	 * @param userId
	 * @return
	 */
	public List<ActionAddress> findAddrsByUserId(Integer userId);
	/**
	 * ��ȡ�û�Ĭ�ϵ�ַ
	 * @param userId
	 * @return
	 */
	public ActionAddress findDefaultAddr(Integer userId);
}

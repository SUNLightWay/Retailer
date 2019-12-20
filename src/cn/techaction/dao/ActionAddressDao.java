package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionAddress;

public interface ActionAddressDao {
	/**
	 * 根据id获得地址信息
	 * @param id
	 * @return
	 */
	public ActionAddress findAddrById(Integer id);
	/**
	 * 根据id查询收货人地址信息
	 * @param addrId
	 * @return
	 */
	public ActionAddress findAddrsById(Integer addrId);
	/**
	 * 查询是否存在默认地址
	 * @param uid
	 * @return
	 */
	public int findDefaultAddrByUserId(Integer userId);
	/**
	 * 新增收货人地址信息
	 * @param addr
	 * @return
	 */
	public int insertAddress(ActionAddress addr);
	/**
	 * 更新收件人地址信息
	 * @param addr
	 * @return
	 */
	public int updateAddress(ActionAddress addr);
	/**
	 * 查询用户的收件人地址信息
	 * @param userId
	 * @return
	 */
	public List<ActionAddress> findAddrsByUserId(Integer userId);
	/**
	 * 读取用户默认地址
	 * @param userId
	 * @return
	 */
	public ActionAddress findDefaultAddr(Integer userId);
}

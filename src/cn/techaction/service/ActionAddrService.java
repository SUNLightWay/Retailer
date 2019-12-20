package cn.techaction.service;

import java.util.List;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionAddress;

public interface ActionAddrService {
	/**
	 * 新增收件人地址信息
	 * @param addr
	 * @return
	 */
	public SverResponse<String> addAddress(ActionAddress addr);
	/**
	 * 更新收件人地址信息
	 * @param addr
	 * @return
	 */
	public SverResponse<String> updateAddress(ActionAddress addr);
	/**
	 * 查找某个用户的所有收货地址
	 * @param userId
	 * @return
	 */
	public SverResponse<List<ActionAddress>> findAddrsByUserId(Integer userId);
	/**
	 * 根据id删除收件人地址信息
	 * @param id
	 * @param id2
	 * @return
	 */
	public SverResponse<String> delAddress(Integer userId, Integer id);
	/**
	 * 更新默认地址
	 * @param id
	 * @param id2
	 * @return
	 */
	public SverResponse<String> updateAddrDefaultStatus(Integer userId, Integer id);

}

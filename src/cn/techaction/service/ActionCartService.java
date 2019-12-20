package cn.techaction.service;

import cn.techaction.common.SverResponse;
import cn.techaction.vo.ActionCartVo;

public interface ActionCartService {
	/**
	 * 保存商品信息到购物车
	 * @param id
	 * @param productId
	 * @param count
	 * @return
	 */
	public SverResponse<String> saveOrUpdate(Integer userId, Integer productId, Integer count);
	/**
	 * 查询用户购物车中商品信息
	 * @param id
	 * @return
	 */
	public SverResponse<ActionCartVo> findAllCarts(Integer userId);
	/**
	 * 清空购物车
	 * @param id
	 * @return
	 */
	public SverResponse<String> clearCart(Integer userId);
	/**
	 * 更新购物车中商品的梳理
	 * @param id
	 * @param productId
	 * @param productId2
	 * @param productId3
	 * @return
	 */
	public SverResponse<ActionCartVo> updateCart(Integer userId, Integer productId, Integer count, Integer checked);
	/**
	 * 删除购物车中的商品信息
	 * @param userId
	 * @param productId
	 * @return
	 */
	public SverResponse<ActionCartVo> deleteCart(Integer userId, Integer productId);
	/**
	 * 获取登录用户购物车中商品的个数
	 * @param id
	 * @return
	 */
	public SverResponse<Integer> getCartCount(Integer userId);

}

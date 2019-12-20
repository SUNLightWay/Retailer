package cn.techaction.controller.protal;

import java.awt.Desktop.Action;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.User;
import cn.techaction.service.ActionCartService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.vo.ActionCartVo;

@Controller
@RequestMapping("/cart")
public class ActionCartController {
	@Autowired
	private ActionCartService aCartService;
	
	/**
	 * 加入购物车
	 * @param session
	 * @param productId
	 * @param count
	 * @return
	 */
	@RequestMapping(value="/savecart.do",method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<String> addProductCart(HttpSession session,Integer productId,Integer count){
		User user = (User) session.getAttribute(ConstUtil.CUR_USER);
		if(user == null ) {
			return SverResponse.createByErrorMessage("请登录后，在查看购物车！");
		}
		return aCartService.saveOrUpdate(user.getId(),productId,count);
	}
	
	/**
	 * 查看购物车
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/findallcarts.do")
	@ResponseBody
	public SverResponse<ActionCartVo> findAllCarts(HttpSession session){
		User user = (User) session.getAttribute(ConstUtil.CUR_USER);
		if(user == null ) {
			return SverResponse.createByErrorMessage("请登录后，在查看购物车！");
		}
		return aCartService.findAllCarts(user.getId());
	}
	
	/**
	 * 清空购物车
	 * @param session
	 * @return
	 */
	@RequestMapping("/clearcarts.do")
	@ResponseBody
	public SverResponse<String> clearCarts(HttpSession session){
		User user = (User) session.getAttribute(ConstUtil.CUR_USER);
		if(user == null ) {
			return SverResponse.createByErrorMessage("请登录后，在查看购物车！");
		}
		//清空购物车
		return aCartService.clearCart(user.getId());
	}
	
	/**
	 * 更新购物车
	 * @param session
	 * @param productId
	 * @param productId
	 * @param productId
	 * @return
	 */
	@RequestMapping("/updatecarts.do")
	@ResponseBody
	public SverResponse<ActionCartVo> updateCarts(HttpSession session,Integer productId,Integer count,Integer checked){
		User user = (User) session.getAttribute(ConstUtil.CUR_USER);
		if(user == null ) {
			return SverResponse.createByErrorMessage("请登录后，在查看购物车！");
		}
		return aCartService.updateCart(user.getId(),productId,count,checked);
	}
	
	/**
	 * 删除商品
	 * @param session
	 * @param productId
	 * @return
	 */
	@RequestMapping("/deletecarts.do")
	@ResponseBody
	public SverResponse<ActionCartVo> deleteCart(HttpSession session,Integer productId){
		User user = (User) session.getAttribute(ConstUtil.CUR_USER);
		if(user == null ) {
			return SverResponse.createByErrorMessage("请登录后，在查看购物车！");
		}
		return aCartService.deleteCart(user.getId(),productId);
	}
	
	/**
	 * 购物车数量
	 * @param session
	 * @return
	 */
	@RequestMapping("/getcartcount.do")
	@ResponseBody
	public SverResponse<Integer> getCartsCount(HttpSession session){
		User user = (User) session.getAttribute(ConstUtil.CUR_USER);
		if(user == null ) {
			return SverResponse.createByErrorMessage("请登录后，在查看购物车！");
		}
		return aCartService.getCartCount(user.getId());
	}
}

package cn.techaction.controller.protal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.User;
import cn.techaction.service.ActionOrderService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionOrderVo;

@Controller
@RequestMapping("/order")
public class ActionOrderController {
	@Autowired
	private ActionOrderService aOrderService;
	
	/**
	 * 获取订单列表
	 * @param session
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/getlist.do")
	@ResponseBody
	public SverResponse<PageBean<ActionOrderVo>> list(HttpSession session,Integer status,
			@RequestParam(value="pageNum",defaultValue="1") int pageNum,
			@RequestParam(value="pageSize",defaultValue="10") int pageSize){
		//判断用户是否登录
		User user = (User) session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录后再进行操作！");
		}
		return aOrderService.findOrders(user.getId(),status,pageNum,pageSize);
	}
	/**
	 * 取消订单
	 * @param session
	 * @param orderNo
	 * @return
	 */
	@RequestMapping("/cancelorder.do")
	@ResponseBody
	public SverResponse<String> cancelOrder(HttpSession session,Long orderNo){
		//判断用户是否登录
		User user = (User) session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录后再进行操作！");
		}
		return aOrderService.cancelOrder(user.getId(),orderNo);
	}
	
	/**
	 * 确认收货
	 * @param session
	 * @param orderNo
	 * @return
	 */
	@RequestMapping("/confirmreceipt.do")
	@ResponseBody
	public SverResponse<String> confirmReceipt(HttpSession session,Long orderNo){
		//判断用户是否登录
		User user = (User) session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录后再进行操作！");
		}
		return aOrderService.cancelOrder(user.getId(),orderNo);
	}
	
	/**
	 * 获取订单详细信息
	 * @param session
	 * @param orderNo
	 * @return
	 */
	@RequestMapping("/getdetail.do")
	@ResponseBody
	public SverResponse<ActionOrderVo> getDetail(HttpSession session,Long orderNo){
		//判断用户是否登录
		User user = (User) session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录后再进行操作！");
		}
		return aOrderService.findOrderDetail(user.getId(),orderNo);
	}
	/**
	 * 创建订单
	 * @param session
	 * @param addrId
	 * @return
	 */
	@RequestMapping(value="/createorder.do",method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<ActionOrderVo> createOrder(HttpSession session,Integer addrId){
		//判断用户是否登录
		User user = (User) session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录后再进行操作！");
		}
		return aOrderService.generateOrder(user.getId(),addrId);
	}
}

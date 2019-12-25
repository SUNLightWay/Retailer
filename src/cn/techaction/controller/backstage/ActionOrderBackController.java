package cn.techaction.controller.backstage;

import java.util.List;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.techaction.common.ResponseCode;
import cn.techaction.common.SverResponse;
import cn.techaction.pojo.User;
import cn.techaction.service.ActionOrderService;
import cn.techaction.service.UserService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.vo.ActionOrderVo;

@Controller
@RequestMapping("/mgr/order")
public class ActionOrderBackController {
	@Autowired
	private ActionOrderService actionOrderService;
	@Autowired
	private UserService userService;
	/**
	 * 通过订单号查询订单信息
	 * @param session
	 * @param orderNo
	 * @return
	 */
	@RequestMapping("/findorders_nopages.do")
	@ResponseBody
	public  SverResponse<List<ActionOrderVo>> findOrder(HttpSession session,Long orderNo){
		//1.判断用户是否登录
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后再进行操作！");
		}
		//2.用户是否是管理员
		SverResponse<String> response=userService.isAdmin(user);
		if(response.isSuccess()) {
			//3.调用Service中的方法:查询订单
			return actionOrderService.findOrderForNoPages(orderNo);
		}	
		return SverResponse.createByErrorMessage("无操作权限！");
	}
	/**
	 * 查看订单详情
	 * @param session
	 * @param orderNo
	 * @return
	 */
	@RequestMapping("/getdetail.do")
	@ResponseBody
	public SverResponse<ActionOrderVo> getDetail(HttpSession session,Long orderNo){
		//1.判断用户是否登录
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后再进行操作！");
		}
		//2.用户是否是管理员
		SverResponse<String> response=userService.isAdmin(user);
		if(response.isSuccess()) {
			//3.调用Service中的方法:查询订单详情
			return actionOrderService.mgrDetail(orderNo);
		}	
		return SverResponse.createByErrorMessage("无操作权限！");
	}
}

package cn.techaction.controller.backstage;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.techaction.common.ResponseCode;
import cn.techaction.common.SverResponse;
import cn.techaction.pojo.User;
import cn.techaction.service.UserService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.vo.ActionUserVo;

@Controller
@RequestMapping("/mgr/user")
public class ActionUserBackController {

	@Autowired
	private UserService userService;
	/**
	 * 前端登陆方法
	 * 
	 * @param account
	 *            账号
	 * @param password
	 *            密码
	 * @param session
	 *            会话
	 * @return
	 */
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	@ResponseBody
	public SverResponse<User> doLogin(String account, String password, HttpSession session) {
		SverResponse<User> response = userService.doLogin(account, password);
		if (response.isSuccess()) {
			User user = response.getData();
			if (user.getRole() == ConstUtil.Role.ROLE_ADMIN) {
				// 说明登录的是管理员
				session.setAttribute(ConstUtil.CUR_USER, user);
				return response;
			} else {
				return SverResponse.createByErrorMessage("不是管理员,无法登录");
			}
		}
		return response;
	}
	@RequestMapping("/finduserlist.do")
	@ResponseBody
	public SverResponse<List<ActionUserVo>> getUserDetail(HttpSession session) {
		//1.判断用户是否登录
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后再进行操作！");
		}
		//2.用户是否是管理员
		SverResponse<String> response=userService.isAdmin(user);
		if(response.isSuccess()) {
			//3.调用Service中的方法获得所有的用户信息
			return userService.findUserList();
		}	
		return SverResponse.createByErrorMessage("您无操作权限！");		
	}
	/**
	 * 根据Id获得用户信息
	 * @param session
	 * @param id
	 * @return
	 */
	@RequestMapping("/finduser.do")
	@ResponseBody
	public SverResponse<ActionUserVo> findUser(HttpSession session, Integer id){
		//1.判断用户是否登录
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后再进行操作！");
		}
		//2.用户是否是管理
		SverResponse<String> response=userService.isAdmin(user);
		if(response.isSuccess()) {
			//3.调用service的方法根据用户id获得用户信息
			return userService.findUser(id);
		}	
		return SverResponse.createByErrorMessage("您无操作权限！");
	}
	/**
	 * 
	 * @param session
	 * @param userVo
	 * @return
	 */
	@RequestMapping("/updateuser.do")
	@ResponseBody
	public SverResponse<User> updateUser(HttpSession session, ActionUserVo userVo){
		//1.判断用户是否登录
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后再进行操作！");
		}
		//2.用户是否是管理
		SverResponse<String> response=userService.isAdmin(user);
		if(response.isSuccess()) {
			//3.调用service的方法:更新用户
			return userService.updateUserInfo(userVo);
		}	
		return SverResponse.createByErrorMessage("您无操作权限！");
	}
	/**
	 * 删除用户
	 * @param session
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteusers.do")
	@ResponseBody
	public SverResponse<String> delUsers(HttpSession session,Integer id){
		//1.判断用户是否登录
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后再进行操作！");
		}
		//2.用户是否是管理
		SverResponse<String> response=userService.isAdmin(user);
		if(response.isSuccess()) {
			//3.调用service的方法:删除方法
			return userService.delUser(id);
		}	
		return SverResponse.createByErrorMessage("您无操作权限！");
	}
}

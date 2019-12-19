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
	 * ǰ�˵�½����
	 * 
	 * @param account
	 *            �˺�
	 * @param password
	 *            ����
	 * @param session
	 *            �Ự
	 * @return
	 */
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	@ResponseBody
	public SverResponse<User> doLogin(String account, String password, HttpSession session) {
		SverResponse<User> response = userService.doLogin(account, password);
		if (response.isSuccess()) {
			User user = response.getData();
			if (user.getRole() == ConstUtil.Role.ROLE_ADMIN) {
				// ˵����¼���ǹ���Ա
				session.setAttribute(ConstUtil.CUR_USER, user);
				return response;
			} else {
				return SverResponse.createByErrorMessage("���ǹ���Ա,�޷���¼");
			}
		}
		return response;
	}
	@RequestMapping("/finduserlist.do")
	@ResponseBody
	public SverResponse<List<ActionUserVo>> getUserDetail(HttpSession session) {
		//1.�ж��û��Ƿ��¼
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "���¼���ٽ��в�����");
		}
		//2.�û��Ƿ��ǹ���Ա
		SverResponse<String> response=userService.isAdmin(user);
		if(response.isSuccess()) {
			//3.����Service�еķ���������е��û���Ϣ
			return userService.findUserList();
		}	
		return SverResponse.createByErrorMessage("���޲���Ȩ�ޣ�");		
	}
	/**
	 * ����Id����û���Ϣ
	 * @param session
	 * @param id
	 * @return
	 */
	@RequestMapping("/finduser.do")
	@ResponseBody
	public SverResponse<ActionUserVo> findUser(HttpSession session, Integer id){
		//1.�ж��û��Ƿ��¼
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "���¼���ٽ��в�����");
		}
		//2.�û��Ƿ��ǹ���
		SverResponse<String> response=userService.isAdmin(user);
		if(response.isSuccess()) {
			//3.����service�ķ��������û�id����û���Ϣ
			return userService.findUser(id);
		}	
		return SverResponse.createByErrorMessage("���޲���Ȩ�ޣ�");
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
		//1.�ж��û��Ƿ��¼
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "���¼���ٽ��в�����");
		}
		//2.�û��Ƿ��ǹ���
		SverResponse<String> response=userService.isAdmin(user);
		if(response.isSuccess()) {
			//3.����service�ķ���:�����û�
			return userService.updateUserInfo(userVo);
		}	
		return SverResponse.createByErrorMessage("���޲���Ȩ�ޣ�");
	}
	/**
	 * ɾ���û�
	 * @param session
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteusers.do")
	@ResponseBody
	public SverResponse<String> delUsers(HttpSession session,Integer id){
		//1.�ж��û��Ƿ��¼
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "���¼���ٽ��в�����");
		}
		//2.�û��Ƿ��ǹ���
		SverResponse<String> response=userService.isAdmin(user);
		if(response.isSuccess()) {
			//3.����service�ķ���:ɾ������
			return userService.delUser(id);
		}	
		return SverResponse.createByErrorMessage("���޲���Ȩ�ޣ�");
	}
}

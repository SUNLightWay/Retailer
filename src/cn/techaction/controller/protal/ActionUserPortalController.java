package cn.techaction.controller.protal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.User;
import cn.techaction.service.UserService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.vo.ActionUserVo;

@Controller
@RequestMapping("/user")
public class ActionUserPortalController {
	@Autowired
	private UserService userService;
	
	/**
	 * �û���¼ 
	 * @param account
	 * @param password
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/do_login.do",method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<User> doLogin(String account,String password,HttpSession session){
		SverResponse<User> response = userService.doLogin(account,password);
		if(response.isSuccess()) {
			//��½�ɹ������û���Ϣ����session
			session.setAttribute(ConstUtil.CUR_USER, response.getData());
		}
		return response;
	}
	
	/**
	 * �û�ע��
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/do_register.do",method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<String> registerUser(User user){
		return userService.doRegister(user);
	}
	
	/**
	 * ��֤�û�������û�����
	 * @param account
	 * @return
	 */
	@RequestMapping(value="/getuserbyaccount.do",method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<User> getUserByAccount(String account){
		return userService.findUserByAccount(account);
	}
	
	/**
	 * ��֤�û�������ʾ�����
	 * @param account
	 * @param question
	 * @param asw
	 * @return
	 */
	@RequestMapping(value="/checkuserasw.do",method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<String> checkUserAnswer(String account,String question,String asw){
		return userService.checkUserAnswer(account,question,asw);
	}
	
	/**
	 * ��������
	 * @param userId
	 * @param newpwd
	 * @return
	 */
	@RequestMapping(value="/resetpassword.do",method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<String> resetPassword(Integer userId,String newpwd){
		return userService.resetPassword(userId,newpwd);
	}
	
	/**
	 * �޸��û���������
	 * @param session
	 * @param userVo
	 * @return
	 */
	@RequestMapping(value="/updateuserinfo.do",method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<User> updateUserInfo(HttpSession session,ActionUserVo userVo){
		User curUser = (User) session.getAttribute(ConstUtil.CUR_USER);
		if(curUser==null) {
			return SverResponse.createByErrorMessage("�û���δ��¼��");
		}
		userVo.setId(curUser.getId());
		userVo.setAccount(curUser.getAccount());
		SverResponse<User> resp = userService.updateUserInfo(userVo);
		if(resp.isSuccess()) {
			//��дsession
			session.setAttribute(ConstUtil.CUR_USER, resp.getData());
		}
		return resp;	
	}
	
	/**
	 * ��¼�û��޸�����
	 * @param session
	 * @param newpwd
	 * @param oldpwd
	 * @return
	 */
	@RequestMapping(value="/updatepassword.do",method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<String> updatePassword(HttpSession session,String newpwd,String oldpwd){
		//�����ǵ�sessionȡ��
		User user = (User) session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("���¼�����޸����룡");
		}
		SverResponse<String> result = userService.updatePassword(user,newpwd,oldpwd);
		//�޸�֮�����session��׼�����µ�¼
		if(result.isSuccess()) {
			session.removeAttribute(ConstUtil.CUR_USER);
		}
		return result;
	}
	
	/**
	 * �ǳ�
	 * @param session
	 * @return
	 */
	@RequestMapping("/do_logout.do")
	@ResponseBody
	public SverResponse<String> logout(HttpSession session){
		User user = (User)session.getAttribute(ConstUtil.CUR_USER);
		if(user != null) {
			session.removeAttribute(ConstUtil.CUR_USER);
			return SverResponse.createRespBySuccess("�ǳ��ɹ�");
		}
		return SverResponse.createByErrorMessage("���ȵ�¼");
	}
	
}

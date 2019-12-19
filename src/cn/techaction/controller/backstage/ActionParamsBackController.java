package cn.techaction.controller.backstage;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.techaction.common.ResponseCode;
import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionParam;
import cn.techaction.pojo.User;
import cn.techaction.service.ActionParamsService;
import cn.techaction.service.UserService;
import cn.techaction.utils.ConstUtil;

@Controller
@RequestMapping("/mgr/param")
public class ActionParamsBackController {
	@Autowired
	private ActionParamsService actionParamsService;
	@Autowired
	private UserService userService;
	/**
	 * ��������
	 * @param session
	 * @param actionParam
	 * @return
	 */
	@RequestMapping("/saveparam.do")
	@ResponseBody
	public SverResponse<String> saveParam(HttpSession session,ActionParam actionParam){
		//1.�ж��û��Ƿ��¼
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "���¼���ٽ��в�����");
		}
		//2.�û��Ƿ��ǹ���Ա
		SverResponse<String> response=userService.isAdmin(user);
		if(response.isSuccess()) {
			//3.����Service�еķ���:��������
			return actionParamsService.addParam(actionParam);
		}	
		return SverResponse.createByErrorMessage("�޲���Ȩ�ޣ�");
	}
	/**
	 * �޸�����
	 * @param session
	 * @param actionParam
	 * @return
	 */
	@RequestMapping("/updateparam.do")
	@ResponseBody
	public SverResponse<String> updateCategory(HttpSession session,ActionParam actionParam){
		//1.�ж��û��Ƿ��¼
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "���¼���ٽ��в�����");
		}
		//2.�û��Ƿ��ǹ���Ա
		SverResponse<String> response=userService.isAdmin(user);
		if(response.isSuccess()) {
			//3.����Service�еķ���:�޸�����
			return actionParamsService.updateParam(actionParam);
		}	
		return SverResponse.createByErrorMessage("�޲���Ȩ�ޣ�");
	}
	/**
	 * ɾ����Ʒ����
	 * @param session
	 * @param id
	 * @return
	 */
	@RequestMapping("/delparam.do")
	@ResponseBody
	public SverResponse<String> delParam(HttpSession session,Integer id){
		//1.�ж��û��Ƿ��¼
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "���¼���ٽ��в�����");
		}
		//2.�û��Ƿ��ǹ���Ա
		SverResponse<String> response=userService.isAdmin(user);
		if(response.isSuccess()) {
			//3.����Service�еķ���:ɾ������
			return actionParamsService.delParam(id);
		}	
		return SverResponse.createByErrorMessage("�޲���Ȩ�ޣ�");		
	}
	/**
	 * ������һ�������ͣ������ͣ�
	 * @param session
	 * @param id
	 * @return
	 */
	@RequestMapping("/findchildren.do")
	@ResponseBody
	public SverResponse<List<ActionParam>> getChildrenParam(HttpSession session,@RequestParam(value="id",defaultValue="0")Integer id){
		//1.�ж��û��Ƿ��¼
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "���¼���ٽ��в�����");
		}
		//2.�û��Ƿ��ǹ���Ա
		SverResponse<String> response=userService.isAdmin(user);
		if(response.isSuccess()) {
			//3.����Service�еķ���:���������
			return actionParamsService.findParamChildren(id);
		}	
		return SverResponse.createByErrorMessage("�޲���Ȩ�ޣ�");	
	}
}

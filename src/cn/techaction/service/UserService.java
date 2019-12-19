package cn.techaction.service;

import java.util.List;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.User;
import cn.techaction.vo.ActionUserVo;

public interface UserService {
	
	/**
	 * �û���¼
	 * @param account
	 * @param password
	 * @return
	 */
	public SverResponse<User> doLogin(String account,String password);	
	/**
	 * �ж��û��Ƿ��ǹ���Ա
	 * @param user
	 * @return
	 */
	public SverResponse<String> isAdmin(User user);
	/**
	 * ������е��û���Ϣ
	 * @return
	 */
	public SverResponse<List<ActionUserVo>> findUserList();
	/**
	 * �����û�Id����û�����
	 * @param id
	 * @return
	 */
	public SverResponse<ActionUserVo> findUser(Integer id);
	/**
	 * �����û���Ϣ
	 * @param userVo
	 * @return
	 */
	public SverResponse<User> updateUserInfo(ActionUserVo userVo);
	/**
	 * ɾ���û�
	 * @param id
	 * @return
	 */
	public SverResponse<String> delUser(Integer id);
}

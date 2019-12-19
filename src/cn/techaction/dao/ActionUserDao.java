package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.User;

public interface ActionUserDao {

	
	/**
	 * �����û�������������û�
	 * @param account
	 * @param password
	 * @return
	 */
	public User findUserByAccountAndPwd(String account,String password);
	/**
	 * �����˺��ж��û��Ƿ����
	 * @param account
	 * @return
	 */
	public int checkUserByAccount(String account);
	/**
	 * ��������û���Ϣ
	 * @return
	 */
	public List<User> findAllUsers();
	/**
	 * �����û�id����û�����
	 * @param id
	 * @return
	 */
	public User findUserById(Integer id);
	/**
	 * �����û���Ϣ
	 * @param user
	 * @return
	 */
	public int updateUserInfo(User user);
}


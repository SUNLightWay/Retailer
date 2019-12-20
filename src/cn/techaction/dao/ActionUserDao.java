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
	
	/**
	 * ��֤���������Ƿ��Ѿ���ע��
	 * @param email
	 * @return
	 */
	public int checkUserByEmail(String email);
	/**
	 * ��֤�ֻ��Ƿ��Ѿ���ע��
	 * @param phone
	 * @return
	 */
	public int checkUserByPhone(String phone);
	/**
	 * �����û�
	 * @param user
	 * @return
	 */
	public int insertUser(User user);
	/**
	 * �����û��������û�
	 * @param account
	 * @return
	 */
	public User findUserByAccount(String account);
	/**
	 * ����û�������Ĵ�
	 * @param account
	 * @param question
	 * @param asw
	 * @return
	 */
	public int checkUserAnswer(String account, String question, String asw);

	/**
	 * ��֤�û������Ƿ��Ѿ�����
	 * @param account
	 * @param oldPassword
	 * @return
	 */
	public int checkPassword(String account, String password);
}


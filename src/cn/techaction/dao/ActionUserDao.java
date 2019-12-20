package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.User;

public interface ActionUserDao {

	
	/**
	 * 根据用户名和密码查找用户
	 * @param account
	 * @param password
	 * @return
	 */
	public User findUserByAccountAndPwd(String account,String password);
	/**
	 * 根据账号判断用户是否存在
	 * @param account
	 * @return
	 */
	public int checkUserByAccount(String account);
	/**
	 * 获得所有用户信息
	 * @return
	 */
	public List<User> findAllUsers();
	/**
	 * 根据用户id获得用户对象
	 * @param id
	 * @return
	 */
	public User findUserById(Integer id);
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	public int updateUserInfo(User user);
	
	/**
	 * 验证电子邮箱是否已经被注册
	 * @param email
	 * @return
	 */
	public int checkUserByEmail(String email);
	/**
	 * 验证手机是否已经被注册
	 * @param phone
	 * @return
	 */
	public int checkUserByPhone(String phone);
	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	public int insertUser(User user);
	/**
	 * 根据用户名查找用户
	 * @param account
	 * @return
	 */
	public User findUserByAccount(String account);
	/**
	 * 检查用户面问题的答案
	 * @param account
	 * @param question
	 * @param asw
	 * @return
	 */
	public int checkUserAnswer(String account, String question, String asw);

	/**
	 * 验证用户密码是否已经存在
	 * @param account
	 * @param oldPassword
	 * @return
	 */
	public int checkPassword(String account, String password);
}


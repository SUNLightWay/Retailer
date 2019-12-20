package cn.techaction.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.ActionOrderDao;
import cn.techaction.dao.ActionUserDao;
import cn.techaction.pojo.User;
import cn.techaction.service.UserService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.utils.MD5Util;
import cn.techaction.utils.TokenCache;
import cn.techaction.vo.ActionUserVo;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private ActionUserDao userDao;
	@Autowired
	private ActionOrderDao orderDao;
	
	@Override
	public SverResponse<User> doLogin(String account, String password) {
		//判断用户是否存在
		int rs = userDao.checkUserByAccount(account);
		if(rs==0) {
			return SverResponse.createByErrorMessage("用户不存在！");
		}
		//根据用户名和密码查找用户
		String md5Pwd = MD5Util.MD5Encode(password, "utf-8", false);
		User user = userDao.findUserByAccountAndPwd(account, md5Pwd);
		if(user==null) {
			return SverResponse.createByErrorMessage("密码错误！");
		}
		//将密码置空，防止泄密
		user.setPassword(StringUtils.EMPTY);
		return SverResponse.createRespBySuccess("登陆成功", user);
	}
	
	@Override
	public SverResponse<String> isAdmin(User user) {
		// TODO Auto-generated method stub
		if(user.getRole()==ConstUtil.Role.ROLE_ADMIN) {
			return SverResponse.createRespBySuccess();
		}
		return SverResponse.createRespByError();
	}

	@Override
	public SverResponse<List<ActionUserVo>> findUserList() {
		// TODO Auto-generated method stub
		List<ActionUserVo> vos=Lists.newArrayList();
		//1.调用dao层类的方法
		List<User> users=userDao.findAllUsers();
		//2.处理：user对象转换成ActionUserVo对象
		for(User u:users) {
			vos.add(setNormalProperty(u));
		}
		return SverResponse.createRespBySuccess(vos);
	}
	/**
	 * 将user转换成ActionUserVo对象
	 * @param user
	 * @return
	 */
	private ActionUserVo setNormalProperty(User user) {
		ActionUserVo vo=new ActionUserVo();
		vo.setAccount(user.getAccount());
		vo.setAge(user.getAge());
		vo.setEmail(user.getEmail());
		vo.setId(user.getId());
		vo.setName(user.getName());
		vo.setPhone(user.getPhone());
		if(user.getSex()==1) {
			vo.setSex("男");
		}else {
			vo.setSex("女");
		}		
		return vo;
	}

	@Override
	public SverResponse<ActionUserVo> findUser(Integer id) {
		// TODO Auto-generated method stub
		//1.调用dao层类中方法
		User user=userDao.findUserById(id);
		//2.将user对象转换成ActionUserVo对象
		ActionUserVo vo=setNormalProperty(user);
		return SverResponse.createRespBySuccess(vo);
	}

	@Override
	public SverResponse<User> updateUserInfo(ActionUserVo userVo) {
		// TODO Auto-generated method stub
		//1.根据id获得user对象
		User user=userDao.findUserById(userVo.getId());
		//2.把userVo里的修改的属性值赋给user对象
		user.setAccount(userVo.getAccount());
		user.setAccount(userVo.getAccount());
		user.setEmail(userVo.getEmail());
		user.setName(userVo.getName());
		user.setPhone(userVo.getPhone());
		if(userVo.getSex().equals("男")) {
			user.setSex(1);
		}else {
			user.setSex(0);
		}
		user.setUpdate_time(new Date());
		//3.调用dao层的方法
		int rs=userDao.updateUserInfo(user);
		if(rs>0) {
			return SverResponse.createRespBySuccess("用户信息修改成功！",user);
		}
		return SverResponse.createByErrorMessage("用户信息修改失败！");
	}

	@Override
	public SverResponse<String> delUser(Integer id) {
		// TODO Auto-generated method stub
		//1.判断用户是否有订单，如果没有订单则删除
		if(orderDao.findOrderByUid(id).size()>0) {
			return SverResponse.createByErrorMessage("用户存在关联的订单无法删除！");
		}
		//2.删除用户，实际是修改用户的del状态字段的值
		User user=userDao.findUserById(id);
		user.setDel(1);
		user.setUpdate_time(new Date());
		int rs=userDao.updateUserInfo(user);
		if(rs>0){
			return SverResponse.createRespBySuccess("用户删除成功！");
		}
		return SverResponse.createByErrorMessage("用户删除失败！");
	}

	@Override
	public SverResponse<String> doRegister(User user) {
		//检查用户名是否存在
		 SverResponse<String> resp = checkValidation(user.getAccount(),ConstUtil.TYPE_ACCOUNT);
		 if(!resp.isSuccess()) {
			 return resp;
		 }
		//检查邮箱是否被注册
		resp = checkValidation(user.getEmail(),ConstUtil.TYPE_EMAIL);
		if(!resp.isSuccess()) {
			return resp;
		}
		//指定用户角色，通过前端注册的用户都为客户
		user.setRole(ConstUtil.Role.ROLE_CUSTOMER);
		//密码加密
		user.setPassword(MD5Util.MD5Encode(user.getPassword(), "utf-8", false));
		//执行注册
		Date curDate =new Date();
		user.setCreate_time(curDate);
		user.setUpdate_time(curDate);
		int rs = userDao.insertUser(user);
		if(rs==0) {
			return SverResponse.createByErrorMessage("注册失败！");
		}
		return SverResponse.createRespBySuccessMessage("注册成功！");
	}

	@Override
	public SverResponse<String> checkValidation(String str, String type) {
		//判断我们的字符串不为空 type
		if(StringUtils.isNotBlank(type)) {
			if(ConstUtil.TYPE_ACCOUNT.equals(type)) {
				int rs = userDao.checkUserByAccount(str);
				if(rs > 0) {
					return SverResponse.createByErrorMessage("用户名已经存在");
				}	
			}
			if(StringUtils.isNotBlank(type)) {
				if(ConstUtil.TYPE_EMAIL.equals(type)) {
					int rs = userDao.checkUserByEmail(str);
					if(rs > 0) {
						return SverResponse.createByErrorMessage("Email已经存在");
					}
				}
			}
			if(StringUtils.isNotBlank(type)) {
				if(ConstUtil.TYPE_PHONE.equals(type)) {
					int rs = userDao.checkUserByPhone(str);
					if(rs > 0) {
						return SverResponse.createByErrorMessage("电话号码已经存在");
					}
				}
			}
			
		}else {
			return SverResponse.createByErrorMessage("信息验证类别错误！");
		}
		return SverResponse.createRespBySuccessMessage("信息验证成功！");
	}

	@Override
	public SverResponse<User> findUserByAccount(String account) {
		//通过用户名查找到用户
		User user = userDao.findUserByAccount(account);
		if(user==null) {
			return SverResponse.createByErrorMessage("用户名错误！");
		}
		//将密码置空
		user.setPassword(StringUtils.EMPTY);
		//将安全问题答案置空
		user.setAsw(StringUtils.EMPTY);
		return SverResponse.createRespBySuccess(user);
	}

	@Override
	public SverResponse<String> checkUserAnswer(String account, String question, String asw) {
		//获取校验结果
		int rs = userDao.checkUserAnswer(account,question,asw);
		if(rs > 0) {
			//答案正确，生成token
			String token = UUID.randomUUID().toString();
			//放入缓存
			TokenCache.setCacheData(TokenCache.PREFIX+account, token);
			return SverResponse.createRespBySuccessMessage(token);
		}
		return SverResponse.createByErrorMessage("问题答案错误！");
	}

	@Override
	public SverResponse<String> resetPassword(Integer userId, String password) {
		//密码加密
		String pwd = MD5Util.MD5Encode(password, "utf-8", false);
		//获得user对象
		User user = userDao.findUserById(userId);
		//更新密码
		user.setPassword(pwd);
		user.setUpdate_time(new Date());
		int rs = userDao.updateUserInfo(user);
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("密码修改成功！");
		}
		return SverResponse.createByErrorMessage("密码修改失败！");
	}

	@Override
	public SverResponse<String> updatePassword(User user, String newPassword, String oldPassword) {
		//防止越权，检测用户旧密码是否正确
		oldPassword = MD5Util.MD5Encode(oldPassword, "utf-8", false);
		int rs = userDao.checkPassword(user.getAccount(),oldPassword);
		if(rs == 0) {
			return SverResponse.createByErrorMessage("原始密码错误！");
		}
		//将新密码插入数据库
		newPassword = MD5Util.MD5Encode(newPassword, "utf-8", false);
		user.setPassword(newPassword);
		user.setUpdate_time(new Date());
		rs = userDao.updateUserInfo(user);
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("密码修改成功！");
		}
		return SverResponse.createByErrorMessage("密码修改失败！");
	}

	
}

package cn.techaction.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.ActionUserDao;
import cn.techaction.pojo.User;
import cn.techaction.service.ActionUserService;
import cn.techaction.utils.MD5Util;

@Service
public class ActionUserServiceImpl implements ActionUserService {
	@Autowired
	private ActionUserDao actionUserDao;
	@Override
	public SverResponse<User> doLogin(String account, String password) {
		//1.判断账号、用户名是否存在
		int rs = actionUserDao.checkUserByAccount(account);
		//2.存在，根据用户名密码 查找用户
			//2.1md5加密后对比
		String md5pwd = MD5Util.MD5Encode(password, "utf-8",false);
		if(rs > 0) {
			User user = actionUserDao.findUserByAccountAndPwd(account, md5pwd);
			//3.判断是否找到
			if(user==null) {
				return SverResponse.createByErrorMessage("密码错误");
			}
			else {
				//用户存在,制空密码，写回session
				user.setPassword(StringUtils.EMPTY);
				return SverResponse.createRespBySuccess("登陆成功！", user);
			}
		}
		else {
			return SverResponse.createByErrorMessage("用户不存在！");
		}
	}
	
}

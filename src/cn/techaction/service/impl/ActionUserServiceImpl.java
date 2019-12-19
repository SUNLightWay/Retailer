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
		//1.�ж��˺š��û����Ƿ����
		int rs = actionUserDao.checkUserByAccount(account);
		//2.���ڣ������û������� �����û�
			//2.1md5���ܺ�Ա�
		String md5pwd = MD5Util.MD5Encode(password, "utf-8",false);
		if(rs > 0) {
			User user = actionUserDao.findUserByAccountAndPwd(account, md5pwd);
			//3.�ж��Ƿ��ҵ�
			if(user==null) {
				return SverResponse.createByErrorMessage("�������");
			}
			else {
				//�û�����,�ƿ����룬д��session
				user.setPassword(StringUtils.EMPTY);
				return SverResponse.createRespBySuccess("��½�ɹ���", user);
			}
		}
		else {
			return SverResponse.createByErrorMessage("�û������ڣ�");
		}
	}
	
}

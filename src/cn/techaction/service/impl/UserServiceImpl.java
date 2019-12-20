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
		//�ж��û��Ƿ����
		int rs = userDao.checkUserByAccount(account);
		if(rs==0) {
			return SverResponse.createByErrorMessage("�û������ڣ�");
		}
		//�����û�������������û�
		String md5Pwd = MD5Util.MD5Encode(password, "utf-8", false);
		User user = userDao.findUserByAccountAndPwd(account, md5Pwd);
		if(user==null) {
			return SverResponse.createByErrorMessage("�������");
		}
		//�������ÿգ���ֹй��
		user.setPassword(StringUtils.EMPTY);
		return SverResponse.createRespBySuccess("��½�ɹ�", user);
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
		//1.����dao����ķ���
		List<User> users=userDao.findAllUsers();
		//2.����user����ת����ActionUserVo����
		for(User u:users) {
			vos.add(setNormalProperty(u));
		}
		return SverResponse.createRespBySuccess(vos);
	}
	/**
	 * ��userת����ActionUserVo����
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
			vo.setSex("��");
		}else {
			vo.setSex("Ů");
		}		
		return vo;
	}

	@Override
	public SverResponse<ActionUserVo> findUser(Integer id) {
		// TODO Auto-generated method stub
		//1.����dao�����з���
		User user=userDao.findUserById(id);
		//2.��user����ת����ActionUserVo����
		ActionUserVo vo=setNormalProperty(user);
		return SverResponse.createRespBySuccess(vo);
	}

	@Override
	public SverResponse<User> updateUserInfo(ActionUserVo userVo) {
		// TODO Auto-generated method stub
		//1.����id���user����
		User user=userDao.findUserById(userVo.getId());
		//2.��userVo����޸ĵ�����ֵ����user����
		user.setAccount(userVo.getAccount());
		user.setAccount(userVo.getAccount());
		user.setEmail(userVo.getEmail());
		user.setName(userVo.getName());
		user.setPhone(userVo.getPhone());
		if(userVo.getSex().equals("��")) {
			user.setSex(1);
		}else {
			user.setSex(0);
		}
		user.setUpdate_time(new Date());
		//3.����dao��ķ���
		int rs=userDao.updateUserInfo(user);
		if(rs>0) {
			return SverResponse.createRespBySuccess("�û���Ϣ�޸ĳɹ���",user);
		}
		return SverResponse.createByErrorMessage("�û���Ϣ�޸�ʧ�ܣ�");
	}

	@Override
	public SverResponse<String> delUser(Integer id) {
		// TODO Auto-generated method stub
		//1.�ж��û��Ƿ��ж��������û�ж�����ɾ��
		if(orderDao.findOrderByUid(id).size()>0) {
			return SverResponse.createByErrorMessage("�û����ڹ����Ķ����޷�ɾ����");
		}
		//2.ɾ���û���ʵ�����޸��û���del״̬�ֶε�ֵ
		User user=userDao.findUserById(id);
		user.setDel(1);
		user.setUpdate_time(new Date());
		int rs=userDao.updateUserInfo(user);
		if(rs>0){
			return SverResponse.createRespBySuccess("�û�ɾ���ɹ���");
		}
		return SverResponse.createByErrorMessage("�û�ɾ��ʧ�ܣ�");
	}

	@Override
	public SverResponse<String> doRegister(User user) {
		//����û����Ƿ����
		 SverResponse<String> resp = checkValidation(user.getAccount(),ConstUtil.TYPE_ACCOUNT);
		 if(!resp.isSuccess()) {
			 return resp;
		 }
		//��������Ƿ�ע��
		resp = checkValidation(user.getEmail(),ConstUtil.TYPE_EMAIL);
		if(!resp.isSuccess()) {
			return resp;
		}
		//ָ���û���ɫ��ͨ��ǰ��ע����û���Ϊ�ͻ�
		user.setRole(ConstUtil.Role.ROLE_CUSTOMER);
		//�������
		user.setPassword(MD5Util.MD5Encode(user.getPassword(), "utf-8", false));
		//ִ��ע��
		Date curDate =new Date();
		user.setCreate_time(curDate);
		user.setUpdate_time(curDate);
		int rs = userDao.insertUser(user);
		if(rs==0) {
			return SverResponse.createByErrorMessage("ע��ʧ�ܣ�");
		}
		return SverResponse.createRespBySuccessMessage("ע��ɹ���");
	}

	@Override
	public SverResponse<String> checkValidation(String str, String type) {
		//�ж����ǵ��ַ�����Ϊ�� type
		if(StringUtils.isNotBlank(type)) {
			if(ConstUtil.TYPE_ACCOUNT.equals(type)) {
				int rs = userDao.checkUserByAccount(str);
				if(rs > 0) {
					return SverResponse.createByErrorMessage("�û����Ѿ�����");
				}	
			}
			if(StringUtils.isNotBlank(type)) {
				if(ConstUtil.TYPE_EMAIL.equals(type)) {
					int rs = userDao.checkUserByEmail(str);
					if(rs > 0) {
						return SverResponse.createByErrorMessage("Email�Ѿ�����");
					}
				}
			}
			if(StringUtils.isNotBlank(type)) {
				if(ConstUtil.TYPE_PHONE.equals(type)) {
					int rs = userDao.checkUserByPhone(str);
					if(rs > 0) {
						return SverResponse.createByErrorMessage("�绰�����Ѿ�����");
					}
				}
			}
			
		}else {
			return SverResponse.createByErrorMessage("��Ϣ��֤������");
		}
		return SverResponse.createRespBySuccessMessage("��Ϣ��֤�ɹ���");
	}

	@Override
	public SverResponse<User> findUserByAccount(String account) {
		//ͨ���û������ҵ��û�
		User user = userDao.findUserByAccount(account);
		if(user==null) {
			return SverResponse.createByErrorMessage("�û�������");
		}
		//�������ÿ�
		user.setPassword(StringUtils.EMPTY);
		//����ȫ������ÿ�
		user.setAsw(StringUtils.EMPTY);
		return SverResponse.createRespBySuccess(user);
	}

	@Override
	public SverResponse<String> checkUserAnswer(String account, String question, String asw) {
		//��ȡУ����
		int rs = userDao.checkUserAnswer(account,question,asw);
		if(rs > 0) {
			//����ȷ������token
			String token = UUID.randomUUID().toString();
			//���뻺��
			TokenCache.setCacheData(TokenCache.PREFIX+account, token);
			return SverResponse.createRespBySuccessMessage(token);
		}
		return SverResponse.createByErrorMessage("����𰸴���");
	}

	@Override
	public SverResponse<String> resetPassword(Integer userId, String password) {
		//�������
		String pwd = MD5Util.MD5Encode(password, "utf-8", false);
		//���user����
		User user = userDao.findUserById(userId);
		//��������
		user.setPassword(pwd);
		user.setUpdate_time(new Date());
		int rs = userDao.updateUserInfo(user);
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("�����޸ĳɹ���");
		}
		return SverResponse.createByErrorMessage("�����޸�ʧ�ܣ�");
	}

	@Override
	public SverResponse<String> updatePassword(User user, String newPassword, String oldPassword) {
		//��ֹԽȨ������û��������Ƿ���ȷ
		oldPassword = MD5Util.MD5Encode(oldPassword, "utf-8", false);
		int rs = userDao.checkPassword(user.getAccount(),oldPassword);
		if(rs == 0) {
			return SverResponse.createByErrorMessage("ԭʼ�������");
		}
		//��������������ݿ�
		newPassword = MD5Util.MD5Encode(newPassword, "utf-8", false);
		user.setPassword(newPassword);
		user.setUpdate_time(new Date());
		rs = userDao.updateUserInfo(user);
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("�����޸ĳɹ���");
		}
		return SverResponse.createByErrorMessage("�����޸�ʧ�ܣ�");
	}

	
}

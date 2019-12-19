package test;

import java.util.Date;

import cn.techaction.utils.DateUtils;

public class MD5Util {
	public static void main(String[] args) {
		String pwdString="admin1234";
		String resultString = cn.techaction.utils.MD5Util.MD5Encode(pwdString, "utf-8", false);
		System.out.println(resultString);
		System.out.println();
		System.out.println(new Date());
		System.out.println(DateUtils.date2Str(new Date()));
	}
}

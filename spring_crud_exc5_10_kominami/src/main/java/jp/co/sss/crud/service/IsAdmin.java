package jp.co.sss.crud.service;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import jp.co.sss.crud.bean.EmployeeBean;
import jp.co.sss.crud.util.Constant;

@Service
public class IsAdmin {
	
	/**
	 * ログインユーザが管理者か判定してbooleanを返す
	 * @param session
	 * @return
	 */
	public boolean execute(HttpSession session) {
		EmployeeBean loginUser = (EmployeeBean)session.getAttribute("loginUser");
		if(loginUser.getAuthority().equals(Constant.DEFAULT_AUTHORITY)) {
			return false;
		}
		return true;
	}
}

package jp.co.sss.crud.service;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import jp.co.sss.crud.bean.EmployeeBean;

@Service
public class IsLoginUsersId {
	
	/**
	 * ログインユーザとempIdがマッチしている判定してbooleanを返す
	 * @param session
	 * @param empId
	 * @return
	 */
	public boolean execute(HttpSession session, Integer empId) {
		EmployeeBean loginUser = (EmployeeBean)session.getAttribute("loginUser");
		if(loginUser.getEmpId().equals(empId)) {
				return true;
		}
		return false;
	}
}

package jp.co.sss.crud.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jp.co.sss.crud.bean.EmployeeBean;
import jp.co.sss.crud.form.EmployeeForm;
import jp.co.sss.crud.service.IsAdmin;
import jp.co.sss.crud.service.IsLoginUsersId;
import jp.co.sss.crud.service.SearchForEmployeesByEmpIdService;
import jp.co.sss.crud.service.UpdateEmployeeService;
import jp.co.sss.crud.util.BeanManager;
import jp.co.sss.crud.util.Constant;

@Controller
public class UpdateController {

	@Autowired
	SearchForEmployeesByEmpIdService searchForEmployeesByEmpIdService;

	@Autowired
	UpdateEmployeeService updateEmployeeService;
	
	@Autowired
	IsAdmin isAdmin;
	
	@Autowired
	IsLoginUsersId isLoginUsersId;

	/**
	 * 社員情報の変更内容入力画面を出力
	 *
	 * @param empId
	 *            社員ID
	 * @param model
	 *            モデル
	 * @return 遷移先のビュー
	 * @throws ParseException 
	 */
	@RequestMapping(path = "/update/input", method = RequestMethod.GET)
	public String inputUpdate(Integer empId, @ModelAttribute EmployeeForm employeeForm, Model model, HttpSession session) {

		//権限が一般かつ、ログインユーザー以外の変更ページにアクセスしていた場合リダイレクト
		if(!isAdmin.execute(session) && !isLoginUsersId.execute(session, empId)) {
			return "redirect:/list";
		}
		
		EmployeeBean employeeBean =  searchForEmployeesByEmpIdService.execute(empId);;
		model.addAttribute("employeeForm", employeeForm = BeanManager.copyBeanToForm(employeeBean));
		return "update/update_input";
	}

	/**
	 * 社員情報の変更確認画面を出力
	 *
	 * @param employeeForm
	 *            変更対象の社員情報
	 * @param model
	 *            モデル
	 * @return 遷移先のビュー
	 */
	@RequestMapping(path = "/update/check", method = RequestMethod.POST)
	public String checkUpdate(
			@Valid @ModelAttribute EmployeeForm employeeForm,
			BindingResult result) {
		if (result.hasErrors()) {
			return "update/update_input";
		}

		return "update/update_check";
	}

	/**
	 * 変更内容入力画面に戻る
	 *
	 * @param employeeForm 変更対象の社員情報
	 * @return 遷移先のビュー
	 */
	@RequestMapping(path = "/update/back", method = RequestMethod.POST)
	public String backInputUpdate(@ModelAttribute EmployeeForm employeeForm) {
		return "update/update_input";
	}

	/**
	 * 社員情報の変更
	 *
	 * @param employeeForm
	 *            変更対象の社員情報
	 * @return 遷移先のビュー
	 */
	@RequestMapping(path = "/update/complete", method = RequestMethod.POST)
	public String completeUpdate(EmployeeForm employeeForm, HttpSession session) {

		//権限が一般の時に1(一般)をセット
		if (!isAdmin.execute(session)) {
			employeeForm.setAuthority(Constant.DEFAULT_AUTHORITY);
		}

		updateEmployeeService.execute(employeeForm);
		
		//ログインUserと更新対象Userが同じならセッション情報を更新
		if (isLoginUsersId.execute(session, employeeForm.getEmpId())) {
			EmployeeBean employeeBean = searchForEmployeesByEmpIdService.execute(employeeForm.getEmpId());
			session.setAttribute("loginUser", employeeBean);
		}

		return "redirect:/update/complete";
	}

	/**
	 * 完了画面の表示
	 * 
	 * @return 遷移先ビュー
	 */
	@RequestMapping(path = "/update/complete", method = RequestMethod.GET)
	public String completeUpdate() {
		return "update/update_complete";
	}

}

package jp.co.sss.crud.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sss.crud.bean.EmployeeBean;
import jp.co.sss.crud.form.EmployeeForm;
import jp.co.sss.crud.service.SearchForEmployeesByEmpIdService;
import jp.co.sss.crud.service.UpdateEmployeeService;
import jp.co.sss.crud.util.BeanManager;

@Controller
public class UpdateController {

	@Autowired
	SearchForEmployeesByEmpIdService searchForEmployeesByEmpIdService;

	@Autowired
	UpdateEmployeeService updateEmployeeService;

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
	public String inputUpdate(Integer empId, @ModelAttribute EmployeeForm employeeForm, Model model) {

		EmployeeBean employee = null;

		employee = searchForEmployeesByEmpIdService.execute(empId);

		employeeForm = BeanManager.copyBeanToForm(employee);
		model.addAttribute("employeeForm", employee);

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
	public String checkUpdate(@ModelAttribute EmployeeForm employeeForm) {

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
	public String completeUpdate(EmployeeForm employeeForm) {

				updateEmployeeService.execute(employeeForm);

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

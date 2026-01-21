package jp.co.sss.crud.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.sss.crud.entity.Employee;
import jp.co.sss.crud.service.SearchAllEmployeesService;
import jp.co.sss.crud.service.SearchForEmployeesByDepartmentService;
import jp.co.sss.crud.service.SearchForEmployeesByEmpNameService;
import jp.co.sss.crud.util.BeanManager;
import jp.co.sss.crud.util.Constant;

@Controller
public class ListController {

	@Autowired
	SearchAllEmployeesService searchAllEmployeesService;

	@Autowired
	SearchForEmployeesByEmpNameService searchForEmployeesByEmpNameService;

	@Autowired
	SearchForEmployeesByDepartmentService searchForEmployeesByDepartmentService;

	/**
	 * 社員情報を全件検索した結果を出力
	 *
	 * @param model モデル
	 * @return 遷移先のビュー
	 * @throws ParseException 
	 */
	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public String findAll(@PageableDefault(size = 10, sort = "empId", direction = Sort.Direction.ASC) Pageable pageable,
			Model model) {
		Page<Employee> employeeListPage = null;
		employeeListPage = searchAllEmployeesService.executeForPage(pageable);
		
		model.addAttribute("page", employeeListPage);
		model.addAttribute("employees", BeanManager.copyEntityListToBeanList(employeeListPage.getContent()));
		model.addAttribute("searchType", Constant.SEARTH_TYPE_FIND_All);

		return "list/list";
	}
	
	@RequestMapping(path = "/list/fragment", method = RequestMethod.GET)
	public String listFragment(
			@PageableDefault(size = 10, sort = "empId", direction = Sort.Direction.ASC) Pageable pageable,
			Model model, 
			@RequestParam Integer searchType,
			@RequestParam String empName,
			@RequestParam Integer deptId) {
		
		Page<Employee> employeeListPage = null;
		switch (searchType) {
		case Constant.SEARTH_TYPE_FIND_All:
			employeeListPage = searchAllEmployeesService.executeForPage(pageable); 
			break;
		case Constant.SEARTH_TYPE_FIND_BY_EMPNAME:
			employeeListPage = searchForEmployeesByEmpNameService.executeForPage(pageable,empName);
			break;
		case Constant.SEARTH_TYPE_FIND_BY_DEPARTMENT:
			employeeListPage = searchForEmployeesByDepartmentService.executeForPage(pageable, deptId);
			break;
		}
		
		
		model.addAttribute("page", employeeListPage);
		model.addAttribute("employees", BeanManager.copyEntityListToBeanList(employeeListPage.getContent()));
		model.addAttribute("searchType", searchType);
		
		return "list/list :: employeeList";
	}

	/**
	 * 社員情報を社員名検索した結果を出力
	 *
	 * @param empName 検索対象の社員名
	 * @param model モデル
	 * @return 遷移先のビュー
	 * @throws ParseException 
	 */
	@RequestMapping(path = "/list/empName", method = RequestMethod.GET)
	public String findByEmpName(@PageableDefault(size = 10, sort = "empId", direction = Sort.Direction.ASC) Pageable pageable,
			String empName, Model model) {
		Page<Employee> employeeListPage = null;
		employeeListPage = searchForEmployeesByEmpNameService.executeForPage(pageable, empName);
		
		model.addAttribute("page", employeeListPage);
		model.addAttribute("employees", BeanManager.copyEntityListToBeanList(employeeListPage.getContent()));
		model.addAttribute("searchType", Constant.SEARTH_TYPE_FIND_BY_EMPNAME);

		return "list/list";
	}

	/**
	 * 社員情報を部署ID検索した結果を出力
	 *
	 * @param deptId 検索対象の部署ID
	 * @param model モデル
	 * @return 選先のビュー
	 * @throws ParseException 
	 */
	@RequestMapping(path = "/list/deptId", method = RequestMethod.GET)
	public String findByDeptId(@PageableDefault(size = 10, sort = "empId", direction = Sort.Direction.ASC) Pageable pageable,
			Integer deptId, 
			Model model) {

		Page<Employee> employeeListPage = null;
		employeeListPage = searchForEmployeesByDepartmentService.executeForPage(pageable, deptId);
		
		model.addAttribute("page", employeeListPage);
		model.addAttribute("employees", BeanManager.copyEntityListToBeanList(employeeListPage.getContent()));
		model.addAttribute("searchType", Constant.SEARTH_TYPE_FIND_BY_DEPARTMENT);

		return "list/list";
	}
}

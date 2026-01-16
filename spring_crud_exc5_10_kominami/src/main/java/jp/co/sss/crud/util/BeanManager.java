package jp.co.sss.crud.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sss.crud.bean.EmployeeBean;
import jp.co.sss.crud.entity.Department;
import jp.co.sss.crud.entity.Employee;
import jp.co.sss.crud.form.EmployeeForm;

public class BeanManager {

	/**
	 * JavaBeans(EmployeeBean)の各フィールドの値をFormクラスにコピー
	 * 
	 * @param bean JavaBeans
	 * @return Formクラス
	 */
	public static EmployeeForm copyBeanToForm(EmployeeBean bean) {

		EmployeeForm form = new EmployeeForm();

		form.setEmpId(bean.getEmpId());
		form.setEmpPass(bean.getEmpPass());
		form.setEmpName(bean.getEmpName());
		form.setGender(bean.getGender());
		form.setAddress(bean.getAddress());
		form.setBirthday(bean.getBirthday());
		form.setAuthority(bean.getAuthority());
		form.setDeptId(bean.getDeptId());

		return form;
	}

	/**
	 * EmployeeForm -> EmployeeBean コピー
	 * 
	 * @param form
	 * @return EmployeeBean
	 */
	public static EmployeeBean copyFormToBean(EmployeeForm form) {

		EmployeeBean employeeBean = new EmployeeBean();

		employeeBean.setEmpId(form.getEmpId());
		employeeBean.setEmpPass(form.getEmpPass());
		employeeBean.setEmpName(form.getEmpName());
		employeeBean.setGender(form.getGender());
		employeeBean.setAddress(form.getAddress());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date formatDate = null;
		try {
			formatDate = sdf.parse(sdf.format(form.getBirthday()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		employeeBean.setBirthday(formatDate);
		employeeBean.setAuthority(form.getAuthority());
		employeeBean.setDeptId(form.getDeptId());

		String deptName = getDeptName(form.getDeptId());

		employeeBean.setDeptName(deptName);

		return employeeBean;

	}

	/**
	 * EmployeeForm -> Employee コピー
	 * 
	 * @param form
	 * @return Employee
	 */
	public static Employee copyFormToEntity(EmployeeForm form) {

		Employee employee = new Employee();

		employee.setEmpId(form.getEmpId());
		employee.setEmpPass(form.getEmpPass());
		employee.setEmpName(form.getEmpName());
		employee.setGender(form.getGender());
		employee.setAddress(form.getAddress());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date formatDate = null;
		try {
			formatDate = sdf.parse(sdf.format(form.getBirthday()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		employee.setBirthday(formatDate);
		employee.setAuthority(form.getAuthority());

		Department department = new Department();
		department.setDeptId(form.getDeptId());
		employee.setDepartment(department);

		return employee;

	}

	/**
	 * @param deptId
	 * @return deptName
	 */
	public static String getDeptName(Integer deptId) {

		String deptName = null;

		switch (deptId) {
		case 1:
			deptName = "営業部";
			break;
		case 2:
			deptName = "経理部";

			break;
		case 3:
			deptName = "総務部";

			break;

		default:
			break;
		}

		return deptName;
	}

	/**
	 * {@codeList<Employee> -> List<EmployeeBean>} 
	 * 
	 * @param empList
	 * @return EmployeeBeanリスト
	 */
	public static List<EmployeeBean> copyEntityListToBeanList(List<Employee> empList) {

		List<EmployeeBean> tempEmployeeBeans = new ArrayList<EmployeeBean>();

		for (Employee employee : empList) {

			EmployeeBean employeeBean = new EmployeeBean();

			employeeBean.setEmpId(employee.getEmpId());
			employeeBean.setEmpPass(employee.getEmpPass());
			employeeBean.setEmpName(employee.getEmpName());
			employeeBean.setGender(employee.getGender());
			employeeBean.setAddress(employee.getAddress());
			employeeBean.setBirthday(employee.getBirthday());
			employeeBean.setAuthority(employee.getAuthority());
			employeeBean.setDeptId(employee.getDepartment().getDeptId());

			tempEmployeeBeans.add(employeeBean);

		}

		return tempEmployeeBeans;
	}

	public static EmployeeBean copyEntityToBean(Employee employee) {

		EmployeeBean employeeBean = new EmployeeBean();

		employeeBean.setEmpId(employee.getEmpId());
		employeeBean.setEmpPass(employee.getEmpPass());
		employeeBean.setEmpName(employee.getEmpName());
		employeeBean.setGender(employee.getGender());
		employeeBean.setAddress(employee.getAddress());
		employeeBean.setBirthday(employee.getBirthday());
		employeeBean.setAuthority(employee.getAuthority());
		employeeBean.setDeptId(employee.getDepartment().getDeptId());

		return employeeBean;
	}

}

package jp.co.sss.crud.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.crud.bean.EmployeeBean;
import jp.co.sss.crud.entity.Employee;
import jp.co.sss.crud.repository.DepartmentRepository;
import jp.co.sss.crud.repository.EmployeeRepository;
import jp.co.sss.crud.util.BeanManager;
/**
 * 部署別従業員検索サービスクラス。
 * 指定された部署IDに所属する従業員情報を取得し、EmployeeBeanリストとして返却します。
 * 検索結果は従業員IDの昇順でソートされます。
 * 
 * @author SystemShared Co., Ltd.
 * @version 1.0
 * @since 1.0
 */
@Service
public class SearchForEmployeesByDepartmentService {

	/**
	 * 従業員データアクセス用リポジトリ。
	 * Spring DIによって自動注入されます。
	 */
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	DepartmentRepository departmentRepository;
	/**
	 * 指定された部署に所属する従業員情報を取得します。
	 * 
	 * 部署IDを基に該当部署のDepartmentオブジェクトを作成し、
	 * そのDepartmentオブジェクトを使用して従業員検索を行います。
	 * 検索結果はBeanManagerを使用してEmployeeBeanリストに変換して返却します。
	 * 
	 * @param deptId 検索対象の部署ID
	 * @return 指定部署に所属する従業員のEmployeeBeanリスト（従業員ID昇順）。
	 *         該当する従業員が存在しない場合は空のリストを返却
	 */
	public List<EmployeeBean> execute(Integer deptId){
		List<Employee> employeeList = employeeRepository.findByDepartment(departmentRepository.getReferenceById(deptId));
		
		if(employeeList.size() == 0) {
			List<EmployeeBean> employeeBeanList = new ArrayList<>();
			return employeeBeanList;
		}
		
		return BeanManager.copyEntityListToBeanList(employeeList);
	
	}
}

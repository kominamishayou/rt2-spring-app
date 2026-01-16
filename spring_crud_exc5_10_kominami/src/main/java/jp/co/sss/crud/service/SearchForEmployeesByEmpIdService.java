package jp.co.sss.crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.crud.bean.EmployeeBean;
import jp.co.sss.crud.entity.Employee;
import jp.co.sss.crud.repository.EmployeeRepository;
import jp.co.sss.crud.util.BeanManager;

/**
 * 従業員ID検索サービスクラス。
 * 指定された従業員IDを基に、該当する従業員情報を取得し、EmployeeBeanとして返却します。
 * データベースからの参照取得にはJPAのgetReferenceByIdメソッドを使用します。
 * 
 * @author SystemShared Co., Ltd.
 * @version 1.0
 * @since 1.0
 */
@Service
public class SearchForEmployeesByEmpIdService {

	/**
	 * 従業員データアクセス用リポジトリ。
	 * Spring DIによって自動注入されます。
	 */
	@Autowired
	EmployeeRepository employeeRepository;
	/**
	 * 指定された従業員IDの従業員情報を取得します。
	 * 
	 * 従業員IDを基にデータベースから該当する従業員エンティティの参照を取得し、
	 * BeanManagerを使用してEmployeeBeanに変換して返却します。
	 * 
	 * @param empId 検索対象の従業員ID
	 * @return 該当する従業員のEmployeeBean
	 */
	public EmployeeBean execute(Integer empId) {
		Employee employee = employeeRepository.findById(empId).orElse(null);
		return BeanManager.copyEntityToBean(employee);
	}
}
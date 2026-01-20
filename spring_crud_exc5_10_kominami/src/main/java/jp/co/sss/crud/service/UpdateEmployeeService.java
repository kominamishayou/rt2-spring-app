package jp.co.sss.crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jp.co.sss.crud.form.EmployeeForm;
import jp.co.sss.crud.repository.EmployeeRepository;
import jp.co.sss.crud.util.BeanManager;

/**
 * 従業員更新サービスクラス。
 * フォームから入力された従業員情報を基に、既存の従業員情報をデータベース上で更新します。
 * EmployeeFormからEmployeeエンティティへの変換処理も含まれます。
 * 
 * @author SystemShared Co., Ltd.
 * @version 1.0
 * @since 1.0
 */
@Service
public class UpdateEmployeeService {

	/**
	 * 従業員データアクセス用リポジトリ。
	 * Spring DIによって自動注入されます。
	 */
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	/**
	 * 従業員情報を更新します。
	 * 
	 * EmployeeFormに格納された従業員情報をEmployeeエンティティに変換し、
	 * データベース上の既存レコードを更新します。
	 * 変換処理はBeanManager.copyFormToEntityメソッドを使用して行います。
	 * JPAのsaveメソッドにより、主キー（従業員ID）が存在する場合は更新処理が実行されます。
	 * 
	 * @param employeeForm 更新する従業員情報を格納したフォームオブジェクト（従業員IDを含む必要があります）
	 */
	public void execute(EmployeeForm employeeForm) {
		employeeForm.setEmpPass(passwordEncoder.encode(employeeForm.getEmpPass()));
		employeeRepository.save(BeanManager.copyFormToEntity(employeeForm));
		return;
	}
}
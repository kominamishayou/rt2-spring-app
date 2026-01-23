package jp.co.sss.crud.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jp.co.sss.crud.form.EmployeeForm;
import jp.co.sss.crud.form.EmployeeFormForCsvUpdate;
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
	
	public void executeForCsv(List<EmployeeFormForCsvUpdate> employeeFormForUpdateCsvList, String initialPassword) {
		setInitialPassword(employeeFormForUpdateCsvList, initialPassword);
		
		List<EmployeeForm> employeeFormList = mappingToEmployeeForm(employeeFormForUpdateCsvList);
		
		for(EmployeeForm e : employeeFormList) {
			execute(e);
		}
	}
	
	/**
	 * 初期パスワードをセット
	 * @param employeeFormList
	 * @param initialPassword
	 */
	public void setInitialPassword(List<EmployeeFormForCsvUpdate> employeeFormList, String initialPassword) {
		for (EmployeeFormForCsvUpdate e : employeeFormList) {
			e.setEmpPass(initialPassword);
		}
	}
	
	/**
	 * CSV用Formリストを更新処理用Formリストへマッピング
	 * @param employeeFormList
	 * @return
	 */
	public List<EmployeeForm> mappingToEmployeeForm(List<EmployeeFormForCsvUpdate> employeeFormList) {
		List<EmployeeForm> resultList = new ArrayList<>();
		
		for(EmployeeFormForCsvUpdate e : employeeFormList) {
			EmployeeForm target = new EmployeeForm();
			
			target.setEmpId(e.getEmpId());
			target.setEmpName(e.getEmpName());
			target.setEmpPass(e.getEmpPass());
			target.setGender(e.getGender());
			target.setAddress(e.getAddress());
			target.setBirthday(e.getBirthday());
			target.setAuthority(e.getAuthority());
			target.setDeptId(e.getDeptId());
			
			resultList.add(target);
		}
		
		return resultList;
	}
}
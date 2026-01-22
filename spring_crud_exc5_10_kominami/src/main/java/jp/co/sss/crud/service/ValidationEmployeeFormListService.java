package jp.co.sss.crud.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jp.co.sss.crud.form.EmployeeForm;
@Service
public class ValidationEmployeeFormListService {
	
	@Autowired
	private Validator validator;
	
	public Map<EmployeeForm, Set<ConstraintViolation<EmployeeForm>>> execute(List<EmployeeForm> employeeFormList){
		Map<EmployeeForm, Set<ConstraintViolation<EmployeeForm>>> resultMap = new HashMap<>();
		
		for (EmployeeForm e : employeeFormList) {
			//IDにNullをセット
			e.setEmpId(null);
			
			//ダミーパスワードをセット
			e.setEmpPass("1111");
			
			Set<ConstraintViolation<EmployeeForm>> resultSet = validate(e);
			resultMap.put(e, resultSet);
		}
		
		return resultMap;
	}
	
	public Set<ConstraintViolation<EmployeeForm>> validate(EmployeeForm form){
		return validator.validate(form);
	}
}

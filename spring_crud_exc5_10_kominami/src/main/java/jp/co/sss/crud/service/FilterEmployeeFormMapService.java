package jp.co.sss.crud.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import jakarta.validation.ConstraintViolation;
import jp.co.sss.crud.form.EmployeeForm;

/**
 * employeeFormとエラー内容が入ったマップを
 * エラーなしとエラーありにフィルタリングするクラス
 */
@Service
public class FilterEmployeeFormMapService {

	public Map<EmployeeForm, Set<ConstraintViolation<EmployeeForm>>> getValid(
			Map<EmployeeForm, Set<ConstraintViolation<EmployeeForm>>> formMap) {
		Map<EmployeeForm, Set<ConstraintViolation<EmployeeForm>>> resultMap = new HashMap<>();

		for (Map.Entry<EmployeeForm, Set<ConstraintViolation<EmployeeForm>>> entry : formMap.entrySet()) {

			if (entry.getValue().isEmpty()) {
				resultMap.put(entry.getKey(), entry.getValue());
			}
		}

		return resultMap;
	}

	public Map<EmployeeForm, Set<ConstraintViolation<EmployeeForm>>> getInvalid(
			Map<EmployeeForm, Set<ConstraintViolation<EmployeeForm>>> formMap) {
		Map<EmployeeForm, Set<ConstraintViolation<EmployeeForm>>> resultMap = new HashMap<>();
		
		for (Map.Entry<EmployeeForm, Set<ConstraintViolation<EmployeeForm>>> entry : formMap.entrySet()) {
			
			if (!entry.getValue().isEmpty()) {
				resultMap.put(entry.getKey(), entry.getValue());
			}
		}
		
		return resultMap;
	}
}

package jp.co.sss.crud.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.crud.entity.Department;
import jp.co.sss.crud.entity.Employee;
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
	Employee findByEmpIdAndEmpPass(Integer empId, String empPass);
	
	List<Employee> findByEmpNameContaining(String empName);
	
	Page<Employee> findByEmpNameContaining(Pageable pageable,String empName);
	
	List<Employee> findByDepartment(Department department);
	
	Page<Employee> findByDepartment(Pageable pageable, Department department);
}

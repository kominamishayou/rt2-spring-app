package jp.co.sss.crud.service;

import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;

import jp.co.sss.crud.form.EmployeeForm;
import jp.co.sss.crud.form.EmployeeFormForCsvUpdate;

/**
 * Csv入力をBeanにパースするクラス
 */
@Service
public class CsvParseService {
	public List<EmployeeForm> execute(MultipartFile file) throws Exception {
		List<EmployeeForm> employeeFormList = new ArrayList<>();
		
		Charset cs = Charset.forName("MS932");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		try (CSVReader reader = new CSVReader(
				new InputStreamReader(file.getInputStream(), Charset.forName("MS932")))) {
			
			List<String[]> rows = reader.readAll();
			
			for (int i = 1; i < rows.size(); i++) {
				String[] r = rows.get(i);
				EmployeeForm e = new EmployeeForm();
				try {
					e.setEmpId(Integer.parseInt(r[0]));					
				} catch(Exception err) {
					e.setEmpId(null);										
				}
				e.setEmpName(r[1]);
				e.setGender(strToGender(r[2]));
				e.setAddress(r[3]);
				try {
					e.setBirthday(sdf.parse(r[4]));					
				} catch(Exception err) {
					e.setBirthday(null);
				}
				e.setAuthority(strToAuthority(r[5]));
				e.setDeptId(strToDeptId(r[6]));
				
				employeeFormList.add(e);
			}
		}
		return employeeFormList;
	}
	
	public List<EmployeeFormForCsvUpdate> executeForUpdate(MultipartFile file) throws Exception {
		List<EmployeeFormForCsvUpdate> employeeFormList = new ArrayList<>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		try (CSVReader reader = new CSVReader(
				new InputStreamReader(file.getInputStream(), Charset.forName("MS932")))) {
			
			List<String[]> rows = reader.readAll();
			
			for (int i = 1; i < rows.size(); i++) {
				String[] r = rows.get(i);
				EmployeeFormForCsvUpdate e = new EmployeeFormForCsvUpdate();
				try {
					e.setEmpId(Integer.parseInt(r[0]));					
				} catch(Exception err) {
					e.setEmpId(null);										
				}
				e.setEmpName(r[1]);
				e.setGender(strToGender(r[2]));
				e.setAddress(r[3]);
				try {
					e.setBirthday(sdf.parse(r[4]));					
				} catch(Exception err) {
					e.setBirthday(null);
				}
				e.setAuthority(strToAuthority(r[5]));
				e.setDeptId(strToDeptId(r[6]));
				
				employeeFormList.add(e);
			}
		}
		return employeeFormList;
	}
	
	
	private Integer strToGender(String s) {
	    if ("男性".equals(s)) return 1;
	    if ("女性".equals(s)) return 2;
	    return null;
	}
	
	private Integer strToDeptId(String deptName) {
		if ("営業部".equals(deptName)) return 1;
		if ("経理部".equals(deptName)) return 2;
		if ("総務部".equals(deptName)) return 3;
		return null;
	}

	private Integer strToAuthority(String authorityName) {
	    if ("一般".equals(authorityName)) return 1;
	    if ("管理者".equals(authorityName)) return 2;
	    return null;
	}

}

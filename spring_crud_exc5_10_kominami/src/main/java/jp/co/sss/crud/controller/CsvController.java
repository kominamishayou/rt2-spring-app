package jp.co.sss.crud.controller;

import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jp.co.sss.crud.bean.EmployeeBean;
import jp.co.sss.crud.form.EmployeeForm;
import jp.co.sss.crud.form.EmployeeFormForCsvUpdate;
import jp.co.sss.crud.form.InitialPasswordForm;
import jp.co.sss.crud.service.CsvParseService;
import jp.co.sss.crud.service.FilterEmployeeFormMapService;
import jp.co.sss.crud.service.RegisterEmployeeService;
import jp.co.sss.crud.service.SearchAllEmployeesService;
import jp.co.sss.crud.service.UpdateEmployeeService;
import jp.co.sss.crud.service.ValidationEmployeeFormListService;
@Controller
public class CsvController {
	
	@Autowired
	SearchAllEmployeesService searchAllEmployeesService;
	
	@Autowired
	CsvParseService csvParseService;
	
	@Autowired
	ValidationEmployeeFormListService validateFormList;
	
	@Autowired
	FilterEmployeeFormMapService filterFormMapService;
	
	@Autowired
	RegisterEmployeeService registEmployeeService;
	
	@Autowired
	UpdateEmployeeService updateEmployeeService;
	
	/**
	 * CSVインポート、エクスポートページを表示
	 * @return
	 */
	@RequestMapping("/csv")
	public String showCsvInput() {
		return "csv/csv_input";
	}
	
	/**
	 * 全件検索の結果をCSV形式でエクスポート
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(path = "/csv/export", method = RequestMethod.GET)
	public void downloadUserCsv(HttpServletResponse response, Integer csvType) throws Exception {
		
		//ヘッダ
		response.setContentType("text/csv");
		
		switch(csvType) {
		case 1: response.setHeader("Content-Disposition", "attachment; filename=\"list" + getNowDateStr() + ".csv\""); break;
		case 2: response.setHeader("Content-Disposition", "attachment; filename=\"sample.csv\""); break;
		}
		
		Charset cs = Charset.forName("MS932");
		
		List<EmployeeBean> employeeList = searchAllEmployeesService.execute();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		
		
		try(PrintWriter writer = new PrintWriter(response.getOutputStream(), true, cs)) {
			writer.println("社員ID,社員名,性別,住所,生年月日,権限,部署名");
			
			if(csvType.equals(2)) {
				writer.print("更新の場合は該当社員のID 登録の場合は空欄");
				writer.print(",");
				writer.print("30文字まで");
				writer.print(",");
				writer.print("男性or女性");
				writer.print(",");
				writer.print("60文字まで");
				writer.print(",");
				writer.print("yyyy/MM/dd");
				writer.print(",");
				writer.print("一般or管理者");
				writer.print(",");
				writer.println("営業部or経理部or総務部");
				writer.flush();
				return;
			}
			
			for (EmployeeBean e : employeeList) {
				writer.print(e.getEmpId());
				writer.print(",");
				writer.print(csvEscape(e.getEmpName()));
				writer.print(",");
				writer.print(genderToStr(e.getGender()));
				writer.print(",");
				writer.print(csvEscape(e.getAddress()));
				writer.print(",");
				writer.print(csvEscape(sdf.format(e.getBirthday())));
				writer.print(",");
				writer.print(csvEscape(authorityToStr(e.getAuthority())));
				writer.print(",");
				writer.println(csvEscape(e.getDeptName()));
			}
			writer.flush();
		}
	}
	
	/**
	 * CSVをインポートして初期パスワード入力フォームを表示
	 * @param file
	 * @param employeeForm
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(path = "/csv/import/regist", method = RequestMethod.POST)
	public String importRegistCsv(MultipartFile file, @ModelAttribute InitialPasswordForm initialPasswordForm,HttpSession session ,Model model) throws Exception {
		List<EmployeeForm> employeeFormList = csvParseService.execute(file);
		
		Map<EmployeeForm, Set<ConstraintViolation<EmployeeForm>>> resultMap = validateFormList.execute(employeeFormList);
		
		Map<EmployeeForm, Set<ConstraintViolation<EmployeeForm>>> validMap = filterFormMapService.getValid(resultMap);
		
		Map<EmployeeForm, Set<ConstraintViolation<EmployeeForm>>> invalidMap = filterFormMapService.getInvalid(resultMap);
		
		session.setAttribute("validFormMap", validMap);
		model.addAttribute("invalidFormMap", invalidMap);
		
		return "csv/csv_regist_input";
	}
	
	/**
	 * 登録内容確認画面を表示
	 * @param initialPasswordForm
	 * @param result
	 * @param session
	 * @return
	 */
	@RequestMapping(path = "/csv/import/regist/check", method = RequestMethod.POST)
	public String showRegistCsvCheck(@Valid @ModelAttribute InitialPasswordForm initialPasswordForm, 
			BindingResult result,
			HttpSession session) {
		if(result.hasErrors()) {
			return "csv/csv_regist_input";
		}
		session.setAttribute("empPass", initialPasswordForm.getEmpPass());
		return "csv/csv_regist_check";
	}
	
	@RequestMapping(path = "/csv/import/regist/complete")
	public String showRegistComplete(HttpSession session) {
		Map<EmployeeForm, Set<ConstraintViolation<EmployeeForm>>> validForMap =
				(Map<EmployeeForm, Set<ConstraintViolation<EmployeeForm>>>)
				session.getAttribute("validFormMap");
		
		List<EmployeeForm> employeeFormList = new ArrayList<>(validForMap.keySet());
		
		String empPass = (String)session.getAttribute("empPass");
		
		registEmployeeService.executeFromCsv(employeeFormList, empPass);
		
		session.removeAttribute("validFormMap");
		session.removeAttribute("empPass");
		return "regist/regist_complete";
	}
	
	@RequestMapping(path = "/csv/import/update", method = RequestMethod.POST)
	public String showUpdateCsvInput (MultipartFile file, @ModelAttribute InitialPasswordForm initialPasswordForm,
			HttpSession session ,Model model) throws Exception {
		List<EmployeeFormForCsvUpdate> employeeFormList = csvParseService.executeForUpdate(file);
		
		Map<EmployeeFormForCsvUpdate, Set<ConstraintViolation<EmployeeFormForCsvUpdate>>> resultMap = validateFormList.executeForUpdate(employeeFormList);
		
		Map<EmployeeFormForCsvUpdate, Set<ConstraintViolation<EmployeeFormForCsvUpdate>>>  validMap = filterFormMapService.getValidForUpdate(resultMap);
		
		Map<EmployeeFormForCsvUpdate, Set<ConstraintViolation<EmployeeFormForCsvUpdate>>>  invalidMap = filterFormMapService.getInvalidForUpdate(resultMap);
		
		session.setAttribute("validFormMap", validMap);
		model.addAttribute("invalidFormMap", invalidMap);
		
		return "csv/csv_update_input";
	}
	
	@RequestMapping(path = "/csv/import/update/check", method = RequestMethod.POST)
	public String showUpdateCsvCheck(@Valid @ModelAttribute InitialPasswordForm initialPasswordForm,
			BindingResult result,
			HttpSession session) {
		if(result.hasErrors()) {
			return "csv/csv_regist_input";
		}
		session.setAttribute("empPass", initialPasswordForm.getEmpPass());
		return "csv/csv_update_check";
	}
	
	@RequestMapping(path = "/csv/import/update/complete", method = RequestMethod.POST)
	public String showUpdateComplete(HttpSession session) {
		Map<EmployeeFormForCsvUpdate, Set<ConstraintViolation<EmployeeFormForCsvUpdate>>> validForMap =
				(Map<EmployeeFormForCsvUpdate, Set<ConstraintViolation<EmployeeFormForCsvUpdate>>>)
				session.getAttribute("validFormMap");
		
		List<EmployeeFormForCsvUpdate> employeeFormList = new ArrayList<>(validForMap.keySet());
		
		String empPass = (String)session.getAttribute("empPass");
		
		updateEmployeeService.executeForCsv(employeeFormList, empPass);
		
		session.removeAttribute("validFormMap");
		session.removeAttribute("empPass");
		return "update/update_complete";
	}
	
	/**
	 * CSVエスケープ エクスポート用 Serviceに移動させる
	 * @param s
	 * @return
	 */
    private String csvEscape(String s) {
        if (s == null) return "";
        boolean needQuote = s.contains(",") || s.contains("\"") || s.contains("\n") || s.contains("\r");
        String escaped = s.replace("\"", "\"\"");
        return needQuote ? "\"" + escaped + "\"" : escaped;
    }
    
    /**
     * 性別を数字から文字列へマッピング、エクスポート用 Serviceに移動させる
     */
    private String genderToStr(Integer gender) {
    	switch(gender) {
    	case 1: return "男性";
    	case 2: return "女性";
    	}
    	
    	return "";
    }
    
    /**
     * 権限を整数から文字列へマッピング、エクスポート用 Serviceへ移動させる
     * @param authority
     * @return
     */
    private String authorityToStr(Integer authority) {
    	switch(authority) {
    	case 1: return "一般";
    	case 2: return "管理者";
    	}
    	
    	return "";
    }
    
    private String getNowDateStr() {
    	Date now = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
    	return sdf.format(now);
    }
}

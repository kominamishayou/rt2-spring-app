package jp.co.sss.crud.controller;

import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.List;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import jp.co.sss.crud.bean.EmployeeBean;
import jp.co.sss.crud.form.EmployeeForm;
import jp.co.sss.crud.service.CsvParseService;
import jp.co.sss.crud.service.SearchAllEmployeesService;

@Controller
public class CsvController {
	
	@Autowired
	SearchAllEmployeesService searchAllEmployeesService;
	
	@Autowired
	CsvParseService csvParseService;
	
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
	public void downloadUserCsv(HttpServletResponse response) throws Exception {
		
		//ヘッダ
		response.setContentType("text/csv");
		
		response.setHeader("Content-Disposition", "attachment; filename=\"employeeList.csv\"");
		
		Charset cs = Charset.forName("MS932");
		
		List<EmployeeBean> employeeList = searchAllEmployeesService.execute();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		try(PrintWriter writer = new PrintWriter(response.getOutputStream(), true, cs)) {
			writer.println("社員ID,社員名,性別,住所,生年月日,権限,部署名");
			
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
	public String importCsv(MultipartFile file, @ModelAttribute EmployeeForm employeeForm) throws Exception {
		List<EmployeeBean> employeeBeanList = csvParseService.execute(file);
		for ( EmployeeBean e : employeeBeanList) {
			System.out.println(e);
		}
		return "csv/csv_regist_input";
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
}

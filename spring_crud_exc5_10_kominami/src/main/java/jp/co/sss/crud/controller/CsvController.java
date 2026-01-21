package jp.co.sss.crud.controller;

import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletResponse;
import jp.co.sss.crud.bean.EmployeeBean;
import jp.co.sss.crud.service.SearchAllEmployeesService;

@Controller
public class CsvController {
	
	@Autowired
	SearchAllEmployeesService searchAllEmployeesService;
	
	@RequestMapping("/csv/export")
	public void downloadUserCsv(HttpServletResponse response) throws Exception {
		
		//ヘッダ
		response.setContentType("text/csv");
		
		response.setHeader("Content-Disposition", "attachment; filename=\"employeeList.csv\"");
		
		Charset cs = Charset.forName("MS932");
		
		List<EmployeeBean> employeeList = searchAllEmployeesService.execute();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		try(PrintWriter writer = new PrintWriter(response.getOutputStream(), true, cs)) {
			writer.println("社員ID,社員名,性別,住所,生年月日,部署名");
			
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
				writer.println(csvEscape(e.getDeptName()));
			}
			writer.flush();
		}
	}
	
	//「CSVエスケープ」
    private String csvEscape(String s) {
        if (s == null) return "";
        boolean needQuote = s.contains(",") || s.contains("\"") || s.contains("\n") || s.contains("\r");
        String escaped = s.replace("\"", "\"\"");
        return needQuote ? "\"" + escaped + "\"" : escaped;
    }
    
    private String genderToStr(Integer gender) {
    	switch(gender) {
    	case 1: return "男性";
    	case 2: return "女性";
    	}
    	
    	return "";
    }
}

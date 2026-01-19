package jp.co.sss.crud.filter;

import java.io.IOException;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jp.co.sss.crud.bean.EmployeeBean;
import jp.co.sss.crud.util.Constant;

@Component
@Order(2)
public class AuthorityCheckFilter extends HttpFilter {
	@Override
	public void doFilter(
			HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String requestURL = request.getRequestURI();

		if(requestURL.contains("/regist") ||
				requestURL.contains("/delete")) {
			HttpSession session = request.getSession();
			EmployeeBean loginUser = (EmployeeBean)session.getAttribute("loginUser");
			if( (loginUser.getAuthority().equals(Constant.DEFAULT_AUTHORITY))) {
				response.sendRedirect("/spring_crud");
				return;
			}
		}
		chain.doFilter(request, response);
	}
}

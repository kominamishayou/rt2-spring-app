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

@Component
@Order(1)
public class LoginCheckFilter extends HttpFilter{
	@Override
	public void doFilter(
			HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String requestURL = request.getRequestURI();

		if( requestURL.contains("/list") ||
				requestURL.contains("/regist") ||
				requestURL.contains("/update") ||
				requestURL.contains("/delete")) {
			HttpSession session = request.getSession();
			if( session.getAttribute("loginUser") == null) {
				response.sendRedirect("/spring_crud");
				return;
			}
		}
		chain.doFilter(request, response);
	}
}

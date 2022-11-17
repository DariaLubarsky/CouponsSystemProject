package app.core.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import app.core.util.JwtUtil;
import app.core.util.JwtUtil.Client;
import app.core.util.JwtUtil.Client.ClientType;
import io.swagger.models.HttpMethod;

public class LoginFilterAdmin implements Filter {

	// we need JwtUtil to check token validity
	private JwtUtil jwtUtil;

	public LoginFilterAdmin(JwtUtil jwtUtil) {
		super();
		this.jwtUtil = jwtUtil;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("========== filter");

		HttpServletRequest req = (HttpServletRequest) request;
		System.err.println(req.getMethod() + "  |  " + req.getRequestURI());
		HttpServletResponse resp = (HttpServletResponse) response;
		resp.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		resp.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "*");
		resp.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");
		resp.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "*");
		resp.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET,PUT,POST,DELETE");
		String token = req.getHeader("Autorization");
		try {
			if (req.getMethod().equals(HttpMethod.OPTIONS.toString())) {
				System.out.println("hereeeeee");
				chain.doFilter(request, response);
				return;
			}
			if (token.contains("Bearer "))
				token = token.replace("Bearer ", "");
			Client client = this.jwtUtil.extractClient(token);
			System.out.println("token: " + token);
			System.out.println("client: " + client.getId());
			if (!(client.getType() == ClientType.ADMIN)) {
				resp.sendError(HttpStatus.UNAUTHORIZED.value(), "you are not admin o_O");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
			resp.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
			return;
		}
		System.out.println("doFilter");

		chain.doFilter(request, response);

	}

}
package com.stackroute.keepnote.jwtfilter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import com.mongodb.util.JSONParseException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/* This class implements the custom filter by extending org.springframework.web.filter.GenericFilterBean.  
 * Override the doFilter method with ServletRequest, ServletResponse and FilterChain.
 * This is used to authorize the API access for the application.
 */

public class JwtFilter extends GenericFilterBean {

	/*
	 * Override the doFilter method of GenericFilterBean. Retrieve the
	 * "authorization" header from the HttpServletRequest object. Retrieve the
	 * "Bearer" token from "authorization" header. If authorization header is
	 * invalid, throw Exception with message. Parse the JWT token and get claims
	 * from the token using the secret key Set the request attribute with the
	 * retrieved claims Call FilterChain object's doFilter() method
	 */

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String authHeader = request.getHeader("authorization");
		if ("OPTION".equals(request.getMethod())) {
			response.setStatus(HttpServletResponse.SC_OK);
			filterChain.doFilter(request, response);
		} else {
			if (null == authHeader || !authHeader.startsWith("Bearer ")) {
				throw new ServletException("Missing or Invalid Authorization header");
			}
			String token = authHeader.split(" ")[1].trim();
			try {
				final Claims claims = Jwts.parser().setSigningKey("apple").parseClaimsJws(token).getBody();
				request.setAttribute("claims", claims);
				filterChain.doFilter(request, response);
			} catch (JSONParseException e) {
				System.out.println(e.getMessage());
			}
		}

	}
}

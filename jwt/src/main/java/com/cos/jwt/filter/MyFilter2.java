package com.cos.jwt.filter;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class MyFilter2 implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("필터2 통과");
		PrintWriter out = response.getWriter();
		//out.print("안녕");//이상태면 필터에서 끝나버림
		chain.doFilter(request, response); //chain으로 옮겨줘야함
		
	}
	
	
 
}

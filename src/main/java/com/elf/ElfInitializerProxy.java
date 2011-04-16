package com.elf;
import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ElfInitializerProxy extends GenericServlet {

    private static final long serialVersionUID = 8212501958503780702L;
    private String targetBeanName;
	private Servlet target;
	
	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		target.service(request, response);
	}

	@Override
	public void init() throws ServletException {
		this.targetBeanName = getServletName();
		getServletBean();
		target.init(getServletConfig());
	}
	
	private void getServletBean() {
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		this.target = (Servlet)wac.getBean(targetBeanName);
	}

}
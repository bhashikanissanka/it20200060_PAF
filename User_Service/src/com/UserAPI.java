package com;

import model.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@WebServlet("/UserAPI")
public class UserAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private Object userObj;
	
	 
   
    public UserAPI() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User userObj = new User();
		String output = userObj.insertUser(
				request.getParameter("Username"),
				request.getParameter("Email"),
				request.getParameter("Phoneno"),
				request.getParameter("Nic"));
		
	    response.getWriter().write(output);
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Map paras = getParasMap(request);
		User userObj = new User();
		String output = userObj.updateUser(paras.get("hidUserSave").toString(),
				 paras.get("Username").toString(),
				paras.get("Email").toString(),
				paras.get("Phoneno").toString(),
				paras.get("Nic").toString());
		
		response.getWriter().write(output);
	} 
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Map paras = getParasMap(request);
		User userObj = new User();
		String output = userObj.deleteUser(paras.get("Userid").toString());
		response.getWriter().write(output);
	}

	private static Map getParasMap(HttpServletRequest request)
	{
		Map<String, String> map = new HashMap<String, String>();
	try
	 {
		 Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
		 String queryString = scanner.hasNext() ?
		 scanner.useDelimiter("\\A").next() : "";
		 scanner.close();
		 String[] params = queryString.split("&");
		 for (String param : params)
	 { 
		 String[] p = param.split("=");
		 map.put(p[0], p[1]);
		 }
	}
		catch (Exception e)
			 {
			 }
		return map;
			}
}

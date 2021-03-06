package com.mayer.mvc.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mayer.mvc.dao.CriteriaCustomer;
import com.mayer.mvc.dao.CustomerDAO;
import com.mayer.mvc.dao.impl.CustomerDAOJdbcImpl;
import com.mayer.mvc.domain.Customer;

/**
 * Servlet implementation class CustomerServlet
 */
@WebServlet("*.do")
public class CustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private CustomerDAO customerDAO = new CustomerDAOJdbcImpl();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String servletPath = request.getServletPath();
		String methodName = servletPath.substring(1);
		methodName = methodName.substring(0, methodName.length()-3);
		try {
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			method.invoke(this, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String forwardPath = "/error.jsp";
		String idStr = request.getParameter("id");
		
		try {
			Customer customer = customerDAO.get(Integer.parseInt(idStr));
			if(customer != null){
				forwardPath = "updatecustomer.jsp";
				request.setAttribute("customer", customer);
			}
		} catch (Exception e) {
			
		}
		request.getRequestDispatcher(forwardPath).forward(request,response);
		
	}
	private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		String oldname = request.getParameter("oldname");
		
		if(!oldname.equalsIgnoreCase(name)){
			long count = customerDAO.getCountWithName(name);
			
			if(count > 0){
				request.setAttribute("message", "用户名" + name + "已经被占用，请重新选择！");
				request.getRequestDispatcher("/updatecustomer.jsp").forward(request, response);
				return;
			}
		}
		Customer customer =new Customer(name, address, phone);
		customer.setId(Integer.parseInt(id));
		customerDAO.update(customer);
		
		response.sendRedirect("query.do");
		
	}
	private void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		
		CriteriaCustomer cc = new CriteriaCustomer(name, address, phone);
		
		
		List<Customer> customers = customerDAO.getForListWithCriteriaCustomer(cc);
		
		request.setAttribute("customers", customers);
		
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}
	private void deleteCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		int id = 0;
		try {
			id = Integer.parseInt(idStr);
			customerDAO.delete(id);
		} catch (Exception e) {
			
		}
		response.sendRedirect("query.do");
	}
	private void addCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		
		long count = customerDAO.getCountWithName(name);
		
		if(count > 0){
			request.setAttribute("message", "用户名" + name + "已经被占用，请重新选择！");
			request.getRequestDispatcher("/newcustomer.jsp").forward(request, response);
			return;
		}
		Customer customer = new Customer(name, address, phone);
		
		customerDAO.save(customer);
		
		response.sendRedirect("success.jsp");
	}

}

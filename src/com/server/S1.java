package com.server;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.util.PoiForExcel;
import com.util.Points;

/**
 * 
 * @author lenovo
 *
 */
public class S1 extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
		 * Constructor of the object.
		 */
	public S1() {
		super();
	}

	/**
		 * Destruction of the servlet. <br>
		 */
	@Override
	public void destroy() {
		// Just puts "destroy" string in log
		//super.destroy(); 
		// Put your code here
		System.out.println("destroy S1!");
	}

	/**
		 * The doGet method of the servlet. <br>
		 *
		 * This method is called when a form has its tag value method equals to get.
		 * 
		 * @param request the request send by the client to the server
		 * @param response the response send by the server to the client
		 * @throws ServletException if an error occurred
		 * @throws IOException if an error occurred
		 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
		 * The doPost method of the servlet. <br>
		 *
		 * This method is called when a form has its tag value method equals to post.
		 * 
		 * @param request the request send by the client to the server
		 * @param response the response send by the server to the client
		 * @throws ServletException if an error occurred
		 * @throws IOException if an error occurred
		 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		try{
			Integer n = Integer.parseInt(request.getParameter("n"));			
			out.write(getJson(n));
			out.flush();
			if(n != null){
				out.close();
				return;
			}
		}catch(Exception e){
			
		}
		try{
			int col,col2,sheet;
			col = Integer.parseInt(request.getParameter("col"));
			col2 = Integer.parseInt(request.getParameter("col2"));
			sheet = Integer.parseInt(request.getParameter("sheet"));
			String json = getExcelRow(col, col2, sheet);
			out.write(json);
			out.flush();
			out.close();
		}catch(Exception e){
			out.close();
		}
		
	}
	static ObjectMapper mapper = new ObjectMapper();
	static Random rand = new Random();
	//一个横坐标自增······
	static int x=0;
	public String getExcelRow(int col,int col2,int sheet){
		String json = "";
		LinkedList<Points> list = PoiForExcel.getXcol(col, col2, sheet);
		try {
			json = mapper.writeValueAsString(list);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}
	public String getJson(int n){
		LinkedList<Point> list = new LinkedList<Point>();
		while(n-->0){
			list.add(new Point(x++, rand.nextInt(100)));			
		}

		String json ="";
		try {
			json = mapper.writeValueAsString(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	/**
		 * Initialization of the servlet. <br>
		 *
		 * @throws ServletException if an error occurs
		 */
	@Override
	public void init() throws ServletException {
		// Put your code here
		String path = this.getServletContext().getRealPath("/data")+ File.separatorChar
				+ "book2.xlsx";
		PoiForExcel.setPath(path);
		System.out.println(path);
		System.out.println("初始化S1");
	}
}

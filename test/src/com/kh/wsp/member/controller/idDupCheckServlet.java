package com.kh.wsp.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.wsp.member.model.service.MemberService;

@WebServlet("/idDupCheckServlet")
public class idDupCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String id = request.getParameter("id");
		
//		try {
//			int result = new MemberService().inDupCheck(id);
//			request.setAttribute("result", result);
//			
//			RequestDispatcher view = request.getRequestDispatcher("idDupForm.do");
//			view.forward(request, response);
//		
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
	
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

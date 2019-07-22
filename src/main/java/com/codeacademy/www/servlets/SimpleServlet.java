package com.codeacademy.www.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class SimpleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/plain");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("Hello World!");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Integer num1 = Integer.parseInt(request.getParameter("num1"));
        Integer num2 = Integer.parseInt(request.getParameter("num2"));
        String operation = request.getParameter("operation");

        try {
            Integer result = calculate(num1, num2, operation);
            request.setAttribute("result", result);
            response.setContentType("text/plain");
            response.setStatus(200);
            response.getWriter().println(result);
        } catch (Exception ex) {
            response.sendError(503, "Something went wrong");
        }

    }

    private Integer calculate(Integer num1, Integer num2, String operation) {
        if (operation.equalsIgnoreCase("add")) {
            return num1 + num2;
        } else if (operation.equalsIgnoreCase("subs")) {
            return num1 - num2;
        } else {
            throw new UnsupportedOperationException("Operation " + operation + " is not supported");
        }
    }

}

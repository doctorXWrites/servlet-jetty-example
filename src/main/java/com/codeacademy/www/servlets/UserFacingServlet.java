package com.codeacademy.www.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

class Query {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYob() {
        return yob;
    }

    public void setYob(Integer yob) {
        this.yob = yob;
    }

    private String name;
    private Integer yob;
}

public class UserFacingServlet extends HttpServlet {
    ObjectMapper om = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/plain");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("Hello World!");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        InputStream is = request.getInputStream();
        Scanner sc = new Scanner(is);
        StringBuilder sb = new StringBuilder();
        while(sc.hasNext()) {
            sb.append(sc.nextLine());
        }

        Query query = om.readValue(sb.toString(), Query.class);

        String url = "http://localhost:8090/api/calculate";
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        String params = "num1=2019&num2=" + query.getYob() + "&operation=subs";
        byte[] postData = params.getBytes( StandardCharsets.UTF_8 );
        connection.setRequestMethod("POST");
        connection.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
        connection.setDoOutput(true);
        DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
        dataOutputStream.write(postData);

        is = connection.getInputStream();
        sc = new Scanner(is);
        String result = sc.nextLine();
        response.getWriter().println("Hey " + query.getName() + " your age is " + result + " years!");
    }
}

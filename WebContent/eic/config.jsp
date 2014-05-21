<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.rojosewe.eic.fun.Conf" %>
    
<%
Conf.JDBC = getServletContext().getInitParameter("jdbc");
Conf.USERJDBC = getServletContext().getInitParameter("userjdbc");
Conf.PSSWDJDBC = getServletContext().getInitParameter("psswdjdbc");
response.sendError(401);
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.pharmacystore.daoimpl.UserDaoImpl" %>
<%@ page import="com.pharmacystore.pojo.User" %>

<%
    // Check if the user is logged in
    if (session.getAttribute("USER") != null) {
        String userid = (String) session.getAttribute("USER");
        
        // Get the new address from the form submission
        String newAddress = request.getParameter("newAddress");

        // Update the user's address in the database
        UserDaoImpl userDao = new UserDaoImpl();
        User user = userDao.getUserById(userid);

        // Assuming you have appropriate methods in your UserDaoImpl to update the user's address
        user.setAddress(newAddress);
        boolean updateSuccess = userDao.updateUser(user);

        if (updateSuccess) {
            // Redirect to a success page
            response.sendRedirect("UpdateAddressSuccess.jsp");
        } else {
            // Redirect to a failure page
            response.sendRedirect("UpdateAddressFailure.jsp");
        }
    } else {
        // If the user is not logged in, redirect to the login page
        response.sendRedirect("login.jsp");
    }
%>

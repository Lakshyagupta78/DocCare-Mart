<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.pharmacystore.daoimpl.UserDaoImpl" %>
<%@ page import="com.pharmacystore.pojo.User" %>

<%
    // Check if the user is logged in
    if (session.getAttribute("USER") != null) {
        String userid = (String) session.getAttribute("USER");

        // Get the current user details
        UserDaoImpl userDao = new UserDaoImpl();
        User user = userDao.getUserById(userid);

        // Display the current address
        String currentAddress = user.getCity() + ", " + user.getState() + " - " + user.getPincode();

        // Display a form to update the address
%>
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="ISO-8859-1">
            <title>Update Address</title>
            <!-- Add your CSS and JavaScript links here -->
        </head>
        <body>

            <h2>Update Address</h2>

            <p>Current Address: <%= currentAddress %></p>

            <form action="ProcessUpdateAddress.jsp" method="post">
                <label for="newAddress">New Address:</label>
                <input type="text" id="newAddress" name="newAddress" required>

                <button type="submit">Update Address</button>
            </form>

        </body>
        </html>
<%
    } else {
        // If the user is not logged in, redirect to the login page
        response.sendRedirect("login.jsp");
    }
%>

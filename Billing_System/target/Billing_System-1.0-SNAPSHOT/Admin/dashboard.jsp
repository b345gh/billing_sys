<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.example.billing_system.dto.UserResponseDTO" %>
<%
  // Check if user is logged in
  UserResponseDTO user = (UserResponseDTO) session.getAttribute("user");
  if (user == null) {
    response.sendRedirect(request.getContextPath() + "/auth/login");
    return;
  }
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Dashboard - Pahana Edu</title>

</head>
<body class="bg-gray-50 min-h-screen">
<!-- Navigation -->
<nav class="bg-white shadow-sm border-b">
  <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
    <div class="flex justify-between items-center h-16">
      <div class="flex items-center">
        <div class="w-8 h-8 bg-indigo-600 rounded-lg flex items-center justify-center mr-3">
          <i class="fas fa-graduation-cap text-white"></i>
        </div>
        <h1 class="text-xl font-bold text-gray-900">Pahana Edu</h1>
      </div>
      <div class="flex items-center space-x-4">
        <div class="flex items-center space-x-2">
          <div class="w-8 h-8 bg-indigo-100 rounded-full flex items-center justify-center">
            <i class="fas fa-user text-indigo-600"></i>
          </div>
          <span class="text-gray-700 font-medium"><%= user.getFirstName() %> <%= user.getLastName() %></span>
        </div>
        <a href="${pageContext.request.contextPath}/auth/logout"
           class="bg-red-600 text-white px-4 py-2 rounded-lg hover:bg-red-700 transition duration-200">
          <i class="fas fa-sign-out-alt mr-2"></i>Logout
        </a>
      </div>
    </div>
  </div>
</nav>

<%@ include file="../sidebar.jsp" %>
</body>
</html>
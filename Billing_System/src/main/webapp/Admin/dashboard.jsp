<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.example.billing_system.business.dto.UserResponseDTO" %>
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
<body>
<!-- Navigation -->
<nav>
  <div>
    <div>
      <div>
        <h1>Pahana Edu</h1>
      </div>
      <div>
        <div>
          <div>
          </div>
          <span><%= user.getFirstName() %> <%= user.getLastName() %></span>
        </div>
        <a href="${pageContext.request.contextPath}/auth/logout">
          Logout
        </a>
      </div>
    </div>
  </div>
</nav>

<%@ include file="../sidebar.jsp" %>
</body>
</html>
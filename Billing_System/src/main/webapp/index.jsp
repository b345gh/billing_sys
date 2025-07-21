<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pahana Edu</title>

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
                <a href="${pageContext.request.contextPath}/auth/register">
                    Get Started
                </a>
            </div>
        </div>
    </div>
</nav>

<!-- Hero Section -->
<div>
    <div>
        <h1>
            Welcome to <span>Pahana Edu</span>
        </h1>
        <p>
            Your gateway to quality education. Join thousands of learners who are advancing their skills
            and achieving their goals with our comprehensive learning management system.
        </p>
        <div>
            <a href="${pageContext.request.contextPath}/auth/register">
                Start Learning Today
            </a>
            <a href="login.jsp">
                Sign In
            </a>
        </div>
    </div>
</div>


</body>
</html>
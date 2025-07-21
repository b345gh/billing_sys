<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Login - Pahana Edu</title>
</head>
<body>
<div>
  <!-- Logo/Header -->
  <div>
    <h1>Pahana Edu</h1>
    <p>Sign in to your account</p>
  </div>

  <!-- Login Form -->
  <div>
    <% if (request.getAttribute("error") != null) { %>
    <div>
      <div>
        <i class="fas fa-exclamation-circle"></i>
        <%= request.getAttribute("error") %>
      </div>
    </div>
    <% } %>

    <% if (request.getAttribute("success") != null) { %>
    <div>
      <div>
        <i class="fas fa-check-circle mr-2"></i>
        <%= request.getAttribute("success") %>
      </div>
    </div>
    <% } %>

    <form action="${pageContext.request.contextPath}/auth/login" method="post" class="space-y-6">
      <div>
        <label for="username">
          Username
        </label>
        <input type="text"
               id="username"
               name="username"
               required
               placeholder="Enter your username">
      </div>

      <div>
        <label for="password" class="block text-sm font-medium text-gray-700 mb-2">
          Password
        </label>
        <div class="relative">
          <input type="password"
                 id="password"
                 name="password"
                 required
                 placeholder="Enter your password">
          <button type="button" onclick="togglePassword()">
            <i id="passwordIcon" class="fas fa-eye"></i>
          </button>
        </div>
      </div>

      <button type="submit">
        <i class="fas fa-sign-in-alt mr-2"></i>Sign In
      </button>
    </form>

    <div>
      <p>
        Don't have an account?
        <a href="${pageContext.request.contextPath}/auth/register">
          Sign up here
        </a>
      </p>
    </div>
  </div>

</div>

<script>
  function togglePassword() {
    const passwordInput = document.getElementById('password');
    const passwordIcon = document.getElementById('passwordIcon');

    if (passwordInput.type === 'password') {
      passwordInput.type = 'text';
      passwordIcon.className = 'fas fa-eye-slash';
    } else {
      passwordInput.type = 'password';
      passwordIcon.className = 'fas fa-eye';
    }
  }

  // Auto-hide alerts after 5 seconds
  setTimeout(() => {
    const alerts = document.querySelectorAll('.bg-red-50, .bg-green-50');
    alerts.forEach(alert => {
      alert.style.transition = 'opacity 0.5s';
      alert.style.opacity = '0';
      setTimeout(() => alert.remove(), 500);
    });
  }, 5000);
</script>
</body>
</html>
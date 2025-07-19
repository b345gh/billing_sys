<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.example.billing_system.dto.UserRegistrationDTO" %>
<%
  UserRegistrationDTO registrationData = (UserRegistrationDTO) request.getAttribute("registrationData");
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Register - Pahana Edu</title>


</head>
<body>
<div>
  <!-- Logo/Header -->
  <div>
    <h1>Pahana Edu</h1>
    <p>Create your account</p>
  </div>

  <!-- Registration Form -->
  <div>

    <form action="${pageContext.request.contextPath}/auth/register" method="post" id="registerForm">
      <div>
        <div>
          <label>
            <i class="fas fa-user mr-2"></i>First Name
          </label>
          <input type="text"
                 id="firstName"
                 name="firstName"
                 required
                 value="<%= registrationData != null ? (registrationData.getFirstName() != null ? registrationData.getFirstName() : "") : "" %>"
                 placeholder="First name">
        </div>
        <div>
          <label for="lastName" class="block text-sm font-medium text-gray-700 mb-2">
            <i class="fas fa-user mr-2"></i>Last Name
          </label>
          <input type="text"
                 id="lastName"
                 name="lastName"
                 required
                 value="<%= registrationData != null ? (registrationData.getLastName() != null ? registrationData.getLastName() : "") : "" %>"
                 placeholder="Last name">
        </div>
      </div>

      <div>
        <label for="username">
          <i class="fas fa-at mr-2"></i>Username
        </label>
        <input type="text"
               id="username"
               name="username"
               required
               value="<%= registrationData != null ? (registrationData.getUsername() != null ? registrationData.getUsername() : "") : "" %>"
               placeholder="Choose a username">
      </div>

      <div>
        <label for="email" class="block text-sm font-medium text-gray-700 mb-2">
          <i class="fas fa-envelope mr-2"></i>Email Address
        </label>
        <input type="email"
               id="email"
               name="email"
               required
               value="<%= registrationData != null ? (registrationData.getEmail() != null ? registrationData.getEmail() : "") : "" %>"
               placeholder="Enter your email">
      </div>

      <div>
        <label for="password" class="block text-sm font-medium text-gray-700 mb-2">
          <i class="fas fa-lock mr-2"></i>Password
        </label>
        <div class="relative">
          <input type="password"
                 id="password"
                 name="password"
                 required
                 minlength="6"
                 placeholder="Create a password">
          <button type="button"
                  onclick="togglePassword('password', 'passwordIcon')"
                  class="absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-500 hover:text-gray-700">
            <i id="passwordIcon" class="fas fa-eye"></i>
          </button>
        </div>
        <p class="text-xs text-gray-500 mt-1">Password must be at least 6 characters long</p>
      </div>

      <div>
        <label for="confirmPassword" class="block text-sm font-medium text-gray-700 mb-2">
          <i class="fas fa-lock mr-2"></i>Confirm Password
        </label>
        <div class="relative">
          <input type="password"
                 id="confirmPassword"
                 name="confirmPassword"
                 required
                 class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 transition duration-200"
                 placeholder="Confirm your password">
          <button type="button"
                  onclick="togglePassword('confirmPassword', 'confirmPasswordIcon')"
                  class="absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-500 hover:text-gray-700">
            <i id="confirmPasswordIcon" class="fas fa-eye"></i>
          </button>
        </div>
        <div id="passwordMatch" class="text-xs mt-1 hidden">
          <span id="passwordMatchText"></span>
        </div>
      </div>

      <button type="submit"
              id="submitBtn"
              class="w-full bg-indigo-600 text-white py-3 px-4 rounded-lg hover:bg-indigo-700 focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2 transition duration-200 font-medium">
        <i class="fas fa-user-plus mr-2"></i>Create Account
      </button>
    </form>

    <div class="mt-6 text-center">
      <p class="text-gray-600">
        Already have an account?
        <a href="${pageContext.request.contextPath}/auth/login"
           class="text-indigo-600 hover:text-indigo-700 font-medium">
          Sign in here
        </a>
      </p>
    </div>
  </div>

  <!-- Footer -->
  <div class="text-center mt-8 text-gray-500 text-sm">
    <p>&copy; 2025 Pahana Edu. All rights reserved.</p>
  </div>
</div>

<script>
  function togglePassword(inputId, iconId) {
    const passwordInput = document.getElementById(inputId);
    const passwordIcon = document.getElementById(iconId);

    if (passwordInput.type === 'password') {
      passwordInput.type = 'text';
      passwordIcon.className = 'fas fa-eye-slash';
    } else {
      passwordInput.type = 'password';
      passwordIcon.className = 'fas fa-eye';
    }
  }

  // Password confirmation validation
  document.getElementById('confirmPassword').addEventListener('input', function() {
    const password = document.getElementById('password').value;
    const confirmPassword = this.value;
    const matchDiv = document.getElementById('passwordMatch');
    const matchText = document.getElementById('passwordMatchText');
    const submitBtn = document.getElementById('submitBtn');

    if (confirmPassword.length > 0) {
      matchDiv.classList.remove('hidden');

      if (password === confirmPassword) {
        matchText.textContent = 'Passwords match';
        matchText.className = 'text-green-600';
        submitBtn.disabled = false;
        submitBtn.classList.remove('opacity-50', 'cursor-not-allowed');
      } else {
        matchText.textContent = 'Passwords do not match';
        matchText.className = 'text-red-600';
        submitBtn.disabled = true;
        submitBtn.classList.add('opacity-50', 'cursor-not-allowed');
      }
    } else {
      matchDiv.classList.add('hidden');
      submitBtn.disabled = false;
      submitBtn.classList.remove('opacity-50', 'cursor-not-allowed');
    }
  });

  // Form validation
  document.getElementById('registerForm').addEventListener('submit', function(e) {
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;

    if (password !== confirmPassword) {
      e.preventDefault();
      alert('Passwords do not match!');
      return false;
    }

    if (password.length < 6) {
      e.preventDefault();
      alert('Password must be at least 6 characters long!');
      return false;
    }
  });

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
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
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
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

    <!-- Main Content -->
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <!-- Welcome Section -->
        <div class="bg-white rounded-xl shadow-sm p-8 mb-8">
            <div class="flex items-center justify-between">
                <div>
                    <h2 class="text-3xl font-bold text-gray-900 mb-2">
                        Welcome back, <%= user.getFirstName() %>! 👋
                    </h2>
                    <p class="text-gray-600">
                        You're logged in as <strong><%= user.getUsername() %></strong> (<%= user.getEmail() %>)
                    </p>
                </div>
                <div class="w-20 h-20 bg-indigo-100 rounded-full flex items-center justify-center">
                    <i class="fas fa-user-graduate text-indigo-600 text-3xl"></i>
                </div>
            </div>
        </div>

        <!-- Dashboard Cards -->
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mb-8">
            <!-- Students Card -->
            <div class="bg-white rounded-xl shadow-sm p-6 hover:shadow-md transition duration-200">
                <div class="flex items-center justify-between">
                    <div>
                        <h3 class="text-lg font-semibold text-gray-900 mb-2">Students</h3>
                        <p class="text-3xl font-bold text-indigo-600">0</p>
                        <p class="text-sm text-gray-500">Total enrolled</p>
                    </div>
                    <div class="w-12 h-12 bg-indigo-100 rounded-lg flex items-center justify-center">
                        <i class="fas fa-users text-indigo-600 text-xl"></i>
                    </div>
                </div>
            </div>

            <!-- Courses Card -->
            <div class="bg-white rounded-xl shadow-sm p-6 hover:shadow-md transition duration-200">
                <div class="flex items-center justify-between">
                    <div>
                        <h3 class="text-lg font-semibold text-gray-900 mb-2">Courses</h3>
                        <p class="text-3xl font-bold text-green-600">0</p>
                        <p class="text-sm text-gray-500">Active courses</p>
                    </div>
                    <div class="w-12 h-12 bg-green-100 rounded-lg flex items-center justify-center">
                        <i class="fas fa-book text-green-600 text-xl"></i>
                    </div>
                </div>
            </div>

            <!-- Revenue Card -->
            <div class="bg-white rounded-xl shadow-sm p-6 hover:shadow-md transition duration-200">
                <div class="flex items-center justify-between">
                    <div>
                        <h3 class="text-lg font-semibold text-gray-900 mb-2">Revenue</h3>
                        <p class="text-3xl font-bold text-yellow-600">$0</p>
                        <p class="text-sm text-gray-500">This month</p>
                    </div>
                    <div class="w-12 h-12 bg-yellow-100 rounded-lg flex items-center justify-center">
                        <i class="fas fa-dollar-sign text-yellow-600 text-xl"></i>
                    </div>
                </div>
            </div>
        </div>

        <!-- Quick Actions -->
        <div class="bg-white rounded-xl shadow-sm p-8">
            <h3 class="text-xl font-bold text-gray-900 mb-6">Quick Actions</h3>
            <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
                <button class="flex items-center justify-center p-4 border-2 border-dashed border-gray-300 rounded-lg hover:border-indigo-500 hover:bg-indigo-50 transition duration-200">
                    <div class="text-center">
                        <i class="fas fa-plus text-2xl text-gray-400 mb-2"></i>
                        <p class="text-sm font-medium text-gray-600">Add Student</p>
                    </div>
                </button>
                <button class="flex items-center justify-center p-4 border-2 border-dashed border-gray-300 rounded-lg hover:border-indigo-500 hover:bg-indigo-50 transition duration-200">
                    <div class="text-center">
                        <i class="fas fa-book-open text-2xl text-gray-400 mb-2"></i>
                        <p class="text-sm font-medium text-gray-600">Create Course</p>
                    </div>
                </button>
                <button class="flex items-center justify-center p-4 border-2 border-dashed border-gray-300 rounded-lg hover:border-indigo-500 hover:bg-indigo-50 transition duration-200">
                    <div class="text-center">
                        <i class="fas fa-file-invoice text-2xl text-gray-400 mb-2"></i>
                        <p class="text-sm font-medium text-gray-600">Generate Bill</p>
                    </div>
                </button>
                <button class="flex items-center justify-center p-4 border-2 border-dashed border-gray-300 rounded-lg hover:border-indigo-500 hover:bg-indigo-50 transition duration-200">
                    <div class="text-center">
                        <i class="fas fa-chart-bar text-2xl text-gray-400 mb-2"></i>
                        <p class="text-sm font-medium text-gray-600">View Reports</p>
                    </div>
                </button>
            </div>
        </div>
    </div>

    <!-- Footer -->
    <footer class="bg-white border-t mt-12">
        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-6">
            <div class="text-center text-gray-500 text-sm">
                <p>&copy; 2025 Pahana Edu. All rights reserved.</p>
            </div>
        </div>
    </footer>
</body>
</html>
<%-- Remove the page directive --%>
<div>
  <div>
    <!-- Sidebar Header -->
    <div>
      <div>
      </div>
      <h1>Pahana Edu</h1>
    </div>

    <!-- Sidebar Navigation -->
    <nav>
      <a href="${pageContext.request.contextPath}/auth/dashboard">
        Dashboard
      </a>
      <a href="${pageContext.request.contextPath}/books/ManageBooks">
        Books
      </a>
      <a href="${pageContext.request.contextPath}/categories/ManageCategories">
        Categories
      </a>
      <a href="${pageContext.request.contextPath}/customers/ManageCustomers">
        Customers
      </a>
      <a href="${pageContext.request.contextPath}/transactions">
        Transactions
      </a>
    </nav>

    <!-- Sidebar Footer -->
    <div>
      <a href="${pageContext.request.contextPath}/auth/logout">
        Logout
      </a>
    </div>
  </div>
</div>
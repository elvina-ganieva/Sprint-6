package com.example.mvc

import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "AuthServlet", value = ["/login"])
class AuthServlet : HttpServlet() {
    private val accessLogin = "12345"
    private val accessPassword = "12345"
    @Throws(ServletException::class, IOException::class)
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        servletContext.getRequestDispatcher("/login-form.html").forward(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        val userName = request.getParameter("username")
        val password = request.getParameter("password")
        if (userName == accessLogin && password == accessPassword) {
            response.sendRedirect("/app/add")
        } else {
            response.sendRedirect("/login")
        }
    }
}
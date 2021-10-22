package com.example.mvc

import java.time.Instant
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter(urlPatterns = ["/app/*", "/api/*"], servletNames = ["AuthServlet"])
class CookieFilter : Filter {
    override fun doFilter(req: ServletRequest?, resp: ServletResponse?, fc: FilterChain?) {
        val request = req as HttpServletRequest
        val response = resp as HttpServletResponse
        val cookies = request.cookies
        if (cookies != null) {
            for (cookie in cookies) {
                if (cookie.name == "auth" && cookie.value < Instant.now().toEpochMilli().toString()) {
                    response.setHeader("cookie", "allowed")
                } else {
                    response.sendRedirect("/login")
                }
            }
        }
        fc?.doFilter(req, resp)
    }
}
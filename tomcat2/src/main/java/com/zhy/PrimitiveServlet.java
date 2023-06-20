package com.zhy;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

public class PrimitiveServlet implements Servlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {

        System.out.println("from service");
//        Response response = (Response)servletResponse;
//        response.sendStaticResource();
        PrintWriter out = servletResponse.getWriter();
        String header = "HTTP/1.1 200 ok\r\n"+
                "Content-Type: text/html\r\n"+
                "\r\n";
        out.println(header);
        out.println("Hello. Roses are red.");
        out.print("Violets are blue.");
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}

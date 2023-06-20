package com.zhy;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

public class ServletProcessor1 {
    public void process(Request request,Response response){
        String uri = request.getUri();
        String servletName = "com.zhy." + uri.substring(uri.lastIndexOf("/") +1);
        URLClassLoader loader = null;
        URL[] urls = new URL[1];
        URLStreamHandler urlStreamHandler = null;
        File classPath = new File(Constants.WEB_ROOT);
        try {
            String repository = (new URL("file",null,classPath.getCanonicalPath()+File.separator)).toString();
            urls[0] = new URL(null,repository,urlStreamHandler);
            loader = new URLClassLoader(urls);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Class servletClass = null;
        try {
            servletClass = loader.loadClass(servletName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Servlet servlet = null;

        try {
            Servlet primitiveServlet = (Servlet) servletClass.newInstance();
            primitiveServlet.service((ServletRequest) request,(ServletResponse) response);

        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

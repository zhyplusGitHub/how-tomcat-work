package com.zhy;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer1 {
    private final static String SHUTDOWN_COMMAND ="/SHUTDOWN";
    private boolean shutdown = false;

    public static void main(String[] args) {
        HttpServer1 server = new HttpServer1();
        server.await();
    }
    public void await(){
        ServerSocket serverSocket = null;
        int port = 8080;
        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        while (!shutdown){
            Socket socket = null;
            InputStream input = null;
            OutputStream out = null;
            try {
                socket = serverSocket.accept();
                input = socket.getInputStream();
                out = socket.getOutputStream();
                Request request = new Request(input);
                request.parse();
                Response response = new Response(out);
                response.setRequest(request);
                if (request.getUri().startsWith("/servlet/")){
                    ServletProcessor1 servletProcessor = new ServletProcessor1();
                    servletProcessor.process(request,response);
                }else{
                    StaticResourceProcessor processor = new StaticResourceProcessor();
                    processor.process(request,response);
                }
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

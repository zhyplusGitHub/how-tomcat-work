package com.zhy;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.*;
import java.util.Locale;

public class Response implements ServletResponse {
    private static final int BUFFER_SIZE = 1024;
    private OutputStream out;
    private Request request;

    PrintWriter writer;
    public Response(OutputStream out) {
        this.out = out;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void sendStaticResource(){
        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        try {
            File file = new File(Constants.WEB_ROOT, request.getUri());
            if (file.exists()){
                fis = new FileInputStream(file);
                int ch = fis.read(bytes,0,BUFFER_SIZE);
                String header = "HTTP/1.1 200 ok\r\n"+
                        "Content-Type: text/html\r\n"+
                        "\r\n";
                out.write(header.getBytes());
                if (ch != -1){
                    out.write(bytes,0,ch);
                }
            }else{
                String errMessage = "HTTP/1.1 404 File Not Found\r\n"+
                        "Content-Type: text/html\r\n"+
                        "\r\n"+
                        "<h1>File Not Found.......</h1>";
                out.write(errMessage.getBytes());
            }
        }catch (Exception e){

        }finally {
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        writer = new PrintWriter(out, true);
        return writer;
    }

    @Override
    public void setCharacterEncoding(String s) {

    }

    @Override
    public void setContentLength(int i) {

    }

    @Override
    public void setContentLengthLong(long l) {

    }

    @Override
    public void setContentType(String s) {

    }

    @Override
    public void setBufferSize(int i) {

    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {

    }

    @Override
    public void resetBuffer() {

    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setLocale(Locale locale) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }
}

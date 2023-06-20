import java.io.*;

public class Response {
    private static final int BUFFER_SIZE = 1024;
    private OutputStream out;
    private Request request;
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
            File file = new File(HttpServer.WEB_ROOT, request.getUri());
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
}

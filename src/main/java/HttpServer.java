import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    public final static String WEB_ROOT = System.getProperty("user.dir")+ File.separator+"webroot";
    private final static String SHUTDOWN_COMMAND ="/SHUTDOWN";
    private boolean shutdown = false;

    public static void main(String[] args) {
        HttpServer server = new HttpServer();
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
                response.sendStaticResource();
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

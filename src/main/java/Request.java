import java.io.IOException;
import java.io.InputStream;

public class Request {
    private InputStream input;
    private String uri;

    public Request(InputStream input) {
        this.input = input;
    }

    public String getUri() {
        return uri;
    }

    public void parse(){
        byte[] buffer = new byte[2048];
        StringBuffer request = new StringBuffer(2048);
        int i;
        try {
            i = input.read(buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (int j = 0; j < i; j++) {
            request.append((char)buffer[j]);
        }
        uri = paseUri(request.toString());
    }

    public String paseUri(String requestString) {
        int index1,index2;
        index1 = requestString.indexOf(" ");
        if (index1 != 0){
            index2 = requestString.indexOf(" ",index1+1);
            if(index2 > index1){
                return requestString.substring(index1+1,index2);
            }
        }
        return null;
    }

    public static void main(String[] args) {
    }
}

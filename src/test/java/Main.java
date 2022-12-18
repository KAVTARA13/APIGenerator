import java.io.*;
import java.net.*;

public class Main {
    protected void start() {
        ServerSocket s;
        String gets = "";

        System.out.println("Start on port 8888");
        try {
            // create the main server socket
            s = new ServerSocket(8888);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return;
        }

        System.out.println("Waiting for connection");
        for (;;) {
            try {
                Socket socket = s.accept();
                //socket is an instance of Socket
                InputStream is = socket.getInputStream();
                InputStreamReader isReader = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isReader);
                System.out.println("+++++++++++++++++++++++++++++");
                System.out.println("SERVER SOCKET TESTS:");
                System.out.println("getChannel: " + s.getChannel());
                System.out.println("getInetAddress: " + s.getInetAddress());
                System.out.println("getLocalPort: " + s.getLocalPort());
                System.out.println("getLocalSocketAddress: " + s.getLocalSocketAddress());

                System.out.println();

                System.out.println("CLIENT SOCKET TESTS:");
                System.out.println("getChannel: " + socket.getChannel());
                System.out.println("getLocalAddress: " + socket.getLocalAddress());
                System.out.println("getLocalPort: " + socket.getLocalPort());
                System.out.println("getLocalSocketAddress: " + socket.getLocalSocketAddress());
                System.out.println("getRemoteSocketAddress: " + socket.getRemoteSocketAddress());
                System.out.println("getInetAddress: " + socket.getInetAddress());
                System.out.println("getInputStream: " + socket.getInputStream());
                System.out.println("getOutputStream: " + socket.getOutputStream());

                System.out.println("+++++++++++++++++++++++++++++");
//code to read and print headers
                String headerLine = null;
                while((headerLine = br.readLine()).length() != 0){
                    System.out.println(headerLine);
                }

//code to read the post payload data
                StringBuilder payload = new StringBuilder();
                while(br.ready()){
                    payload.append((char) br.read());
                }
                System.out.println("Payload data is: "+payload.toString());


            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        }
    }

    public static void main(String args[]) {
        Main ws = new Main();
        ws.start();
    }
}
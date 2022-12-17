import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main( String[] args ) throws IOException {
        final int port = 80;
        final ServerSocket serverSocket = new ServerSocket(port);

        System.out.println(String.format("Start listening on port %d", port));
        while (true) {
            final Socket client = serverSocket.accept();
            final InputStream inputStream = client.getInputStream();
            final OutputStream outputStream = client.getOutputStream();

            new Thread(() -> {
                try{
                    final ByteArrayOutputStream request = new ByteArrayOutputStream();
                    transfer(inputStream,request);
                    System.out.println(request.toString());
                }catch (IOException ex){
                    ex.printStackTrace();
                }
            }).start();
        }
    }
    public static void transfer(InputStream inputStream,OutputStream outputStream)throws IOException {
        final byte[] buff = new byte[2048];
        int read;
        while ((read = inputStream.read(buff, 0, Math.min(inputStream.available(), 2048))) > 0) {
            outputStream.write(buff, 0, read);
        }
    }
}
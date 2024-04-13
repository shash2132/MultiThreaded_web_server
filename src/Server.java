import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public void run() throws IOException {
        int port=8020;
        ServerSocket socket=new ServerSocket(port);
        socket.setSoTimeout(30000);

        while(true){
            System.out.println("Serve is Listening");
            Socket acceptedConnection=socket.accept();
            System.out.println("Connection Accepted from :"+acceptedConnection.getInetAddress());
            PrintWriter toClient=new PrintWriter(acceptedConnection.getOutputStream());
            BufferedReader fromClient=new BufferedReader(new InputStreamReader(acceptedConnection.getInputStream()));

            //sending data to client
            toClient.println("Hello From the Server");
            toClient.close();
            fromClient.close();
            acceptedConnection.close();

            //remember : printWriter to send the data wether from socket or server
            // while BufferReader to get data wether on client or server side


        }

    }
    public static void main(String[] args) {
        Server server=new Server();
        try{
            server.run();
        }catch (IOException ex){
            ex.printStackTrace();
        }

    }
}

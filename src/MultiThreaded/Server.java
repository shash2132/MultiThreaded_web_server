package MultiThreaded;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {
    public Consumer<Socket> getConsumer(){
        return new Consumer<Socket>() {
            @Override
            public void accept(Socket socket) {
                try {
                    PrintWriter toClient=new PrintWriter(socket.getOutputStream());
                    toClient.print("Hello from the Server ");
                    toClient.close();
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    public static void main(String[] args) throws IOException {
        Server server=new Server();
        int port=8020;
        ServerSocket serverSocket=new ServerSocket(port);
        serverSocket.setSoTimeout(20000);
        System.out.println("Server Listening at : "+port);
        int c=1;
        while(true){
            Socket acceptedConnection=serverSocket.accept();
            //now handle this client through the help of thread
            System.out.println("Accepted Connection of Client "+c);
            c++;
            Thread thread=new Thread(()->server.getConsumer().accept(acceptedConnection));
            thread.start();
        }

    }
}

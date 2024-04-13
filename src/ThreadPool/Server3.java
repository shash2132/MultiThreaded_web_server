package ThreadPool;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server3 {
    private final ExecutorService threadPool;

    public Server3(int poolSize) {
        this.threadPool = Executors.newFixedThreadPool(poolSize);
    }

    public void handleClients(Socket clientSocket){
        try(PrintWriter toSocket=new PrintWriter(clientSocket.getOutputStream())){
            //now i have send data to client so use printWriter here
            toSocket.println("Hello from the server :"+clientSocket.getInetAddress());

        }catch(Exception exception){
            exception.printStackTrace();
        }

    }

    public static void main(String[] args) {
        int port=8020;
        Server3 server3=new Server3(10);
        try{
            ServerSocket server=new ServerSocket(port);
            server.setSoTimeout(200000);
            while(true){
                Socket acceptedConnection=server.accept();
                System.out.println("Connection accepted at: "+acceptedConnection);

                // now instead of creating a new thread here we would use ThreadPool Concept
                server3.threadPool.execute(()-> server3.handleClients(acceptedConnection));
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            server3.threadPool.shutdown();
        }
    }
}

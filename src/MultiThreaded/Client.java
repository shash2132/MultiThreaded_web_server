package MultiThreaded;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public Runnable getRunnable(){
        return new Runnable() {
            @Override
            public void run() {
                int port=8020;
                try{
                    InetAddress address=InetAddress.getByName("localhost");
                    Socket socket=new Socket(address,port);
                    PrintWriter toSocket=new PrintWriter(socket.getOutputStream());
                    BufferedReader fromSocket=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    toSocket.println("Hello from client !!");
                    String line=fromSocket.readLine();
                    System.out.println("Response from Server: "+line);
                    toSocket.close();
                    socket.close();
                    fromSocket.close();
                }catch(Exception ex){
                    ex.printStackTrace();
                }

            }
        };
    }
    public static void main(String[] args) {
        Client client=new Client();
        for(int i=0;i<10;i++){
            try {
                Thread thread=new Thread(client.getRunnable());
                thread.start();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}

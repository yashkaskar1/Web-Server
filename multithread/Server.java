import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;
import java.io.IOException;
import java.io.PrintWriter;



public class Server{
    public Consumer<Socket> getConsumer() {
        return (ClientSocket) -> {
            try {
                PrintWriter toClient = new PrintWriter(ClientSocket.getOutputStream());
                toClient.println("hello from server");
                toClient.close();
                ClientSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        };
    }
    public static void main(String[] args){
        int port=8010;
        Server server=new Server();
        try{
            ServerSocket serverSocket= new ServerSocket(port);
            serverSocket.setSoTimeout(10000);
            System.out.println("Server is Listening port"+port);
            while(true){
                Socket acceptedSocket = serverSocket.accept();
                Thread thread =new Thread(()->server.getConsumer().accept(acceptedSocket));
                thread.start();
            }
            }catch(IOException ex){
               ex.printStackTrace();
            }
        }
    }


import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    public static void main(String args[]) throws IOException
    {
        try (ServerSocket server = new ServerSocket(2048)) {
            while (true) {
                Thread t = new Thread(new Task(server.accept()));
                t.start();
            }
        }

    }
}

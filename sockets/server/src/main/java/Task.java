
import java.io.*;
import java.net.Socket;

public class Task implements Runnable {

    private final Socket socket;

    public Task(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        String name;
        long size;

        try(DataInputStream dataInputStream = new DataInputStream(socket.getInputStream())){
            try{
                name = dataInputStream.readUTF();
                size = dataInputStream.readLong();
            } catch(IOException exception){
                System.out.println("fail");
                socket.close();
                return;
            }
            String nazwa =  name +".jpg";

            File file = new File(".", name);

            FileOutputStream outputStream = new FileOutputStream(file);
            try {

                byte[] buffer = new byte[4096];
                int downloaded = 0;
                int readSize = 0;

                while(downloaded < size){
                    readSize = dataInputStream.read(buffer);
                    if(readSize != -1) downloaded += readSize;
                    else
                        throw new IOException("fail");

                    outputStream.write(buffer, 0, readSize);
                }
                System.out.println("success");
                outputStream.close();
            } catch(IOException exception){
                System.out.println("fail");

            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}

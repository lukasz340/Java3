package sample;

import java.io.*;
import java.net.Socket;
import javafx.concurrent.Task;

public class SendFileTask extends Task<Void> {
    private final File file;

    public SendFileTask(File file) {
        this.file = file;
    }

    @Override protected Void call() throws Exception {
        updateMessage("initialization");
        updateProgress(0L, 100L);

        try {
            Socket clientSocket = new Socket("localhost", 2048);
            if (!clientSocket.isConnected()) {
                updateMessage("fail");
                return null;
            }

            DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());

            try {
                outputStream.writeUTF(file.getName());
                outputStream.writeLong(file.length());
                long full = file.length();
                int downloaded = 0;
                updateMessage("sending");
                updateProgress(0L, full);
                FileInputStream inputStream = new FileInputStream(file);
                byte[] buffer = new byte[4096];

                while(downloaded != full) {
                    int read = inputStream.read(buffer);
                    outputStream.write(buffer, 0, read);
                    downloaded += read;
                    updateProgress(downloaded, 100L);
                }
            } catch (IOException ex ) {
                System.err.println(ex);

            }
        } catch (IOException exception) {
            System.err.println(exception);
        }

        updateMessage("finishing");
        updateProgress(100L, 100L);
        return null;
    }
}
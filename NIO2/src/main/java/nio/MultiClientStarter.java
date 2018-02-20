package nio;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiClientStarter {
    public static void main(String[] args) throws IOException {
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Client client = new Client();
                        client.run();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        executor.shutdown();
    }

}

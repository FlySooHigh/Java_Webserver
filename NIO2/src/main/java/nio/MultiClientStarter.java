package nio;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiClientStarter {

    private static final int THREAD_NUMBER = 5;

    public static void main(String[] args) throws IOException {
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < THREAD_NUMBER; i++){
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

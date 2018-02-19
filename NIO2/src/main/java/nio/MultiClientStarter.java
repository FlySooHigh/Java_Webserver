package nio;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiClientStarter implements Runnable{
    public static void main(String[] args) throws IOException {
//        for (int i = 0; i < 5; i++){
//            String[] arguments = {};
//            Client.main(arguments);
//        }
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++){
            executor.execute(new MultiClientStarter());
        }
        executor.shutdown();
    }

    @Override
    public void run() {
        String[] arguments = {};
        try {
//            Client client = new Client();
//            client.main(arguments);
            Client.main(arguments);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

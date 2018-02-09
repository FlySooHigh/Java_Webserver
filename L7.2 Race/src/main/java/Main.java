import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author v.chibrikov
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class Main {
    private static final int HUNDRED_MILLION = 100_000_000;
    private static final int THREAD_NUMBER = 20;
    private static final int VECTOR_LENGTH = 60_000;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_NUMBER);

        long generationStartTime = (new Date()).getTime();

        List<Double> vector1 = generateRandomVector(VECTOR_LENGTH);
        List<Double> vector2 = generateRandomVector(VECTOR_LENGTH);

        long generationFinishTime = (new Date()).getTime();
        System.out.println("Generation time : " + (generationFinishTime - generationStartTime)/1000.0 + " sec");

        VectorMultiplication vectorMultiplication = new VectorMultiplication(vector1, vector2);
        ArrayList<Future<Double>> futureContainer = new ArrayList<>();

        long startTime = (new Date()).getTime();

        int partitionSize = vector1.size()/THREAD_NUMBER;

        for (int i = 0; i < THREAD_NUMBER; i++) {
            if (i != THREAD_NUMBER - 1){                // если не последняя партиция
                RaceExample1 callable = new RaceExample1(vectorMultiplication, partitionSize * i, partitionSize * (i + 1));
                Future<Double> future = executorService.submit(callable);
                futureContainer.add(future);
            }
            else {                                      // если последняя, то вычитаем из end единицу
                RaceExample1 callable = new RaceExample1(vectorMultiplication, partitionSize * i, partitionSize * (i + 1) - 1);
                Future<Double> future = executorService.submit(callable);
                futureContainer.add(future);
            }
        }

        Double futureResult;
        Double totalFutureResult = 0.0;

        for(int i = 0; i < THREAD_NUMBER; i++){
            futureResult = futureContainer.get(i).get();
            totalFutureResult += futureResult;
            System.out.println("Future" + i + " : " + futureResult);
            if (i == THREAD_NUMBER - 1){
                System.out.println("Total: " + totalFutureResult);
            }
        }


        long finishTime = (new Date()).getTime();
        System.out.println("Time spent: " + (finishTime - startTime)/1000.0 + " sec");
        executorService.shutdown();
    }

    private static List<Double> generateRandomVector(int vectorLength) {
        List<Double> vector = new LinkedList<>();
        for(int i = 0; i < vectorLength; i++){
            vector.add((Math.random()));
        }
        return vector;
    }

    private static class RaceExample implements Callable<Integer> {
        private final CallCounter callCounter;

        private RaceExample(CallCounter counter) {
            this.callCounter = counter;
        }

        @Override
        public Integer call() throws Exception {
                synchronized (callCounter) {
                    while (callCounter.getCount() < HUNDRED_MILLION) {
                        callCounter.increment();
                    }
                }
            return callCounter.getCount();
        }
    }

    private static class RaceExample1 implements Callable<Double> {
        private final VectorMultiplication vectorMultiplication;
        private int start;
        private int end;

        private RaceExample1(VectorMultiplication vectorMultiplication, int start, int end) {
            this.vectorMultiplication = vectorMultiplication;
            this.start = start;
            this.end = end;
        }

        @Override
        public Double call() throws Exception {
//            synchronized (vectorMultiplication) {
                return vectorMultiplication.multiply(start, end);
//            }
        }
    }
}

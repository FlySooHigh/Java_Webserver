import java.util.List;

/**
 * Created by sbt-kadatskiy-av on 07.02.2018.
 */
public class VectorMultiplication {
    List<Double> array1;
    List<Double> array2;

    public VectorMultiplication(List<Double> array1, List<Double> array2) {
        this.array1 = array1;
        this.array2 = array2;
    }

    public double multiply(int start, int end){
        double result = 0;
        for (int i = start; i < end; i++){
            result =+ array1.get(i) * array2.get(i);
        }
        return result;
    }

}

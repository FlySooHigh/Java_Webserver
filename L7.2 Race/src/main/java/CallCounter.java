/**
 * @author v.chibrikov
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class CallCounter {
    private int count = 0;

    public   void increment(){
//        synchronized(this){
//            count++;
        count = count + 1;
//        }
    }

    public  int  getCount() {
        return count;
    }
}

package cz.kalas.samples.dogstation;

public class AnotherCleverUtils {

    public final static long DELAY_CONSTANT = 2000; // milisecs

    public static void delay(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

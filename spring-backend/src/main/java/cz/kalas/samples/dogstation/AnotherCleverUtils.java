package cz.kalas.samples.dogstation;

public class AnotherCleverUtils {

    public static void delay(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

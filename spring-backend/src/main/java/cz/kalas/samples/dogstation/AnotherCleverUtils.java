package cz.kalas.samples.dogstation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AnotherCleverUtils {


    public final static Boolean DELAY_ENABLED = true;
    public final static long DELAY_CONSTANT = 4000; // milisecs

    public static void delay(long time) {
        delay(null, time);
    }

    public static void delay(String comment, long time) {
        log.debug("[DELAY] --- " + ((comment != null) ? comment : ""));
        if (DELAY_ENABLED) {
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void delay(String comment) {
        delay(comment, DELAY_CONSTANT);
    }

}

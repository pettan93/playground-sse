package cz.kalas.samples.dogstation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AnotherCleverUtils {


    public final static Boolean DELAY_ENABLED = false;
    public final static long DELAY_CONSTANT = 5000; // milisecs

    public static void delay(long time) {
        delay(null, time);
    }

    public static void delay(String comment, long time) {

        if (DELAY_ENABLED) {
            log.debug("[DELAY] --- " + ((comment != null) ? comment : ""));
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else {
            log.debug("[NO-DELAY] --- " + ((comment != null) ? comment : ""));
        }
    }

    public static void delay(String comment) {
        delay(comment, DELAY_CONSTANT);
    }

}

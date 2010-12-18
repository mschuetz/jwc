package de.idlecrew.util;

import java.util.concurrent.TimeUnit;

public class Profile {
    /**
     * @return time taken in nanoseconds (10^-9)
     */
    public static long profile(Runnable r) {
        long t1 = System.nanoTime();
        r.run();
        return System.nanoTime() - t1;
    }
    
    public static void report(String name, TimeUnit timeUnit, int iterations, Runnable r) {
        for (int i=0; i<iterations; i++) {
                long t = profile(r);
                System.out.println(i + ": " + name + " took " + timeUnit.convert(t, TimeUnit.NANOSECONDS) +" "+ timeUnit.name());
        }
    }
}

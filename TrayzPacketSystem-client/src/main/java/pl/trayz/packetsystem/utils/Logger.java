package pl.trayz.packetsystem.utils;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Trayz
 * @Created 28.01.2022
 **/

@Getter
public class Logger {

    @Setter
    protected static boolean logError = true;
    @Setter
    protected static boolean logSuccess = true;

    public static void logError(String message) {
        if(!logError)
            return;
        System.err.println("[ERROR] "+message);
    }

    public static void logSuccess(String message) {
        if(!logSuccess)
            return;
        System.out.println("[SUCCESS] "+message);
    }

}

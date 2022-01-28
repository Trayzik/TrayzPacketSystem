package pl.trayz.packetsystem.utils;

import lombok.Getter;

/**
 * @Author: Fabian 'Trayz'
 * @Created 28.01.2022
 * @Class: LoggerUtil
 **/

@Getter
public class Logger {

    public static void logError(String message) {
        System.err.println("[ERROR] "+message);
    }

    public static void logSuccess(String message) {
        System.out.println("[SUCCESS] "+message);
    }

    public static void logWarning(String message) {
        System.err.println("[WARNING] "+message);
    }
}

package cash;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("query: find payment by id");
        try {
            factorial(9);
            factorial(-3);
        } catch (IllegalArgumentException e) {
            // вывод сообщения уровня ERROR
            logger.error("my error is great! negative argument: ", e);
        }
    }
    public static int factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException(
                    "argument " + n +" less than zero");
        }
        // вывод сообщения уровня DEBUG
        logger.debug("Argument n is " + n);
        int result = 1;
        for (int i = n; i >= 1; i--) {
            result *= i;
        }
        // вывод сообщения уровня INFO
        logger.info("Result is " + result);
        return result;
    }

    }







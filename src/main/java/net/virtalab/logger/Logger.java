package net.virtalab.logger;

import java.io.PrintStream;

/**
 * Logger
 *
 */
public class Logger {
    /**
     * Log prefix. Concept came from android.util.Log class
     */
    private String prefix;
    /**
     * Default log level, used if logger wasn't initialized with some log level
     */
    private static final LogLevel defaultLogLevel = LogLevel.OFF;
    /**
     * Holder for current log level
     */
    private static LogLevel currentLogLevel = LogLevel.INFO;

    /**
     * Main constructor
     *
     * @param prefix log prefix
     */
    private Logger(String prefix){
        this.prefix = prefix;
    }

    /**
     * Overrides default log level
     *
     * @param logLevel desirable log level
     */
    public static void init(LogLevel logLevel){
        currentLogLevel = logLevel;
    }

    /**
     * Resets current log level to default.
     * Made primary for unit tests
     */
    public static void resetLogLevel(){
        currentLogLevel = defaultLogLevel;
    }
    /**
     * Provides Logger object with given prefix
     *
     * @param prefix desirable prefix, if null of empty, it will create Logger without prefix
     * @return newly created Logger instance
     */
    public static Logger getLogger(String prefix){
        return new Logger(prefix);
    }

    /**
     * Shows current log level
     *
     * @return current log level
     */
    public static LogLevel getCurrentLogLevel(){
        return currentLogLevel;
    }

    /**
     * Provides int representation of current log level
     * @return int representation of current log level
     */
    public static int getCurrentLogLevelAsInt(){
        return currentLogLevel.asInt();
    }

    /**
     * Prints Trace Logs
     *
     * @param message trace message
     */
    public void trace(String message){
        if(currentLogLevel.priority >= LogLevel.TRACE.priority){
            publish(LogLevel.TRACE,message);
        }
    }

    /**
     * Prints debug message
     *
     * @param message log message
     */
    public void debug(String message){
        if(currentLogLevel.priority >= LogLevel.DEBUG.priority){
            publish(LogLevel.DEBUG,message);
        }
    }

    /**
     * Prints info message
     *
     * @param message log message
     */
    public void info(String message){
        if(currentLogLevel.priority >= LogLevel.INFO.priority){
            publish(LogLevel.INFO,message);
        }
    }

    /**
     * Prints warn message
     *
     * @param message log message
     */
    public void warn(String message){
        if(currentLogLevel.priority >= LogLevel.WARN.priority){
            publish(LogLevel.WARN,message);
        }

    }

    /**
     * Prints error message
     *
     * @param message log message
     */
    public void error(String message){
        if(currentLogLevel.priority >= LogLevel.ERROR.priority){
            publish(LogLevel.ERROR,message);
        }
    }

    /**
     * Does all dirty job
     *
     * @param level log level
     * @param message log message
     */
    private void publish(LogLevel level, String message){
        PrintStream stream;

        switch (level){
            case WARN:
            case ERROR:
                stream = System.err;
                break;
            case TRACE:
            case DEBUG:
            case INFO:
            default:
                stream = System.out;
                break;
        }
        String readyToGoMessage = createMessage(level,message);

        stream.println(readyToGoMessage);
    }

    /**
     * Builds full string which will be published
     *
     * @param lvl log level
     * @param message log message
     * @return ready-to-print line
     */
    private String createMessage(LogLevel lvl,String message){
        String color;
        String label;
        switch (lvl){
            case TRACE:
                color = Color.CYAN;
                label = "TRACE";
                break;
            case DEBUG:
                color = Color.GREEN;
                label = "DEBUG";
                break;
            case INFO:
                color = Color.WHITE;
                label = "INFO";
                break;
            case WARN:
                color = Color.YELLOW;
                label = "WARN";
                break;
            case ERROR:
                color = Color.RED;
                label = "ERROR";
                break;
            default:
                color = Color.BLACK;
                label = "VOID";
                break;
        }

        StringBuilder line = new StringBuilder();
        if(prefix!=null && ! prefix.isEmpty()){
            line.append("[").append(color).append(this.prefix).append(Color.RESET).append("]");
            line.append(" ");
        }
        line.append("(").append(color).append(label).append(Color.RESET).append(")");
        line.append(" ");
        line.append(message);

        return line.toString();
    }
}

package net.virtalab.logger;

import java.io.PrintStream;

/**
 * logger
 */@SuppressWarnings("unused")
public class Logger {

    private static Logger self = null;

    private String prefix;
    private static Level currentLogLevel = Level.INFO;

    private Logger(){}
    private Logger(String prefix){
        this.prefix = prefix;
    }

    public static void init(Level logLevel){
        currentLogLevel = logLevel;
    }

    public static Logger getLogger(String prefix){
        return new Logger(prefix);
    }

    public void debug(String message){
        if(currentLogLevel.priority >= Level.DEBUG.priority){
            publish(Level.DEBUG,message);
        }
    }

    public void info(String message){
        if(currentLogLevel.priority >= Level.INFO.priority){
            publish(Level.INFO,message);
        }
    }

    public void warn(String message){
        if(currentLogLevel.priority >= Level.WARN.priority){
            publish(Level.WARN,message);
        }

    }

    public void error(String message){
        if(currentLogLevel.priority >= Level.ERROR.priority){
            publish(Level.ERROR,message);
        }
    }

    private void publish(Level level, String message){
        PrintStream stream;

        switch (level){
            case WARN:
            case ERROR:
                stream = System.err;
                break;
            case DEBUG:
            case INFO:
            default:
                stream = System.out;
                break;
        }
        String readyToGoMessage = createMessage(level,message);

        stream.println(readyToGoMessage);
    }

    private String createMessage(Level lvl,String message){
        String color;
        String label;
        switch (lvl){
            case DEBUG:
                color = Color.GREEN;
                label = "DEBUG";
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
        line.append("[").append(color).append(this.prefix).append(Color.RESET).append("]");
        line.append(" ");
        line.append("(").append(color).append(label).append(Color.RESET).append(")");
        line.append(" ");
        line.append(message);

        return line.toString();
    }

    public enum Level{
        DEBUG(4),
        INFO(3),
        WARN(2),
        ERROR(1);

        private int priority;

        Level(int priority){
            this.priority = priority;
        }
        public int asInt(){
            return this.priority;
        }
    }

}

package net.virtalab.logger;


import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Brand New Logger inspired by android.util.Log
 * Supports following log levels: trace, debug, info, warn, error, off and wtf
 * <br>
 * Methods for general use are: Log.t(), Log.d(), Log.i(), Log.w(), Log.e(), Log.wtf()
 * or their long named aliases Log.trace(), Log.debug(), Log.info(), Log.warn(), Log.error()
 *
 * Default log output format: level timestamp class prefix text
 *
 * level is single letter... can be disabled by Log.noLetter()
 * timestamp is timedate (format ...) can be disabled by Log.noTime()
 * class is name of caller class can be disabled by Log.noClassName()
 * prefix is text printed before message (aka Tag)
 *
 * @author Alexander Muravya
 * @since 1.5
 *
 */
public class Log {

    //log level
    private static final LogLevel defaultLogLevel = LogLevel.OFF;
    private static LogLevel currentLogLevel;

    //message format settings
    private static boolean isLetterEnabled = true;
    private static boolean isTimeEnabled = true;
    private static boolean isClassNameEnabled = true;

    //matrix 1,2,3
    private static Map<LogLevel,String> colorMatrix;
    private static Map<LogLevel, String> letterMatrix;
    private static Map<LogLevel, PrintStream> streamMatrix;

    static {
        colorMatrix = new HashMap<LogLevel, String>();
        //defaults
        colorMatrix.put(LogLevel.ERROR, Color.RED);
        colorMatrix.put(LogLevel.WARN, Color.YELLOW);
        colorMatrix.put(LogLevel.INFO, Color.GREEN);
        colorMatrix.put(LogLevel.DEBUG, Color.BLUE);
        colorMatrix.put(LogLevel.TRACE, Color.CYAN);

        letterMatrix = new HashMap<LogLevel, String>();
        letterMatrix.put(LogLevel.ERROR, "E");
        letterMatrix.put(LogLevel.WARN, "W");
        letterMatrix.put(LogLevel.INFO, "I");
        letterMatrix.put(LogLevel.DEBUG, "D");
        letterMatrix.put(LogLevel.TRACE, "T");

        streamMatrix = new HashMap<LogLevel, PrintStream>();
        streamMatrix.put(LogLevel.ERROR, System.err);
        streamMatrix.put(LogLevel.WARN, System.err);
        streamMatrix.put(LogLevel.INFO, System.out);
        streamMatrix.put(LogLevel.DEBUG, System.out);
        streamMatrix.put(LogLevel.TRACE, System.out);
    }
    /**
     * Makes initial configuration of logger
     *
     * @param level log level logger initialized with
     */
    public static void init(LogLevel level){
        if(level!=null){
            Log.currentLogLevel = level;
        } else {
            Log.currentLogLevel = defaultLogLevel;
        }
    }

    /**
     * Disables log level letter at very beginning
     */
    public static void noLetter(){
        Log.isLetterEnabled = false;
    }

    /**
     * Disables timestamp in log message
     */
    public static void noTime(){
        Log.isTimeEnabled = false;
    }

    /**
     * Disables class name in log message
     */
    public static void noClassName(){
        Log.isClassNameEnabled = false;
    }

    /**
     * Change log message color for concrete log level
     * @param level log level
     * @param color one of possible ANSI-Colors
     */
    public static void changeMessageColor(LogLevel level, String color){
        if(level==null || color==null){
            return;
        }
        //TODO check color
        colorMatrix.put(level,color);
    }

    /**
     * Change output stream for concrete log level
     *
     * @param level log level
     * @param stream valid Print Stream
     */
    public static void changeStreamForLevel(LogLevel level, PrintStream stream){
        if(level==null || stream==null){ return; }
        streamMatrix.put(level, stream);
    }
    /**
     * Updates (sets) new log level
     *
     * @param logLevel desirable log level
     */
    public static void updateCurrentLogLevel(LogLevel logLevel) {
        Log.currentLogLevel = logLevel;
    }

    /**
     * Provides log level
     *
     * @return current value of log level
     */
    public static LogLevel getCurrentLogLevel() {
        return currentLogLevel;
    }

    /**
     * Provides int representation of current log level
     *
     * @return int value of current log level
     */
    public static int getCurrentLogLevelAsInt() {
        return currentLogLevel.asInt();
    }

    public static void trace(String tag, String message){}
    public static void trace(String tag, String message, Throwable t){}
    public static void trace(String tag, Throwable t){}

    public static void trace(String message){}
    public static void trace(Throwable t, String message){}

    public static void t(String tag, String message){
        trace(tag, message);
    }
    public static void t(String tag, String message, Throwable t){
        trace(tag, message, t);
    }
    public static void t(String tag, Throwable t){
        trace(tag, t);
    }

    public static void t(String message){ trace(message);}
    public static void t(Throwable t, String message){ trace(t,message);}

    public static void debug(String tag, String message){}
    public static void debug(String tag, String message, Throwable t){}
    public static void debug(String tag, Throwable t){}

    public static void debug(String message){}
    public static void debug(Throwable t, String message){}

    public static void d(String tag, String message){
        debug(tag, message);
    }
    public static void d(String tag, String message, Throwable t){
        debug(tag, message, t);
    }
    public static void d(String tag, Throwable t){
        debug(tag, t);
    }

    public static void d(String message){ debug(message);}
    public static void d(Throwable t, String message){ debug(t,message);}

    public static void info(String tag, String message){}
    public static void info(String tag, String message, Throwable t){}
    public static void info(String tag, Throwable t){}

    public static void info(String message){}
    public static void info(Throwable t, String message){}

    public static void i(String tag, String message){
        info(tag, message);
    }
    public static void i(String tag, String message, Throwable t){
        info(tag, message, t);
    }
    public static void i(String tag, Throwable t){
        info(tag, t);
    }

    public static void i(String message){ info(message);}
    public static void i(Throwable t, String message){ info(t, message);}

    public static void warn(String tag, String message){}
    public static void warn(String tag, String message, Throwable t){}
    public static void warn(String tag, Throwable t){}

    public static void warn(String message){}
    public static void warn(Throwable t, String message){}

    public static void w(String tag, String message){
        warn(tag, message);
    }
    public static void w(String tag, String message, Throwable t){
        warn(tag, message, t);
    }
    public static void w(String tag, Throwable t){
        warn(tag, t);
    }

    public static void w(String message){ warn(message);}
    public static void w(Throwable t, String message){ warn(t, message);}

    public static void error(String tag, String message){}
    public static void error(String tag, String message, Throwable t){}
    public static void error(String tag, Throwable t){}

    public static void error(String message){}
    public static void error(Throwable t, String message){}

    public static void err(String tag, String message){
        error(tag, message);
    }
    public static void err(String tag, String message, Throwable t){
        error(tag, message, t);
    }
    public static void err(String tag, Throwable t){
        error(tag, t);
    }

    public static void err(String message){ error(message);}
    public static void err(Throwable t, String message){ error(t, message);}

    public static void e(String tag, String message){
        error(tag, message);
    }
    public static void e(String tag, String message, Throwable t){
        error(tag, message, t);
    }
    public static void e(String tag, Throwable t){
        error(tag, t);
    }

    public static void e(String message){ error(message);}
    public static void e(Throwable t, String message){ error(t, message);}

    public static void wtf(String tag, String message){}
    public static void wtf(String tag, String message, Throwable t){}
    public static void wtf(String tag, Throwable t){}

    public static void wtf(String message){}
    public static void wtf(Throwable t, String message){}

    /**
     * Prints message
     *
     * @param level log level
     * @param message ready-to-print message
     */
    private static void publish(LogLevel level, String message){
        if(level==null || message==null){return; }
        if(streamMatrix.containsKey(level)){
            PrintStream stream = streamMatrix.get(level);
            stream.println(message);
        }
    }

    private static String makeString(LogObject logObject){
        StringBuilder sb = new StringBuilder();
        sb.append(logObject.color);
        if(isLetterEnabled){
            sb.append(logObject.letter).append(" ");
        }
        if(isTimeEnabled){
            String ts = "";
            sb.append(ts).append(" ");
        }
        if(isClassNameEnabled){
            String clsName = getCallerClassName();
            if(clsName!=null){
                sb.append(clsName).append(" ");
            }
        }
        if(logObject.tag!=null){
            if(! logObject.tag.isEmpty()){
                sb.append(logObject.tag).append(" ");
            }
        }
        if(logObject.message!=null){
            if(! logObject.message.isEmpty()){
                sb.append(logObject.message);
            }
        }
        //TODO exception logging

        sb.append(Color.RESET);
        return sb.toString();
    }

    public static String getCallerClassName() {
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        for (int i = 1; i < stElements.length; i++) {
            StackTraceElement ste = stElements[i];
            if (!ste.getClassName().equals(Log.class.getName()) && ste.getClassName().indexOf("java.lang.Thread") != 0) {
                return ste.getClassName();
            }
        }
        return null;
    }

    /**
     * Holds values needed to build log string
     */
    private static class LogObject{
        public LogLevel level;
        public String color;
        public String letter;

        public String tag;
        public String message;
        public Throwable th;


        public LogObject(LogLevel level){
            this.level = level;
            if(colorMatrix.containsKey(level)){
                this.color = colorMatrix.get(level);
            }
            if(letterMatrix.containsKey(level)){
                this.letter = letterMatrix.get(level);
            }
        }
    }
}

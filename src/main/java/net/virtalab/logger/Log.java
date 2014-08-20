package net.virtalab.logger;


import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    public static final LogLevel defaultLogLevel = LogLevel.OFF;
    /**
     * Defines log level we use at moment of logging
     * By default it equals to default log level
     * Normally you define it ones at application startup,
     * but also possible to update as many times as you want
     * see {@link #updateCurrentLogLevel(LogLevel)}
     */
    private static LogLevel currentLogLevel = defaultLogLevel;

    //time format
    public static final String defaultTimestampFormat = "dd/MM/yy HH:mm:ss.SSS";
    /**
     * Defines timestamp format. now() is a moment of logging.
     * By default it equals to default timestamp format (dd/MM/yy HH:mm:ss.SSS)
     * Format can by customized by using {@link #setTimestampFormat(String)}
     */
    private static String currentTimestampFormat = defaultTimestampFormat;

    //message format settings
    private static boolean isLetterEnabled = true;
    private static boolean isTimeEnabled = true;
    private static boolean isClassNameEnabled = true;
    private static boolean isTagEnabled = true;

    //constant
    public static final String NEWLINE = System.getProperty("line.separator");

    //matrix 1,2,3
    private static Map<LogLevel,String> colorMatrix;
    private static Map<LogLevel, String> letterMatrix;
    private static Map<LogLevel, PrintStream> streamMatrix;

    static {
        populateMatrix();
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
            Log.currentTimestampFormat = defaultTimestampFormat;
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
     * Do not show tag, even if is passed as arg
     */
    public static void noTag(){
        Log.isTagEnabled = false;
    }
    /**
     * Changes log message color for concrete log level
     *
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
     * Changes output stream for concrete log level
     *
     * @param level log level
     * @param stream valid Print Stream
     */
    public static void changeStreamForLevel(LogLevel level, PrintStream stream){
        if(level==null || stream==null){ return; }
        streamMatrix.put(level, stream);
    }
    /**
     * Updates (sets) new current log level {@link #currentLogLevel}
     *
     * @param logLevel desirable log level
     */
    public static void updateCurrentLogLevel(LogLevel logLevel) {
        if(logLevel==null){ return; }
        Log.currentLogLevel = logLevel;
    }

    /**
     * Sets desirable format of timestamp
     *
     * @param timestampFormat ts format as SimpleDateFormat requires
     * @see java.text.SimpleDateFormat
     */
    public static void setTimestampFormat(String timestampFormat){
        if(timestampFormat==null || timestampFormat.isEmpty()){
            return;
        }
        Log.currentTimestampFormat = timestampFormat;
    }

    /**
     * Provides log level set at moment of calling
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

    /**
     * Prints tag and TRACE message
     *
     * @param tag tag aka prefix
     * @param message ready-to-print message
     */
    public static void trace(String tag, String message){
        if(currentLogLevel.priority >= LogLevel.TRACE.priority) {
            LogObject l = createLogObject(LogLevel.TRACE, tag, message);
            printIt(l);
        }
    }

    /**
     * Prints TRACE message
     *
     * @param message message to log
     */
    public static void trace(String message){
        if(currentLogLevel.priority >= LogLevel.TRACE.priority) {
            LogObject l = createLogObject(LogLevel.TRACE, message);
            printIt(l);
        }
    }

    /**
     * Prints tag, custom message and exception stacktrace at TRACE level
     *
     * @param tag tag aka prefix
     * @param message custom message
     * @param t exception or error object
     */
    public static void trace(String tag, String message, Throwable t){
        if(currentLogLevel.priority >= LogLevel.TRACE.priority) {
            LogObject l = createLogObject(LogLevel.TRACE, tag, message, t);
            printIt(l);
        }
    }

    /**
     * Prints tag and exception stacktrace at TRACE level
     *
     * @param tag tag aka prefix
     * @param t exception or error object
     */
    public static void trace(String tag, Throwable t){
        if(currentLogLevel.priority >= LogLevel.TRACE.priority) {
            LogObject l = createLogObject(LogLevel.TRACE, tag, t);
            printIt(l);
        }
    }

    /**
     * Prints just exception stacktrace without any prefixes and messages at TRACE level
     *
     * @param t exception or error object
     */
    public static void trace(Throwable t){
        if(currentLogLevel.priority >= LogLevel.TRACE.priority) {
            LogObject l = createLogObject(LogLevel.TRACE, t);
            printIt(l);
        }
    }

    /**
     * Prints any object using its string representation {@link Object#toString()} at TRACE level
     *
     * @param o any object
     * @since 1.6
     */
    public static void trace(Object o){
        if(currentLogLevel.priority >= LogLevel.TRACE.priority){
            LogObject l  = createLogObject(LogLevel.TRACE, o);
            printIt(l);
        }
	}

    /**
     * Prints tag and TRACE message
     *
     * @param tag tag aka prefix
     * @param message ready-to-print message
     */
    public static void t(String tag, String message){
        trace(tag, message);
    }

    /**
     * Prints TRACE message
     *
     * @param message message to log
     */
    public static void t(String message){
        trace(message);
    }

    /**
     * Prints tag, custom message and exception stacktrace at TRACE level
     *
     * @param tag tag aka prefix
     * @param message custom message
     * @param t exception or error object
     */
    public static void t(String tag, String message, Throwable t){
        trace(tag, message, t);
    }

    /**
     * Prints tag and exception stacktrace at TRACE level
     *
     * @param tag tag aka prefix
     * @param t exception or error object
     */
    public static void t(String tag, Throwable t){
        trace(tag, t);
    }

    /**
     * Prints just exception stacktrace without any prefixes and messages at TRACE level
     *
     * @param t exception or error object
     */
    public static void t(Throwable t){
        trace(t);
    }

    /**
     * Prints any object using its string representation {@link Object#toString()} at TRACE level
     *
     * @param o any object
     * @since 1.6
     */
    public static void t(Object o) {
        trace(o);
    }

    /**
     * Prints tag and DEBUG message
     *
     * @param tag tag aka prefix
     * @param message ready-to-print message
     */
    public static void debug(String tag, String message){
        if(currentLogLevel.priority >= LogLevel.DEBUG.priority) {
            LogObject l = createLogObject(LogLevel.DEBUG, tag, message);
            printIt(l);
        }
    }

    /**
     * Prints DEBUG message
     *
     * @param message message to log
     */
    public static void debug(String message){
        if(currentLogLevel.priority >= LogLevel.DEBUG.priority) {
            LogObject l = createLogObject(LogLevel.DEBUG, message);
            printIt(l);
        }
    }

    /**
     * Prints tag, custom message and exception stacktrace at DEBUG level
     *
     * @param tag tag aka prefix
     * @param message custom message
     * @param t exception or error object
     */
    public static void debug(String tag, String message, Throwable t){
        if(currentLogLevel.priority >= LogLevel.DEBUG.priority) {
            LogObject l = createLogObject(LogLevel.DEBUG, tag, message, t);
            printIt(l);
        }
    }

    /**
     * Prints tag and exception stacktrace at DEBUG level
     *
     * @param tag tag aka prefix
     * @param t exception or error object
     */
    public static void debug(String tag, Throwable t){
        if(currentLogLevel.priority >= LogLevel.DEBUG.priority) {
            LogObject l = createLogObject(LogLevel.DEBUG, tag, t);
            printIt(l);
        }
    }

    /**
     * Prints just exception stacktrace without any prefixes and messages at DEBUG level
     *
     * @param t exception or error object
     */
    public static void debug(Throwable t){
        if(currentLogLevel.priority >= LogLevel.DEBUG.priority) {
            LogObject l = createLogObject(LogLevel.DEBUG, t);
            printIt(l);
        }
    }

    /**
     * Prints any object using its string representation {@link Object#toString()} at DEBUG level
     *
     * @param o any object
     * @since 1.6
     */
    public static void debug(Object o){
        if(currentLogLevel.priority >= LogLevel.DEBUG.priority){
            LogObject l  = createLogObject(LogLevel.DEBUG, o);
            printIt(l);
        }
    }

    /**
     * Prints tag and DEBUG message
     *
     * @param tag tag aka prefix
     * @param message ready-to-print message
     */
    public static void d(String tag, String message){
        debug(tag, message);
    }

    /**
     * Prints DEBUG message
     *
     * @param message message to log
     */
    public static void d(String message){
        debug(message);
    }

    /**
     * Prints tag, custom message and exception stacktrace at DEBUG level
     *
     * @param tag tag aka prefix
     * @param message custom message
     * @param t exception or error object
     */
    public static void d(String tag, String message, Throwable t){
        debug(tag, message, t);
    }

    /**
     * Prints tag and exception stacktrace at DEBUG level
     *
     * @param tag tag aka prefix
     * @param t exception or error object
     */
    public static void d(String tag, Throwable t){
        debug(tag, t);
    }

    /**
     * Prints just exception stacktrace without any prefixes and messages at DEBUG level
     *
     * @param t exception or error object
     */
    public static void d(Throwable t){
        debug(t);
    }

    /**
     * Prints any object using its string representation {@link Object#toString()} at DEBUG level
     *
     * @param o any object
     * @since 1.6
     */
    public static void d(Object o){
        debug(o);
    }

    /**
     * Prints tag and INFO message
     *
     * @param tag tag aka prefix
     * @param message ready-to-print message
     */
    public static void info(String tag, String message){
        if(currentLogLevel.priority >= LogLevel.INFO.priority) {
            LogObject l = createLogObject(LogLevel.INFO, tag, message);
            printIt(l);
        }
    }

    /**
     * Prints INFO message
     *
     * @param message message to log
     */
    public static void info(String message){
        if(currentLogLevel.priority >= LogLevel.INFO.priority) {
            LogObject l = createLogObject(LogLevel.INFO, message);
            printIt(l);
        }
    }

    /**
     * Prints tag, custom message and exception stacktrace at INFO level
     *
     * @param tag tag aka prefix
     * @param message custom message
     * @param t exception or error object
     */
    public static void info(String tag, String message, Throwable t){
        if(currentLogLevel.priority >= LogLevel.INFO.priority) {
            LogObject l = createLogObject(LogLevel.INFO, tag, message, t);
            printIt(l);
        }
    }

    /**
     * Prints tag and exception stacktrace at INFO level
     *
     * @param tag tag aka prefix
     * @param t exception or error object
     */
    public static void info(String tag, Throwable t){
        if(currentLogLevel.priority >= LogLevel.INFO.priority) {
            LogObject l = createLogObject(LogLevel.INFO, tag, t);
            printIt(l);
        }
    }

    /**
     * Prints just exception stacktrace without any prefixes and messages at INFO level
     *
     * @param t exception or error object
     */
    public static void info(Throwable t){
        if(currentLogLevel.priority >= LogLevel.INFO.priority) {
            LogObject l = createLogObject(LogLevel.INFO, t);
            printIt(l);
        }
    }

    /**
     * Prints any object using its string representation {@link Object#toString()} at INFO level
     *
     * @param o any object
     * @since 1.6
     */
    public static void info(Object o){
        if(currentLogLevel.priority >= LogLevel.INFO.priority){
            LogObject l  = createLogObject(LogLevel.INFO, o);
            printIt(l);
        }
    }

    /**
     * Prints tag and INFO message
     *
     * @param tag tag aka prefix
     * @param message ready-to-print message
     */
    public static void i(String tag, String message){
        info(tag, message);
    }

    /**
     * Prints INFO message
     *
     * @param message message to log
     */
    public static void i(String message){
        info(message);
    }

    /**
     * Prints tag, custom message and exception stacktrace at INFO level
     *
     * @param tag tag aka prefix
     * @param message custom message
     * @param t exception or error object
     */
    public static void i(String tag, String message, Throwable t){
        info(tag, message, t);
    }

    /**
     * Prints tag and exception stacktrace at INFO level
     *
     * @param tag tag aka prefix
     * @param t exception or error object
     */
    public static void i(String tag, Throwable t){
        info(tag, t);
    }

    /**
     * Prints just exception stacktrace without any prefixes and messages at INFO level
     *
     * @param t exception or error object
     */
    public static void i(Throwable t){
        info(t);
    }

    /**
     * Prints any object using its string representation {@link Object#toString()} at INFO level
     *
     * @param o any object
     * @since 1.6
     */
    public static void i(Object o){
        info(o);
    }

    /**
     * Prints tag and WARNING message
     *
     * @param tag tag aka prefix
     * @param message ready-to-print message
     */
    public static void warn(String tag, String message){
        if(currentLogLevel.priority >= LogLevel.WARN.priority) {
            LogObject l = createLogObject(LogLevel.WARN, tag, message);
            printIt(l);
        }
    }

    /**
     * Prints WARNING message
     *
     * @param message message to log
     */
    public static void warn(String message){
        if(currentLogLevel.priority >= LogLevel.WARN.priority) {
            LogObject l = createLogObject(LogLevel.WARN, message);
            printIt(l);
        }
    }

    /**
     * Prints tag, custom message and exception stacktrace at WARNING level
     *
     * @param tag tag aka prefix
     * @param message custom message
     * @param t exception or error object
     */
    public static void warn(String tag, String message, Throwable t){
        if(currentLogLevel.priority >= LogLevel.WARN.priority) {
            LogObject l = createLogObject(LogLevel.WARN, tag, message, t);
            printIt(l);
        }
    }

    /**
     * Prints tag and exception stacktrace at WARNING level
     *
     * @param tag tag aka prefix
     * @param t exception or error object
     */
    public static void warn(String tag, Throwable t){
        if(currentLogLevel.priority >= LogLevel.WARN.priority) {
            LogObject l = createLogObject(LogLevel.WARN, tag, t);
            printIt(l);
        }
    }

    /**
     * Prints just exception stacktrace without any prefixes and messages at WARNING level
     *
     * @param t exception or error object
     */
    public static void warn(Throwable t){
        if(currentLogLevel.priority >= LogLevel.WARN.priority) {
            LogObject l = createLogObject(LogLevel.WARN, t);
            printIt(l);
        }
    }

    /**
     * Prints any object using its string representation {@link Object#toString()} at WARNING level
     *
     * @param o any object
     * @since 1.6
     */
    public static void warn(Object o){
        if(currentLogLevel.priority >= LogLevel.WARN.priority){
            LogObject l  = createLogObject(LogLevel.WARN, o);
            printIt(l);
        }
    }

    /**
     * Prints tag and WARNING message
     *
     * @param tag tag aka prefix
     * @param message ready-to-print message
     */
    public static void w(String tag, String message){
        warn(tag, message);
    }

    /**
     * Prints WARNING message
     *
     * @param message message to log
     */
    public static void w(String message){
        warn(message);
    }

    /**
     * Prints tag, custom message and exception stacktrace at WARNING level
     *
     * @param tag tag aka prefix
     * @param message custom message
     * @param t exception or error object
     */
    public static void w(String tag, String message, Throwable t){
        warn(tag, message, t);
    }

    /**
     * Prints tag and exception stacktrace at WARNING level
     *
     * @param tag tag aka prefix
     * @param t exception or error object
     */
    public static void w(String tag, Throwable t){
        warn(tag, t);
    }

    /**
     * Prints just exception stacktrace without any prefixes and messages at WARNING level
     *
     * @param t exception or error object
     */
    public static void w(Throwable t){
        warn(t);
    }

    /**
     * Prints any object using its string representation {@link Object#toString()} at WARNING level
     *
     * @param o any object
     * @since 1.6
     */
    public static void w(Object o){
        warn(o);
    }

    /**
     * Prints tag and ERROR message
     *
     * @param tag tag aka prefix
     * @param message ready-to-print message
     */
    public static void error(String tag, String message){
        if(currentLogLevel.priority >= LogLevel.ERROR.priority) {
            LogObject l = createLogObject(LogLevel.ERROR, tag, message);
            printIt(l);
        }
    }

    /**
     * Prints ERROR message
     *
     * @param message message to log
     */
    public static void error(String message){
        if(currentLogLevel.priority >= LogLevel.ERROR.priority) {
            LogObject l = createLogObject(LogLevel.ERROR, message);
            printIt(l);
        }
    }

    /**
     * Prints tag, custom message and exception stacktrace at ERROR level
     *
     * @param tag tag aka prefix
     * @param message custom message
     * @param t exception or error object
     */
    public static void error(String tag, String message, Throwable t){
        if(currentLogLevel.priority >= LogLevel.ERROR.priority) {
            LogObject l = createLogObject(LogLevel.ERROR, tag, message, t);
            printIt(l);
        }
    }

    /**
     * Prints tag and exception stacktrace at ERROR level
     *
     * @param tag tag aka prefix
     * @param t exception or error object
     */
    public static void error(String tag, Throwable t){
        if(currentLogLevel.priority >= LogLevel.ERROR.priority) {
            LogObject l = createLogObject(LogLevel.ERROR, tag, t);
            printIt(l);
        }
    }

    /**
     * Prints just exception stacktrace without any prefixes and messages at ERROR level
     *
     * @param t exception or error object
     */
    public static void error(Throwable t){
        if(currentLogLevel.priority >= LogLevel.ERROR.priority) {
            LogObject l = createLogObject(LogLevel.ERROR, t);
            printIt(l);
        }
    }

    /**
     * Prints any object using its string representation {@link Object#toString()} at ERROR level
     *
     * @param o any object
     * @since 1.6
     */
    public static void error(Object o){
        if(currentLogLevel.priority >= LogLevel.ERROR.priority){
            LogObject l  = createLogObject(LogLevel.ERROR, o);
            printIt(l);
        }
    }

    /**
     * Prints tag and ERROR message
     *
     * @param tag tag aka prefix
     * @param message ready-to-print message
     */
    public static void err(String tag, String message){
        error(tag, message);
    }

    /**
     * Prints ERROR message
     *
     * @param message message to log
     */
    public static void err(String message){
        error(message);
    }

    /**
     * Prints tag, custom message and exception stacktrace at ERROR level
     *
     * @param tag tag aka prefix
     * @param message custom message
     * @param t exception or error object
     */
    public static void err(String tag, String message, Throwable t){
        error(tag, message, t);
    }

    /**
     * Prints tag and exception stacktrace at ERROR level
     *
     * @param tag tag aka prefix
     * @param t exception or error object
     */
    public static void err(String tag, Throwable t){
        error(tag, t);
    }

    /**
     * Prints just exception stacktrace without any prefixes and messages at ERROR level
     *
     * @param t exception or error object
     */
    public static void err(Throwable t){
        error(t);
    }

    /**
     * Prints any object using its string representation {@link Object#toString()} at ERROR level
     *
     * @param o any object
     * @since 1.6
     */
    public static void err(Object o){
        error(o);
    }

    /**
     * Prints tag and ERROR message
     *
     * @param tag tag aka prefix
     * @param message ready-to-print message
     */
    public static void e(String tag, String message){
        error(tag, message);
    }

    /**
     * Prints ERROR message
     *
     * @param message message to log
     */
    public static void e(String message){
        error(message);
    }

    /**
     * Prints tag, custom message and exception stacktrace at ERROR level
     *
     * @param tag tag aka prefix
     * @param message custom message
     * @param t exception or error object
     */
    public static void e(String tag, String message, Throwable t){
        error(tag, message, t);
    }

    /**
     * Prints tag and exception stacktrace at ERROR level
     *
     * @param tag tag aka prefix
     * @param t exception or error object
     */
    public static void e(String tag, Throwable t){
        error(tag, t);
    }

    /**
     * Prints just exception stacktrace without any prefixes and messages at ERROR level
     *
     * @param t exception or error object
     */
    public static void e(Throwable t){
        error(t);
    }

    /**
     * Prints any object using its string representation {@link Object#toString()} at ERROR level
     *
     * @param o any object
     * @since 1.6
     */
    public static void e(Object o){
        error(o);
    }

    /**
     * Prints tag and "What the failure?" message. Report problem that should never happen
     *
     * @param tag tag aka prefix
     * @param message ready-to-print message
     */
    public static void wtf(String tag, String message){
        if(currentLogLevel.priority >= LogLevel.ERROR.priority) {
            LogObject l = createLogObject(LogLevel.ERROR, tag, message);
            l.letter = "WTF";
            printIt(l);
        }
    }

    /**
     * Prints "What the failure?" message. Report problem that should never happen.
     *
     * @param message message to log
     */
    public static void wtf(String message){
        if(currentLogLevel.priority >= LogLevel.ERROR.priority) {
            LogObject l = createLogObject(LogLevel.ERROR, message);
            l.letter = "WTF";
            printIt(l);
        }
    }

    /**
     * Prints tag, custom message and exception stacktrace at any (but OFF) level
     *
     * @param tag tag aka prefix
     * @param message custom message
     * @param t exception or error object
     */
    public static void wtf(String tag, String message, Throwable t){
        if(currentLogLevel.priority >= LogLevel.ERROR.priority) {
            LogObject l = createLogObject(LogLevel.ERROR, tag, message, t);
            l.letter = "WTF";
            printIt(l);
        }
    }

    /**
     * Prints tag and exception stacktrace at any (but OFF) level
     *
     * @param tag tag aka prefix
     * @param t exception or error object
     */
    public static void wtf(String tag, Throwable t){
        if(currentLogLevel.priority >= LogLevel.ERROR.priority) {
            LogObject l = createLogObject(LogLevel.ERROR, tag, t);
            l.letter = "WTF";
            printIt(l);
        }
    }

    /**
     * Prints just exception stacktrace without any prefixes and messages at any (but OFF) level
     *
     * @param t exception or error object
     */
    public static void wtf(Throwable t){
        if(currentLogLevel.priority >= LogLevel.ERROR.priority) {
            LogObject l = createLogObject(LogLevel.ERROR, t);
            l.letter = "WTF";
            printIt(l);
        }
    }

    /**
     * Prints any object using its string representation {@link Object#toString()} at any (but OFF) level
     *
     * @param o any object
     * @since 1.6
     */
    public static void wtf(Object o){
        if(currentLogLevel.priority >= LogLevel.ERROR.priority){
            LogObject l  = createLogObject(LogLevel.ERROR, o);
            printIt(l);
        }
    }

    //FOLLOWING METHODS AND CLASSES ARE PRIVATE API

    /**
     * Prepares message and prints it
     * @param l log object
     */
    private static void printIt(LogObject l){
        if(l==null){ return; }
        String ready2PrintMessage = makeString(l);
        publish(l.level, ready2PrintMessage);
    }

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

    /**
     * Produces log string from LogObject
     *
     * @param logObject log object with all properties set
     * @return ready-to-print string
     */
    private static String makeString(LogObject logObject){
        StringBuilder sb = new StringBuilder();
        sb.append(logObject.color);
        if(isLetterEnabled){
            sb.append(logObject.letter).append(" ");
        }
        if(isTimeEnabled){
            String ts = getTimeStamp();
            sb.append(ts).append(" ");
        }
        if(isClassNameEnabled){
            String clsName = getCallerClassName();
            if(clsName!=null){
                sb.append(clsName).append(" ");
            }
        }
        if(isTagEnabled && logObject.tag!=null){
            if(! logObject.tag.isEmpty()){
                sb.append(logObject.tag).append(" ");
            }
        }
        if(logObject.message!=null){
            if(! logObject.message.isEmpty()){
                sb.append(logObject.message);
            }
        }
        if(logObject.th!=null){
            sb.append(NEWLINE);
            sb.append(getStackTrace(logObject.th));
        }
        sb.append(Color.RESET);
        return sb.toString();
    }

    /**
     * Finds short name of class that called Log method
     *
     * @return string contains name of class or NULL in case of error
     */
    private static String getCallerClassName() {
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        for (int i = 1; i < stElements.length; i++) {
            StackTraceElement ste = stElements[i];
            if (!ste.getClassName().equals(Log.class.getName()) && ste.getClassName().indexOf("java.lang.Thread") != 0) {
                String fullName = ste.getClassName();
                String[] arr = fullName.split("\\.");
                if(arr.length>=1){
                    return arr[arr.length-1];
                } else {
                    return ste.getClassName();
                }

            }
        }
        return null;
    }

    /**
     * Throwable's stacktrace to String
     *
     * @param t throwable with its stacktrace
     * @return string with stacktrace
     */
    private static String getStackTrace(Throwable t){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }

    /**
     * Calculates timestamp, applies current format and provide it as string
     *
     * @return string with timestamp
     */
    private static String getTimeStamp(){
        Date now;
        String rslt;
        SimpleDateFormat format;
        try {
            format = new SimpleDateFormat(Log.currentTimestampFormat);
        }catch (Exception e){
            format = new SimpleDateFormat(Log.defaultTimestampFormat);
        }

        now = new Date();
        rslt = format.format(now);

        return rslt;
    }

    /**
     * Standard actions for tag+message combination
     *
     * @param lvl log level
     * @param tag prefix
     * @param message message
     * @return LogObject for inner usage
     */
    private static LogObject createLogObject(LogLevel lvl, String tag, String message){
        if(tag==null || message==null){ return null; }
        LogObject l = createLogObject(lvl);
        l.tag = tag;
        l.message = message;
        return l;
    }

    /**
     * Standard actions for tag+message+exception combination
     *
     * @param lvl log level
     * @param tag prefix
     * @param message message
     * @param th exception
     * @return LogObject for inner usage
     */
    private static LogObject createLogObject(LogLevel lvl, String tag, String message,Throwable th){
        if(tag==null || message==null || th==null){ return null; }
        LogObject l = createLogObject(lvl);
        l.tag = tag;
        l.message = message;
        l.th = th;
        return l;
    }

    /**
     * Standard actions for tag+exception combination
     *
     * @param lvl log level
     * @param tag tag aka prefix
     * @param th exception
     * @return LogObject for inner usage
     */
    private static LogObject createLogObject(LogLevel lvl, String tag, Throwable th){
        if(tag==null || th==null){ return null; }
        LogObject l = createLogObject(lvl);
        l.tag = tag;
        l.th = th;
        return l;
    }

    /**
     * Standard actions for message override
     *
     * @param lvl log level
     * @param message message
     * @return LogObject for inner usage
     */
    private static LogObject createLogObject(LogLevel lvl, String message){
        if(message==null){ return null; }
        LogObject l = createLogObject(lvl);
        l.message = message;
        return l;
    }

    /**
     * Standard actions for exception+message combination
     *
     * @param lvl log level
     * @param th exception
     * @return LogObject for inner usage
     */
    private static LogObject createLogObject(LogLevel lvl, Throwable th){
        if(th==null){ return null; }
        LogObject l = createLogObject(lvl);
        l.th = th;
        return l;
    }

    /**
     * Standard actions for object
     * @param lvl log level
     * @param o any object
     * @return LogObject for internal use
     */
    private static LogObject createLogObject(LogLevel lvl, Object o){
        if(o==null){ return null; }
        LogObject l = createLogObject(lvl);
        l.message = o.toString();
        return l;
    }

    /**
     * Creates new instance of Log Object with given LogLevel
     *
     * @param l given log level (color and letter depends on it)
     * @return LogObject for internal use
     */
    private static LogObject createLogObject(LogLevel l){
        return new LogObject(l);
    }

    /**
     * Populate matrices with defaults
     */
    private static void populateMatrix(){
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
     * Backdoor for tests. Not indented for normal programming.
     */
    public static void reset(){
        currentLogLevel = defaultLogLevel;
        currentTimestampFormat = defaultTimestampFormat;
        isLetterEnabled = true;
        isTimeEnabled = true;
        isClassNameEnabled = true;
        isTagEnabled = true;
        populateMatrix();
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

        private LogObject(LogLevel level){
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

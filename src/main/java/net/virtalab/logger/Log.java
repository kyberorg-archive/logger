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
    private static LogLevel currentLogLevel;

    //time format
    public static final String defaultTimestampFormat = "dd/MM/yy HH:mm:ss.SSS";
    private static String currentTimestampFormat;

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
        if(logLevel==null){ return; }
        Log.currentLogLevel = logLevel;
    }

    /**
     * Set desirable format of timestamp
     * @see java.text.SimpleDateFormat
     *
     * @param timestampFormat ts format as SimpleDateFormat requires
     */
    public static void setTimestampFormat(String timestampFormat){
        if(timestampFormat==null || timestampFormat.isEmpty()){
            return;
        }
        Log.currentTimestampFormat = timestampFormat;
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

    public static void trace(String tag, String message){
        if(currentLogLevel.priority >= LogLevel.TRACE.priority) {
            LogObject l = createLogObject(LogLevel.TRACE, tag, message);
            printIt(l);
        }
    }

    public static void trace(String message){
        if(currentLogLevel.priority >= LogLevel.TRACE.priority) {
            LogObject l = createLogObject(LogLevel.TRACE, message);
            printIt(l);
        }
    }

    public static void trace(String tag, String message, Throwable t){
        if(currentLogLevel.priority >= LogLevel.TRACE.priority) {
            LogObject l = createLogObject(LogLevel.TRACE, tag, message, t);
            printIt(l);
        }
    }

    public static void trace(String tag, Throwable t){
        if(currentLogLevel.priority >= LogLevel.TRACE.priority) {
            LogObject l = createLogObject(LogLevel.TRACE, tag, t);
            printIt(l);
        }
    }

    public static void trace(Throwable t){
        if(currentLogLevel.priority >= LogLevel.TRACE.priority) {
            LogObject l = createLogObject(LogLevel.TRACE, t);
            printIt(l);
        }
    }

    public static void t(String tag, String message){
        trace(tag, message);
    }

    public static void t(String message){
        trace(message);
    }

    public static void t(String tag, String message, Throwable t){
        trace(tag, message, t);
    }

    public static void t(String tag, Throwable t){
        trace(tag, t);
    }

    public static void t(Throwable t){
        trace(t);
    }

    public static void debug(String tag, String message){
        if(currentLogLevel.priority >= LogLevel.DEBUG.priority) {
            LogObject l = createLogObject(LogLevel.DEBUG, tag, message);
            printIt(l);
        }
    }

    public static void debug(String message){
        if(currentLogLevel.priority >= LogLevel.DEBUG.priority) {
            LogObject l = createLogObject(LogLevel.DEBUG, message);
            printIt(l);
        }
    }

    public static void debug(String tag, String message, Throwable t){
        if(currentLogLevel.priority >= LogLevel.DEBUG.priority) {
            LogObject l = createLogObject(LogLevel.DEBUG, tag, message, t);
            printIt(l);
        }
    }

    public static void debug(String tag, Throwable t){
        if(currentLogLevel.priority >= LogLevel.DEBUG.priority) {
            LogObject l = createLogObject(LogLevel.DEBUG, tag, t);
            printIt(l);
        }
    }

    public static void debug(Throwable t){
        if(currentLogLevel.priority >= LogLevel.DEBUG.priority) {
            LogObject l = createLogObject(LogLevel.DEBUG, t);
            printIt(l);
        }
    }

    public static void d(String tag, String message){
        debug(tag, message);
    }

    public static void d(String message){
        debug(message);
    }

    public static void d(String tag, String message, Throwable t){
        debug(tag, message, t);
    }

    public static void d(String tag, Throwable t){
        debug(tag, t);
    }

    public static void d(Throwable t){
        debug(t);
    }

    public static void info(String tag, String message){
        if(currentLogLevel.priority >= LogLevel.INFO.priority) {
            LogObject l = createLogObject(LogLevel.INFO, tag, message);
            printIt(l);
        }
    }

    public static void info(String message){
        if(currentLogLevel.priority >= LogLevel.INFO.priority) {
            LogObject l = createLogObject(LogLevel.INFO, message);
            printIt(l);
        }
    }

    public static void info(String tag, String message, Throwable t){
        if(currentLogLevel.priority >= LogLevel.INFO.priority) {
            LogObject l = createLogObject(LogLevel.INFO, tag, message, t);
            printIt(l);
        }
    }

    public static void info(String tag, Throwable t){
        if(currentLogLevel.priority >= LogLevel.INFO.priority) {
            LogObject l = createLogObject(LogLevel.INFO, tag, t);
            printIt(l);
        }
    }

    public static void info(Throwable t){
        if(currentLogLevel.priority >= LogLevel.INFO.priority) {
            LogObject l = createLogObject(LogLevel.INFO, t);
            printIt(l);
        }
    }

    public static void i(String tag, String message){
        info(tag, message);
    }

    public static void i(String message){
        info(message);
    }

    public static void i(String tag, String message, Throwable t){
        info(tag, message, t);
    }

    public static void i(String tag, Throwable t){
        info(tag, t);
    }

    public static void i(Throwable t){
        info(t);
    }

    public static void warn(String tag, String message){
        if(currentLogLevel.priority >= LogLevel.WARN.priority) {
            LogObject l = createLogObject(LogLevel.WARN, tag, message);
            printIt(l);
        }
    }

    public static void warn(String message){
        if(currentLogLevel.priority >= LogLevel.WARN.priority) {
            LogObject l = createLogObject(LogLevel.WARN, message);
            printIt(l);
        }
    }

    public static void warn(String tag, String message, Throwable t){
        if(currentLogLevel.priority >= LogLevel.WARN.priority) {
            LogObject l = createLogObject(LogLevel.WARN, tag, message, t);
            printIt(l);
        }
    }

    public static void warn(String tag, Throwable t){
        if(currentLogLevel.priority >= LogLevel.WARN.priority) {
            LogObject l = createLogObject(LogLevel.WARN, tag, t);
            printIt(l);
        }
    }

    public static void warn(Throwable t){
        if(currentLogLevel.priority >= LogLevel.WARN.priority) {
            LogObject l = createLogObject(LogLevel.WARN, t);
            printIt(l);
        }
    }

    public static void w(String tag, String message){
        warn(tag, message);
    }

    public static void w(String message){
        warn(message);
    }

    public static void w(String tag, String message, Throwable t){
        warn(tag, message, t);
    }

    public static void w(String tag, Throwable t){
        warn(tag, t);
    }

    public static void w(Throwable t){
        warn(t);
    }

    public static void error(String tag, String message){
        if(currentLogLevel.priority >= LogLevel.ERROR.priority) {
            LogObject l = createLogObject(LogLevel.ERROR, tag, message);
            printIt(l);
        }
    }

    public static void error(String message){
        if(currentLogLevel.priority >= LogLevel.ERROR.priority) {
            LogObject l = createLogObject(LogLevel.ERROR, message);
            printIt(l);
        }
    }

    public static void error(String tag, String message, Throwable t){
        if(currentLogLevel.priority >= LogLevel.ERROR.priority) {
            LogObject l = createLogObject(LogLevel.ERROR, tag, message, t);
            printIt(l);
        }
    }

    public static void error(String tag, Throwable t){
        if(currentLogLevel.priority >= LogLevel.ERROR.priority) {
            LogObject l = createLogObject(LogLevel.ERROR, tag, t);
            printIt(l);
        }
    }

    public static void error(Throwable t){
        if(currentLogLevel.priority >= LogLevel.ERROR.priority) {
            LogObject l = createLogObject(LogLevel.ERROR, t);
            printIt(l);
        }
    }

    public static void err(String tag, String message){
        error(tag, message);
    }

    public static void err(String message){
        error(message);
    }

    public static void err(String tag, String message, Throwable t){
        error(tag, message, t);
    }

    public static void err(String tag, Throwable t){
        error(tag, t);
    }

    public static void err(Throwable t){
        error(t);
    }

    public static void e(String tag, String message){
        error(tag, message);
    }

    public static void e(String message){
        error(message);
    }

    public static void e(String tag, String message, Throwable t){
        error(tag, message, t);
    }

    public static void e(String tag, Throwable t){
        error(tag, t);
    }

    public static void e(Throwable t){
        error(t);
    }

    public static void wtf(String tag, String message){
        if(currentLogLevel.priority >= LogLevel.ERROR.priority) {
            LogObject l = createLogObject(LogLevel.ERROR, tag, message);
            l.letter = "WTF";
            printIt(l);
        }
    }

    public static void wtf(String message){
        if(currentLogLevel.priority >= LogLevel.ERROR.priority) {
            LogObject l = createLogObject(LogLevel.ERROR, message);
            l.letter = "WTF";
            printIt(l);
        }
    }

    public static void wtf(String tag, String message, Throwable t){
        if(currentLogLevel.priority >= LogLevel.ERROR.priority) {
            LogObject l = createLogObject(LogLevel.ERROR, tag, message, t);
            l.letter = "WTF";
            printIt(l);
        }
    }
    public static void wtf(String tag, Throwable t){
        if(currentLogLevel.priority >= LogLevel.ERROR.priority) {
            LogObject l = createLogObject(LogLevel.ERROR, tag, t);
            l.letter = "WTF";
            printIt(l);
        }
    }

    public static void wtf(Throwable t){
        if(currentLogLevel.priority >= LogLevel.ERROR.priority) {
            LogObject l = createLogObject(LogLevel.ERROR, t);
            l.letter = "WTF";
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

    private static String getStackTrace(Throwable t){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }

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

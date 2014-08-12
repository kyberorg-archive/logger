package net.virtalab.logger.test;

import net.virtalab.logger.Color;
import net.virtalab.logger.Log;
import net.virtalab.logger.LogLevel;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Not really test, but fuzzIt
 */
public class DemoTest {

    public static final String TAG = "DEMO";

    /**
     * Demo
     */
    @Test
    public void demo(){
        //setup logger
        Log.init(LogLevel.TRACE);
        //any time you can update log level
        Log.updateCurrentLogLevel(LogLevel.DEBUG);
        Log.t(TAG, "You never see me at your log");
        Log.i("Updated log level is: "+Log.getCurrentLogLevel());
        Log.i("Updated log level int representation: "+Log.getCurrentLogLevelAsInt());

        //Status Quo
        Log.updateCurrentLogLevel(LogLevel.TRACE);

        //if you don't happy with default color schema, you can change colors
        Log.changeMessageColor(LogLevel.DEBUG, Color.CYAN);
        Log.changeMessageColor(LogLevel.TRACE, Color.WHITE);

        //at this step we have full log messages.
        Log.t("This is trace message");
        Log.d(TAG, "This is debug message with Tag (Prefix)");

        //Changing timestamp format, if you don't happy with default one
        String newTSFormat = "dd.MM.yy hh:mm";
        Log.setTimestampFormat(newTSFormat);
        Log.debug("Changing timestamp to format "+newTSFormat);

        Log.i(TAG, "For your information: at next line you will be warned");
        Log.w(TAG, "Just a word of warning");
        Log.e(TAG, "Error line");
        Log.wtf(TAG, "What a terrible fail! Read the manual");

        //assume that we want no more tags, let's disable them
        Log.noTag();
        Log.i(TAG,"This message (and others below) appear without tag (prefix), even if we pass it to method");

        //assume that we don't want to see timestamp anymore
        Log.noTime();

        Log.i("Look ma, there is no timestamp at this line (and below)!");
        Log.info("Methods Log.info() and Log.i() have same effect");

        Log.i("Same situation with methods: ");
        Log.e("Log.e()");
        Log.err("Log.err()");
        Log.error("Log.error()");
        //assume that we don't want to see class name as well
        Log.noClassName();

        Log.info("Let's test exceptions");
        try{
            int a = 5/0;
        }catch (ArithmeticException ae){
            Log.e(TAG,ae);
            //And we can log without TAG as well
            Log.e(ae);
        }

        //There is ability to change Stream for certain level
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream errStream = new PrintStream(os);
        Log.changeStreamForLevel(LogLevel.ERROR,errStream);
        Log.e("Err 1");
        Log.err("Err 2");
        String errors = os.toString();
        Log.i("We have caught following errors: "+errors);
        //Status Quo
        Log.changeStreamForLevel(LogLevel.ERROR, System.err);

        Log.i("Now let's Reset Log to defaults and see more examples...");
        Log.reset();
        Log.updateCurrentLogLevel(LogLevel.TRACE);

        Log.w("Now we intentionally dive into exception");
        Object[] objects = new Object[1];
        try {
            Object neverGotMe = objects[2];
        }catch (ArrayIndexOutOfBoundsException e){
            //we can log it with message and TAG
            String ourCustomMessage = "You cannot ask 3rd element when array has only 2 elements";
            Log.t(TAG, ourCustomMessage, e);
            Log.i(TAG, ourCustomMessage, e);
            Log.w(TAG, ourCustomMessage, e);
            Log.e(TAG, ourCustomMessage, e);
            Log.wtf(TAG, ourCustomMessage, e);

            //or only tag and exception
            Log.t(TAG, e);
            Log.i(TAG, e);
            Log.w(TAG, e);
            Log.e(TAG, e);
            Log.wtf(TAG, e);

            //or just exception
            Log.t(e);
            Log.i(e);
            Log.w(e);
            Log.e(e);
            Log.wtf(e);

        }

        System.out.println("THE END. Thank you for been with us!");
    }
}

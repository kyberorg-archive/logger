package net.virtalab.logger.test;

import net.virtalab.logger.Log;
import net.virtalab.logger.LogLevel;
import org.junit.Test;

/**
 * Not really test, but dermo
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
        //at this step we have full log messages.
        Log.t("This is trace message");
        Log.d(TAG,"This is debug message with Tag (Prefix)");
        Log.i("For your information: at next line you will be warned");
        Log.w("Just a word of warning");
        Log.e("Error line");
        Log.wtf("What a terrible fail! Read the manual");

        //assume that we don't want to see timestamp anymore
        Log.noTime();

        Log.i("Look ma, there is no timestamp at this line!");
        Log.info("Methods Log.info() and Log.i() have same effect");

        //assume that we don't want to see class name as well
        Log.noClassName();

        Log.info("Let's test exceptions");
        try{
            int a = 5/0;
        }catch (ArithmeticException ae){
            Log.e(TAG,ae);
        }
    }
}

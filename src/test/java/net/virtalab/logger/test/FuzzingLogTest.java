package net.virtalab.logger.test;

import net.virtalab.logger.Color;
import net.virtalab.logger.Log;
import net.virtalab.logger.LogLevel;
import org.junit.Assert;
import org.junit.Test;

/**
 * Not really test, but fuzzIt
 */
public class FuzzingLogTest {

    public static final String TAG = null;

    /**
     * Demo
     */
    @Test
    public void fuzzIt(){
        //setup logger
        Log.init(null);
        //expecting default level here
        LogLevel lvl = Log.getCurrentLogLevel();
        Assert.assertEquals(LogLevel.OFF, lvl);

        //setting log level we happy with
        Log.updateCurrentLogLevel(null); //ha-ha, it is another test by the way
        LogLevel lvl1 = Log.getCurrentLogLevel();
        Assert.assertEquals(LogLevel.OFF, lvl1);

        //Now really set it
        Log.updateCurrentLogLevel(LogLevel.TRACE);
        LogLevel lvl2 = Log.getCurrentLogLevel();
        Assert.assertEquals(LogLevel.TRACE,lvl2);

        //if you don't happy with default color schema, you can change colors
        Log.changeMessageColor(null, Color.CYAN);
        Log.changeMessageColor(LogLevel.DEBUG, null);
        Log.changeMessageColor(LogLevel.DEBUG, Color.CYAN);

        //following two lines we don't want to see
        Log.d(TAG, "This is debug message with Tag (Prefix)");

        //Changing timestamp format...to NULL
        String newTSFormat = null;
        Log.setTimestampFormat(newTSFormat);
        Log.debug("Changing timestamp to format "+newTSFormat);

        //if we see default TS, test passed

        Log.i("");
        Log.e(TAG,"");
        Log.wtf("");

        //assume that we don't want to see timestamp anymore
        Log.noTime();

        //assume that we don't want to see class name as well
        Log.noClassName();

        Log.info("Let's test exceptions");
        try{
            int a = 5/0;
        }catch (ArithmeticException ae){
            Log.e(TAG,ae);
        }

        Log.changeStreamForLevel(LogLevel.ERROR,null);
        Log.changeStreamForLevel(null,null);

        Log.changeStreamForLevel(LogLevel.ERROR,System.err);


        System.out.println("THE END. Thank you for been with us!");
    }
}

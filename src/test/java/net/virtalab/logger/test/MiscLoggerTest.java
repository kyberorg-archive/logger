package net.virtalab.logger.test;

import net.virtalab.logger.Color;
import net.virtalab.logger.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Other Tests of Logger
 */
public class MiscLoggerTest {

    private final ByteArrayOutputStream stdOut = new ByteArrayOutputStream();
    private final ByteArrayOutputStream stdErr = new ByteArrayOutputStream();

    @Before
    public void init(){
        Logger.resetLogLevel();

        System.setOut(new PrintStream(stdOut));
        System.setErr(new PrintStream(stdErr));
    }

    @Test
    public void loggerWithEmptyPrefix(){
        String message = "This is Message";
        Logger log = Logger.getLogger("");
        log.info(message);
        //clean up
        log=null;

        String expectedLine = "("+Color.WHITE+"INFO"+Color.RESET+") "+message;
        String actualLine = stdOut.toString().trim();
        Assert.assertEquals(expectedLine, actualLine);
    }

    @Test
    public void loggerWithoutPrefix(){
        String message = "This is Message";
        Logger log = Logger.getLogger(null);
        log.info(message);

        //clean up
        log=null;

        String expectedLine = "("+Color.WHITE+"INFO"+Color.RESET+") "+message;
        String actualLine = stdOut.toString().trim();
        Assert.assertEquals(expectedLine, actualLine);
    }

    @Test
    public void getCurrentLogLevelInfo(){
        Logger.Level exceptedDefaultLevel = Logger.Level.INFO;
        Logger.Level actualDefaultLevel = Logger.getCurrentLogLevel();

        Assert.assertEquals(exceptedDefaultLevel,actualDefaultLevel);

        Logger.init(Logger.Level.ERROR);
        Logger.Level exceptedNewLevel = Logger.Level.ERROR;
        Logger.Level actualNewLevel = Logger.getCurrentLogLevel();

        Assert.assertEquals(exceptedNewLevel,actualNewLevel);
    }

    @Test
    public void getLogLevelAsInt(){
        Logger.init(Logger.Level.WARN);
        int exceptedIntValueOfLevel = 2;
        int actualIntValueOfLevel = Logger.getCurrentLogLevelAsInt();
        Assert.assertEquals(exceptedIntValueOfLevel,actualIntValueOfLevel);
    }
    @After
    public void releaseStreams(){
        System.setOut(System.out);
        System.setErr(System.err);
    }
}

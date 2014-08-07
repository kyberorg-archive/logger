package net.virtalab.logger.test;

import net.virtalab.logger.Color;
import net.virtalab.logger.LogLevel;
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
        Logger.init(LogLevel.INFO);

        log.info(message);

        String expectedLine = "("+Color.WHITE+"INFO"+Color.RESET+") "+message;
        String actualLine = stdOut.toString().trim();
        Assert.assertEquals(expectedLine, actualLine);
    }

    @Test
    public void loggerWithoutPrefix(){
        String message = "This is Message";
        Logger log = Logger.getLogger(null);
        Logger.init(LogLevel.INFO);

        log.info(message);

        String expectedLine = "("+Color.WHITE+"INFO"+Color.RESET+") "+message;
        String actualLine = stdOut.toString().trim();
        Assert.assertEquals(expectedLine, actualLine);
    }

    @Test
    public void getCurrentLogLevelInfo(){
        LogLevel exceptedDefaultLevel = LogLevel.OFF;
        LogLevel actualDefaultLevel = Logger.getCurrentLogLevel();

        Assert.assertEquals(exceptedDefaultLevel,actualDefaultLevel);

        Logger.init(LogLevel.ERROR);
        LogLevel exceptedNewLevel = LogLevel.ERROR;
        LogLevel actualNewLevel = Logger.getCurrentLogLevel();

        Assert.assertEquals(exceptedNewLevel,actualNewLevel);
    }

    @Test
    public void getLogLevelAsInt(){
        Logger.init(LogLevel.WARN);
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

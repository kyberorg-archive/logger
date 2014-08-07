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
    private Logger.Level level = Logger.Level.ERROR;

    private final ByteArrayOutputStream stdOut = new ByteArrayOutputStream();
    private final ByteArrayOutputStream stdErr = new ByteArrayOutputStream();

    @Before
    public void init(){
        Logger.init(level);

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
    @After
    public void releaseStreams(){
        System.setOut(System.out);
        System.setErr(System.err);
    }
}

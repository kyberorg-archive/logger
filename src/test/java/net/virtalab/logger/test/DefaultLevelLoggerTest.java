package net.virtalab.logger.test;

import net.virtalab.logger.Color;
import net.virtalab.logger.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sun.rmi.runtime.Log;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Logger with default level
 */
public class DefaultLevelLoggerTest {
    private String prefix = "Default level Logger";
    private Logger log = Logger.getLogger(prefix);

    private final ByteArrayOutputStream stdOut = new ByteArrayOutputStream();
    private final ByteArrayOutputStream stdErr = new ByteArrayOutputStream();

    @Before
    public void init(){
        Logger.resetLogLevel();

        System.setOut(new PrintStream(stdOut));
        System.setErr(new PrintStream(stdErr));
    }

    @Test
    public void trace(){
        String message = "This is trace message";
        log.trace(message);
        String expectedLine = "";
        String actualLine = stdOut.toString().trim();
        Assert.assertEquals(expectedLine, actualLine);
    }

    /**
     * Test of debug level
     */
    @Test
    public void debug(){
        String message = "This is debug message";
        log.debug(message);
        String expectedLine = "";
        String actualLine = stdOut.toString().trim();
        Assert.assertEquals(expectedLine, actualLine);
    }

    @Test
    public void info(){
        String message = "This is info message";
        log.info(message);
        String expectedLine = "";
        String actualLine = stdOut.toString().trim();
        Assert.assertEquals(expectedLine, actualLine);
    }

    @Test
    public void warn(){
        String message = "This is Warning";
        log.warn(message);
        String expectedLine = "";
        String actualLine = stdErr.toString().trim();
        Assert.assertEquals(expectedLine, actualLine);
    }

    @Test
    public void err(){
        String message = "This is Message about Error";
        log.error(message);
        String expectedLine = "";
        String actualLine = stdErr.toString().trim();
        Assert.assertEquals(expectedLine, actualLine);
    }

    @After
    public void releaseStreams(){
        System.setOut(System.out);
        System.setErr(System.err);
    }

    @After
    public void cleanLog(){
        log = null;
    }
}

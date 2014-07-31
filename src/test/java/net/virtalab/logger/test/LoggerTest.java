package net.virtalab.logger.test;

import junit.framework.Assert;
import net.virtalab.logger.Logger;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests
 */
public class LoggerTest {
    private Logger.Level level = Logger.Level.DEBUG;

    private Logger log = Logger.getLogger("LoggerTest");

    @Before
    public void init(){
        Logger.init(level);
    }

    /**
     * Just simple test
     */
    @Test
    public void debug(){
        String message = "This is debug message";
        log.debug(message);
        //TODO intercept System.out
        Assert.assertTrue(true);
    }

}

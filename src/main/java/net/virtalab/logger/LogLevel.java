package net.virtalab.logger;

/**
 * Logging level
 *
 * @author Alexander Muravya
 * @since 1.4
 */
public enum LogLevel {
    DEBUG(4),
    INFO(3),
    WARN(2),
    ERROR(1),
    OFF(0);

    int priority;

    /**
     * Level constructor
     *
     * @param priority log level priority as int, the higher value, rarer you will see it.
     */
    LogLevel(int priority){
        this.priority = priority;
    }

    /**
     * Provides log level priority as int
     *
     * @return int representation of log level
     */
    public int asInt(){
        return this.priority;
    }

}

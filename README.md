# Virtalab Logger
 This is simple logger for our project.
 It is not really customizable due to convention-over-configuration idea
## Version 1.5.1
 Hotfix version. Fixed issue#2: https://github.com/virtalab/logger/issues/2
## Version 1.5
 New Log class, inspired by Android Log
 
 * Provides simple API (all methods are static):
       * Log.t() or Log.trace() for trace messages
       * Log.d() or Log.debug() for debug messages
       * Log.i() or Log.info() for informational messages
       * Log.w() or Log.warn() for warnings
       * Log.e() or Log.err() or Log.error for errors
       * Log.wtf() for unexpected fails
 * Can log exceptions      
 * Log message format: letter (D,T..) timestamp shortClassName tag message stacktrace
 * New color schema:
       * trace is Cyan
       * debug is Blue
       * info is Green
       * warn is Yellow
       * error and wtf are Red
       
 * Highly configurable. You can: 
        
       * change color for each log level
       * change stream for each log level
       * change timestamp format       
       * disable letter
       * disable timestamp
       * disable tag
       * disable class name 
              
 * Entire message is colored
 
## Version 1.4
* LogLevel class replaces Logger.Level
* Added new LogLevels OFF and TRACE and Logger support for them
* Default log level is OFF

## Version 1.3.1
 * Same as 1.3 by functionality, but Javadocs and Unit tests added
 
## Version 1.3
* First Stable Logger
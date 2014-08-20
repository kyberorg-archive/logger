#Logger Backlog

### Version 1.7-SNAPSHOT
- [ ] PHP's var_dump() functionality
- [ ] Dumper object for dumping objects
- [ ] Dumper setting ```boolean autoboxingTypesAsObjects = true ``` , if true will print ```java.lang.Boolean``` and others as regular objects, otherwise will print boxed primitive
- [ ] Dumper setting ```boolean stringAsObject = false``` , if true will dump String as regular object, otherwise will print string with value

### Version 1.6-SNAPSHOT
- [ ] Support for all log levels in Log object to accept ```java.lang.Object``` as param

### Version 1.5-SNAPSHOT
 * ~~New Log class, inspired by Android Log~~
 * ~~Color for entire message~~
 * ~~Methods with short names like Log.d and their long-named aliases Log.debug~~
 * ~~All methods are static~~
 * ~~New color schema: error - red, warn -yellow, info - green, debug - blue, trace - cyan~~
 * ~~Message format: letter (D,T..) timestamp class prefix  text~~
 * ~~Methods allowing remove almost every part: noLetter, noTime, noClass~~
 * ~~Logging exceptions~~
 * ~~Set custom color for each log level~~
 * ~~Set custom stream for each log level~~
 * ~~Log exception without TAG~~
 * ~~Timestamp format~~
 * ~~Option: noTag~~

### Version 1.4-SNAPSHOT
* ~~Added Javadocs and Unit tests~~
* ~~Log Level moved from Logger to public enum~~
* ~~New Levels OFF and TRACE~~
* ~~Default level is OFF~~

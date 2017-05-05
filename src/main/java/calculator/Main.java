package calculator;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.lf5.LogLevel;
import org.apache.log4j.lf5.LogLevelFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Created by Alexander Dukkardt on 2017-05-05.
 */
public class Main {
    private static Logger log = LoggerFactory.getLogger(Main.class);
    private static  LogLevel logLevel = LogLevel.INFO;
    private static  String expression;
    
    public static void main(String[] args){
        try {
            // read arguments from command lines
            initArgs(args);
            // initialize log4j appender
            initLog4j();
            
            log.info("Expression: {}", expression);
            
        } catch (LogLevelFormatException e) {
            System.out.println("Invalid log level argument. Please use of the following values: INFO, DEBUG, ERROR");
        }
    }
    
    private static void initArgs(String[] args) throws LogLevelFormatException {
        for (String arg : args) {
            if (arg.contains("--log-level=")) {
                logLevel = LogLevel.valueOf(arg.substring("--log-level=".length()).trim());
                if(!logLevel.equals(LogLevel.INFO) && !logLevel.equals(LogLevel.DEBUG) && !logLevel.equals(LogLevel.ERROR)){
                    logLevel = LogLevel.INFO;
                }
            } else {
                expression = arg;
            }
        }
        
    }
    
    private static void initLog4j() {
        Properties p = new Properties();
        String format = "%d %p [%c][%t] - %m%n";
    
        p.setProperty("log4j.rootLogger", logLevel.toString()+",stdout,log");
        p.setProperty("log4j.logger.calculator", logLevel.toString());
        //
        p.setProperty("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender");
        p.setProperty("log4j.appender.stdout.Threshold", logLevel.toString());
        p.setProperty("log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout");
        p.setProperty("log4j.appender.stdout.layout.ConversionPattern", format);
        //
        p.setProperty("log4j.appender.log", "org.apache.log4j.RollingFileAppender");
        p.setProperty("log4j.appender.log.Threshold", logLevel.toString());
        p.setProperty("log4j.appender.log.File", "calculator.log");
        p.setProperty("log4j.appender.log.layout", "org.apache.log4j.PatternLayout");
        p.setProperty("log4j.appender.log.layout.ConversionPattern", format);
        //
        PropertyConfigurator.configure(p);
    }
}

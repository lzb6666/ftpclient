package log;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Target;

/**
 * create by zhong
 * log
 * Date 2019/5/3
 */
public class LogTest {
    private static final Logger log= LoggerFactory.getLogger(LogTest.class);

    @Test
    public void logInfo(){
        log.debug("debug");
        log.error("error");
        log.info("info");
    }
}

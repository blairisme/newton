/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.slave.engine;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.FileAppender;

import java.nio.file.Path;

/**
 * Logs diagnostic information to the log file in given
 * {@link RequestWorkspace workspace}.
 *
 * @author Blair Butterworth
 */
@SuppressWarnings("unchecked")
public class RequestLogger
{
    private Logger logger;

    public RequestLogger(RequestWorkspace workspace) {
        logger = getLogger(workspace.getLog());
    }

    public void error(String message, Throwable error) {
        logger.error(message, error);
    }

    public void info(String message) {
        logger.info(message);
    }

    private Logger getLogger(Path logPath) {
        LoggerContext context = new LoggerContext();
        Logger logger = context.getLogger(RequestLogger.class);
        logger.addAppender(getAppender(context, logPath));
        return logger;
    }

    private FileAppender getAppender(LoggerContext context, Path path) {
        FileAppender fileAppender = new FileAppender();
        fileAppender.setContext(context);
        fileAppender.setEncoder(getEncoder(context));
        fileAppender.setAppend(true);
        fileAppender.setFile(path.toString());
        fileAppender.start();
        return fileAppender;
    }

    private PatternLayoutEncoder getEncoder(LoggerContext context) {
        PatternLayoutEncoder logEncoder = new PatternLayoutEncoder();
        logEncoder.setContext(context);
        logEncoder.setPattern("%d{yyyy-MM-dd HH:mm:ss} %-5p %m%n");
        logEncoder.start();
        return logEncoder;
    }
}

package dev.gamecrash.fallbackserver.util;

import lombok.Setter;
import org.tinylog.Level;
import org.tinylog.core.TinylogLoggingProvider;
import org.tinylog.format.MessageFormatter;
import org.tinylog.provider.LoggingProvider;

public class DynamicLevelLoggingProvider extends TinylogLoggingProvider implements LoggingProvider {
    @Setter
    private volatile static Level activeLevel = Level.INFO;

    public boolean isEnabled(final int depth, final String tag, final Level level) {
        return checkLevel(level)
            && super.isEnabled(depth + 1, tag, level);
    }

    @Override
    public void log(final int depth, final String tag, final Level level, final Throwable exception, final MessageFormatter formatter,
                    final Object obj, final Object... arguments) {
        if (checkLevel(level)) super.log(depth + 1, tag, level, exception, formatter, obj, arguments);
    }

    @Override
    public void log(final String loggerClassName, final String tag, final Level level, final Throwable exception,
                    final MessageFormatter formatter, final Object obj, final Object... arguments) {
        if (checkLevel(level)) super.log(loggerClassName, tag, level, exception, formatter, obj, arguments);
    }

    private boolean checkLevel(Level level) {
        return activeLevel.ordinal() <= level.ordinal();
    }
}

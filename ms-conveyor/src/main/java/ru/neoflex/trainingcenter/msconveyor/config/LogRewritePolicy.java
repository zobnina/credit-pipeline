package ru.neoflex.trainingcenter.msconveyor.config;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.rewrite.RewritePolicy;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.message.SimpleMessage;

@Plugin(name = "LogRewritePolicy", category = "Core", elementType = "rewritePolicy", printObject = true)
public final class LogRewritePolicy implements RewritePolicy {

    public static final String INN_REPLACEMENT = "$1******$3";
    public static final String PASSPORT_SERIES_REPLACEMENT = "$1**$3";
    public static final String PASSPORT_REPLACEMENT = "$1***$3";
    public static final String LAST_NAME_REPLACEMENT = "$1***$3";
    public static final String ACCOUNT_REPLACEMENT = "$1************$3";

    @Override
    public LogEvent rewrite(final LogEvent event) {
        try {
            String formattedMessage = event.getMessage().getFormattedMessage();
            formattedMessage = formattedMessage.replaceAll("(employerInn=\\d{3})(\\d{6})(\\d{3})", INN_REPLACEMENT);
            formattedMessage = formattedMessage.replaceAll("(passportSeries=)(\\d{2})(\\d{2})", PASSPORT_SERIES_REPLACEMENT);
            formattedMessage = formattedMessage.replaceAll("(passportNumber=)(\\d{3})(\\d{3})", PASSPORT_REPLACEMENT);
            formattedMessage = formattedMessage.replaceAll("(account=\\d{4})(\\d{12})(\\d{4})", ACCOUNT_REPLACEMENT);
            formattedMessage = formattedMessage.replaceAll("(lastName=.)([^,]*)([^,],)", LAST_NAME_REPLACEMENT);

            formattedMessage = formattedMessage.replaceAll("(\"employerInn\"\\s*:\\s*\"\\d{3})(\\d{6})(\\d{3}\")", INN_REPLACEMENT);
            formattedMessage = formattedMessage.replaceAll("(\"passportSeries\"\\s*:\\s*\")(\\d{2})(\\d{2}\")", PASSPORT_SERIES_REPLACEMENT);
            formattedMessage = formattedMessage.replaceAll("(\"passportNumber\"\\s*:\\s*\")(\\d{3})(\\d{3}\")", PASSPORT_REPLACEMENT);
            formattedMessage = formattedMessage.replaceAll("(\"account\"\\s*:\\s*\"\\d{4})(\\d{12})(\\d{4}\")", ACCOUNT_REPLACEMENT);
            formattedMessage = formattedMessage.replaceAll("(\"lastName\"\\s*:\\s*\".)([^\"]*)([^\"]\")", LAST_NAME_REPLACEMENT);

            Log4jLogEvent.Builder builder = new Log4jLogEvent.Builder(event);
            builder.setMessage(new SimpleMessage(formattedMessage));

            return builder.build();
        } catch (Exception e) {
            return event;
        }
    }

    /**
     * Метод создания LogRewritePolicy.
     *
     * @return LogRewritePolicy
     */
    @PluginFactory
    public static LogRewritePolicy createPolicy() {
        return new LogRewritePolicy();
    }
}
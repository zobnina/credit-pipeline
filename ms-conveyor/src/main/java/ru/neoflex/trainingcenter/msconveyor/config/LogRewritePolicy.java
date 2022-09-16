package ru.neoflex.trainingcenter.msconveyor.config;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.rewrite.RewritePolicy;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.message.SimpleMessage;

@Plugin(name = "LogRewritePolicy", category = "Core", elementType = "rewritePolicy", printObject = true)
public final class LogRewritePolicy implements RewritePolicy {

    @Override
    public LogEvent rewrite(final LogEvent event) {
        try {
            String formattedMessage = event.getMessage().getFormattedMessage();
            formattedMessage = formattedMessage.replaceAll("(employerInn=\\d{3})(\\d{6})(\\d{3})", "$1******$3");
            formattedMessage = formattedMessage.replaceAll("(passportSeries=)(\\d{2})(\\d{2})", "$1**$3");
            formattedMessage = formattedMessage.replaceAll("(passportNumber=)(\\d{3})(\\d{3})", "$1***$3");
            formattedMessage = formattedMessage.replaceAll("(account=\\d{4})(\\d{12})(\\d{4})", "$1************$3");
            formattedMessage = formattedMessage.replaceAll("(lastName=.)([^,]*)([^,],)", "$1***$3");

            formattedMessage = formattedMessage.replaceAll("(\"employerInn\"\\s*:\\s*\"\\d{3})(\\d{6})(\\d{3}\")", "$1******$3");
            formattedMessage = formattedMessage.replaceAll("(\"passportSeries\"\\s*:\\s*\")(\\d{2})(\\d{2}\")", "$1**$3");
            formattedMessage = formattedMessage.replaceAll("(\"passportNumber\"\\s*:\\s*\")(\\d{3})(\\d{3}\")", "$1***$3");
            formattedMessage = formattedMessage.replaceAll("(\"account\"\\s*:\\s*\"\\d{4})(\\d{12})(\\d{4}\")", "$1************$3");
            formattedMessage = formattedMessage.replaceAll("(\"lastName\"\\s*:\\s*\".)([^\"]*)([^\"]\")", "$1***$3");

            Log4jLogEvent.Builder builder = new Log4jLogEvent.Builder(event);
            builder.setMessage(new SimpleMessage(formattedMessage));

            return builder.build();
        } catch (Throwable t) {
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
package kalbot.bot.utils;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DateUtils {

    public LocalDateTime convertToDate(String message) {
        return LocalDateTime.of(
                Integer.parseInt(message.substring(message.length() - 4)),
                Integer.parseInt(message.substring(message.indexOf("/") + 1, message.lastIndexOf("/"))),
                Integer.parseInt(message.substring(message.indexOf(" ") + 1, message.indexOf("/"))),
                Integer.parseInt(message.substring(0, message.indexOf(":"))),
                Integer.parseInt(message.substring(message.indexOf(":") + 1, message.lastIndexOf(" ")))
        );
    }

    public String convertDateToString(LocalDateTime date) {
        return new StringBuilder()
                .append(date.getHour())
                .append(":")
                .append(date.getMinute())
                .append(" ")
                .append(date.getDayOfMonth())
                .append("/")
                .append(date.getMonthValue())
                .append("/")
                .append(date.getYear())
                .toString();
    }
}

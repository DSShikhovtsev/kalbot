package kalbot.bot.utils;

import kalbot.bot.BotState;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class BotUtils {

    public BotState getBotState(String message) {
        return Arrays.stream(BotState.values()).filter(t -> t.getText().equals(message)).findFirst().orElse(null);
    }
}

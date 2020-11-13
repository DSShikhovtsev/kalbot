package kalbot.bot;

import kalbot.bot.facade.TelegramFacade;
import kalbot.bot.properties.BotProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
@Setter
@Slf4j
@Component
public class TTBot extends TelegramWebhookBot {

    private final String botUsername;
    private final String botToken;
    private final String botPath;

    private final TelegramFacade telegramFacade;

    public TTBot(TelegramFacade telegramFacade, BotProperties properties) {
        this.telegramFacade = telegramFacade;
        this.botUsername = properties.getUsername();
        this.botToken = properties.getBotToken();
        this.botPath = properties.getWebHookPath();
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return telegramFacade.handleMessage(update);
    }
}

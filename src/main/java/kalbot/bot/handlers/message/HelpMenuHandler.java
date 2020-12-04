package kalbot.bot.handlers.message;

import kalbot.bot.BotState;
import kalbot.bot.handlers.InputMessageHandler;
import kalbot.bot.handlers.service.MainMenuService;
import kalbot.bot.service.ReplyMessageService;
import kalbot.bot.utils.Emojis;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class HelpMenuHandler implements InputMessageHandler {

    private final MainMenuService mainMenuService;
    private final ReplyMessageService replyMessageService;

    public HelpMenuHandler(MainMenuService mainMenuService, ReplyMessageService replyMessageService) {
        this.mainMenuService = mainMenuService;
        this.replyMessageService = replyMessageService;
    }

    @Override
    public SendMessage handle(Message message) {
        return mainMenuService.getMessage(message.getChatId(),
                replyMessageService.getEmojiReplyText("reply.helpMenu.message", Emojis.HELP_MENU_WELCOME));
    }

    @Override
    public BotState getStateForHandling() {
        return BotState.SHOW_HELP_MENU;
    }
}

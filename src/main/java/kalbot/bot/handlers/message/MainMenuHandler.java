package kalbot.bot.handlers.message;

import kalbot.bot.BotState;
import kalbot.bot.handlers.InputMessageHandler;
import kalbot.bot.handlers.service.MainMenuService;
import kalbot.bot.service.ReplyMessageService;
import kalbot.bot.utils.Emojis;
import kalbot.domain.UserState;
import kalbot.service.userstate.UserStateService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDateTime;

@Component
public class MainMenuHandler implements InputMessageHandler {

    private final MainMenuService mainMenuService;
    private final ReplyMessageService replyMessageService;
    private final UserStateService userStateService;

    public MainMenuHandler(MainMenuService mainMenuService, ReplyMessageService replyMessageService, UserStateService userStateService) {
        this.mainMenuService = mainMenuService;
        this.replyMessageService = replyMessageService;
        this.userStateService = userStateService;
    }

    @Override
    public SendMessage handle(Message message) {
        UserState userState = userStateService.getByChatId(Long.valueOf(message.getFrom().getId()));
        if (userState == null) {
            userState = new UserState(Long.valueOf(message.getFrom().getId()));
        }
        userState.setState(BotState.SHOW_MAIN_MENU);

        userStateService.save(userState);
        return mainMenuService.getMessage(message.getChatId(),
                replyMessageService.getEmojiReplyText("reply.mainMenu.message", Emojis.HELLO));
    }

    @Override
    public BotState getStateForHandling() {
        return BotState.SHOW_MAIN_MENU;
    }
}

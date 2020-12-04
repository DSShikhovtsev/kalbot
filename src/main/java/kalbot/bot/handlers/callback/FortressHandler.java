package kalbot.bot.handlers.callback;

import kalbot.bot.BotState;
import kalbot.bot.handlers.InputCallbackHandler;
import kalbot.bot.handlers.service.GlobalTasteBotService;
import kalbot.bot.service.ReplyMessageService;
import kalbot.bot.utils.Emojis;
import kalbot.domain.UserState;
import kalbot.exceptions.BotException;
import kalbot.service.fortress.FortressService;
import kalbot.service.userstate.UserStateService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public class FortressHandler implements InputCallbackHandler {

    private final ReplyMessageService replyMessageService;
    private final UserStateService userStateService;
    private final FortressService fortressService;
    private final GlobalTasteBotService globalTasteBotService;

    public FortressHandler(ReplyMessageService replyMessageService, UserStateService userStateService, FortressService fortressService, GlobalTasteBotService globalTasteBotService) {
        this.replyMessageService = replyMessageService;
        this.userStateService = userStateService;
        this.fortressService = fortressService;
        this.globalTasteBotService = globalTasteBotService;
    }

    @Override
    public SendMessage handle(CallbackQuery callbackQuery) {
        UserState userState = userStateService.getByChatId(Long.valueOf(callbackQuery.getFrom().getId()));
        if (userState == null) {
            throw new BotException();
        }
        userState.setState(BotState.GLOBAL_TASTE);
        userState.getKalian().setFortressId(fortressService.getByScore(Integer.valueOf(callbackQuery.getData())).getId());
        userStateService.save(userState);
        return globalTasteBotService.getMessage(Long.valueOf(callbackQuery.getFrom().getId()), userState.getKalian().getFortressId(),
                replyMessageService.getEmojiReplyText("reply.taste.global", Emojis.TASTE_GLOBAL));
    }

    @Override
    public BotState getStateForHandling() {
        return BotState.FORTRESS;
    }
}

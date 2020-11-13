package kalbot.bot.handlers.callback;

import kalbot.bot.BotState;
import kalbot.bot.handlers.InputCallbackHandler;
import kalbot.bot.handlers.service.TasteBotService;
import kalbot.bot.service.ReplyMessageService;
import kalbot.bot.utils.Emojis;
import kalbot.domain.UserState;
import kalbot.service.globaltaste.GlobalTasteService;
import kalbot.service.userstate.UserStateService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public class TasteHandler implements InputCallbackHandler {

    private final ReplyMessageService replyMessageService;
    private final UserStateService userStateService;
    private final TasteBotService tasteBotService;
    private final GlobalTasteService globalTasteService;

    public TasteHandler(ReplyMessageService replyMessageService, UserStateService userStateService, TasteBotService tasteBotService, GlobalTasteService globalTasteService) {
        this.replyMessageService = replyMessageService;
        this.userStateService = userStateService;
        this.tasteBotService = tasteBotService;
        this.globalTasteService = globalTasteService;
    }

    @Override
    public SendMessage handle(CallbackQuery callbackQuery) {
        UserState userState = userStateService.getByChatId(Long.valueOf(callbackQuery.getFrom().getId()));
        if (userState != null) {
            userState.setState(BotState.TASTE_CHOICE.getText());
            userState.setGlobalTasteId(globalTasteService.getById(Long.parseLong(callbackQuery.getData())).getId());
            userStateService.save(userState);
        }
        return tasteBotService.getMessage(Long.valueOf(callbackQuery.getFrom().getId()), callbackQuery.getData(),
                userState.getKalian().getFortressId(),
                replyMessageService.getEmojiReplyText("reply.taste", Emojis.TASTE));
    }

    @Override
    public BotState getHandlerName() {
        return BotState.GLOBAL_TASTE;
    }
}

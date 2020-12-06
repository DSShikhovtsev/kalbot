package kalbot.bot.handlers.callback;

import kalbot.bot.BotState;
import kalbot.bot.handlers.InputCallbackHandler;
import kalbot.bot.handlers.service.GlobalTasteBotService;
import kalbot.bot.handlers.service.TasteMessageService;
import kalbot.bot.service.ReplyMessageService;
import kalbot.bot.utils.Emojis;
import kalbot.domain.UserState;
import kalbot.exceptions.BotException;
import kalbot.service.globaltaste.GlobalTasteService;
import kalbot.service.userstate.UserStateService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public class GlobalTasteHandler implements InputCallbackHandler {

    private final ReplyMessageService replyMessageService;
    private final UserStateService userStateService;
    private final TasteMessageService tasteMessageService;
    private final GlobalTasteService globalTasteService;
    private final GlobalTasteBotService globalTasteBotService;

    public GlobalTasteHandler(ReplyMessageService replyMessageService, UserStateService userStateService, TasteMessageService tasteMessageService, GlobalTasteService globalTasteService, GlobalTasteBotService globalTasteBotService) {
        this.replyMessageService = replyMessageService;
        this.userStateService = userStateService;
        this.tasteMessageService = tasteMessageService;
        this.globalTasteService = globalTasteService;
        this.globalTasteBotService = globalTasteBotService;
    }

    @Override
    public SendMessage handle(CallbackQuery callbackQuery) {
        UserState userState = userStateService.getByChatId(Long.valueOf(callbackQuery.getFrom().getId()));
        if (userState == null) {
            throw new BotException();
        }
        userState.setState(BotState.TASTE_CHOICE);
        userState.setGlobalTasteId(globalTasteService.getById(Long.parseLong(callbackQuery.getData())).getId());
        userStateService.save(userState);
        return tasteMessageService.getMessage(Long.valueOf(callbackQuery.getFrom().getId()), callbackQuery.getData(),
                userState.getKalian().getFortressId(),
                replyMessageService.getEmojiReplyText("reply.taste", Emojis.TASTE));
    }

    @Override
    public SendMessage handleLastMessage(BotApiObject botApiObject) {
        CallbackQuery callbackQuery = (CallbackQuery) botApiObject;
        UserState userState = userStateService.getByChatId(Long.valueOf(callbackQuery.getFrom().getId()));
        if (userState == null) {
            throw new BotException();
        }
        return globalTasteBotService.getMessage(Long.valueOf(callbackQuery.getFrom().getId()), userState.getKalian().getFortressId(),
                replyMessageService.getEmojiReplyText("reply.taste.global", Emojis.TASTE_GLOBAL));
    }

    @Override
    public BotState getStateForHandling() {
        return BotState.GLOBAL_TASTE;
    }
}

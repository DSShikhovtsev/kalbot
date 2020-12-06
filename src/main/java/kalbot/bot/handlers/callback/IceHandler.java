package kalbot.bot.handlers.callback;

import kalbot.bot.BotState;
import kalbot.bot.handlers.InputCallbackHandler;
import kalbot.bot.handlers.service.FinishBotService;
import kalbot.bot.handlers.service.IceBotService;
import kalbot.bot.service.ReplyMessageService;
import kalbot.bot.utils.Emojis;
import kalbot.domain.UserState;
import kalbot.exceptions.BotException;
import kalbot.service.userstate.UserStateService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public class IceHandler implements InputCallbackHandler {

    private final ReplyMessageService replyMessageService;
    private final UserStateService userStateService;
    private final FinishBotService finishBotService;
    private final IceBotService iceBotService;

    public IceHandler(ReplyMessageService replyMessageService, UserStateService userStateService, FinishBotService finishBotService, IceBotService iceBotService) {
        this.replyMessageService = replyMessageService;
        this.userStateService = userStateService;
        this.finishBotService = finishBotService;
        this.iceBotService = iceBotService;
    }

    @Override
    public SendMessage handle(CallbackQuery callbackQuery) {
        UserState userState = userStateService.getByChatId(Long.valueOf(callbackQuery.getFrom().getId()));
        if (userState == null) {
            throw new BotException();
        }
        userState.setState(BotState.FINISH);
        if (callbackQuery.getData().contains("yes")) userState.getKalian().setIce(true);
        else userState.getKalian().setIce(false);
        userStateService.save(userState);
        return finishBotService.getMessage(Long.valueOf(callbackQuery.getFrom().getId()),
                replyMessageService.getEmojiReplyText("reply.finish", Emojis.FINISH));
    }

    @Override
    public SendMessage handleLastMessage(BotApiObject botApiObject) {
        return iceBotService.getMessage(Long.valueOf(((CallbackQuery) botApiObject).getFrom().getId()),
                replyMessageService.getEmojiReplyText("reply.ice", Emojis.ICE));
    }

    @Override
    public BotState getStateForHandling() {
        return BotState.ICE;
    }
}

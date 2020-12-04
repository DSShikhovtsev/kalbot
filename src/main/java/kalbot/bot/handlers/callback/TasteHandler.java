package kalbot.bot.handlers.callback;

import kalbot.bot.BotState;
import kalbot.bot.handlers.InputCallbackHandler;
import kalbot.bot.handlers.service.BrandBotService;
import kalbot.bot.handlers.service.FinishBotService;
import kalbot.bot.handlers.service.IceBotService;
import kalbot.bot.service.ReplyMessageService;
import kalbot.bot.utils.Emojis;
import kalbot.domain.UserState;
import kalbot.exceptions.BotException;
import kalbot.service.userstate.UserStateService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public class TasteHandler implements InputCallbackHandler {

    private final ReplyMessageService replyMessageService;
    private final UserStateService userStateService;
    private final BrandBotService brandBotService;
    private final IceBotService iceBotService;

    public TasteHandler(ReplyMessageService replyMessageService, UserStateService userStateService, BrandBotService brandBotService, IceBotService iceBotService) {
        this.replyMessageService = replyMessageService;
        this.userStateService = userStateService;
        this.brandBotService = brandBotService;
        this.iceBotService = iceBotService;
    }

    @Override
    public SendMessage handle(CallbackQuery callbackQuery) {
        UserState userState = userStateService.getByChatId(Long.valueOf(callbackQuery.getFrom().getId()));
        if (userState == null) {
            throw new BotException();
        }

        if (userState.getTastes().size() > 1) {
            userState.setState(BotState.BRAND_CHOICE);
        } else userState.setState(BotState.BRAND);

        brandBotService.addTobacco(userState, callbackQuery);
        userStateService.save(userState);

        if (userState.getTastes().size() <= 1)
            return iceBotService.getMessage(Long.valueOf(callbackQuery.getFrom().getId()),
                    replyMessageService.getEmojiReplyText("reply.ice", Emojis.ICE));
        return brandBotService.getCutMessage(Long.valueOf(callbackQuery.getFrom().getId()),
                replyMessageService.getEmojiReplyText("reply.brand", Emojis.TOBACCO_BRAND));
    }

    @Override
    public BotState getStateForHandling() {
        return BotState.TASTE;
    }
}

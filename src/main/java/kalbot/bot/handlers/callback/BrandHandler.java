package kalbot.bot.handlers.callback;

import kalbot.bot.BotState;
import kalbot.bot.handlers.InputCallbackHandler;
import kalbot.bot.handlers.service.BrandBotService;
import kalbot.bot.handlers.service.FinishBotService;
import kalbot.bot.handlers.service.IceBotService;
import kalbot.bot.service.ReplyMessageService;
import kalbot.bot.utils.Emojis;
import kalbot.domain.UserState;
import kalbot.service.userstate.UserStateService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public class BrandHandler implements InputCallbackHandler {

    private final ReplyMessageService replyMessageService;
    private final UserStateService userStateService;
    private final BrandBotService brandBotService;
    private final IceBotService iceBotService;

    public BrandHandler(ReplyMessageService replyMessageService, UserStateService userStateService, BrandBotService brandBotService, IceBotService iceBotService) {
        this.replyMessageService = replyMessageService;
        this.userStateService = userStateService;
        this.brandBotService = brandBotService;
        this.iceBotService = iceBotService;
    }

    @Override
    public SendMessage handle(CallbackQuery callbackQuery) {
        UserState userState = userStateService.getByChatId(Long.valueOf(callbackQuery.getFrom().getId()));
        if (userState != null) {
            if (userState.getTastes().size() > 1) {
                userState.setState(BotState.BRAND_CHOICE.getText());
            } else userState.setState(BotState.BRAND.getText());
            brandBotService.addTobacco(userState, callbackQuery);
            userStateService.save(userState);
        if (userState.getTastes().size() <= 1)
            return iceBotService.getMessage(Long.valueOf(callbackQuery.getFrom().getId()),
                    replyMessageService.getEmojiReplyText("reply.ice", Emojis.ICE));
        }
        return brandBotService.getCutMessage(Long.valueOf(callbackQuery.getFrom().getId()),
                replyMessageService.getEmojiReplyText("reply.brand", Emojis.TOBACCO_BRAND));
    }

    @Override
    public BotState getHandlerName() {
        return BotState.TASTE;
    }
}

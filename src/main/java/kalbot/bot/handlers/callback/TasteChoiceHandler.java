package kalbot.bot.handlers.callback;

import kalbot.bot.BotState;
import kalbot.bot.handlers.InputCallbackHandler;
import kalbot.bot.handlers.service.BrandBotService;
import kalbot.bot.handlers.service.TasteBotService;
import kalbot.bot.service.ReplyMessageService;
import kalbot.bot.utils.Emojis;
import kalbot.domain.UserState;
import kalbot.service.taste.TasteService;
import kalbot.service.userstate.UserStateService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public class TasteChoiceHandler implements InputCallbackHandler {

    private final ReplyMessageService replyMessageService;
    private final UserStateService userStateService;
    private final TasteService tasteService;
    private final TasteBotService tasteBotService;
    private final BrandBotService brandBotService;

    public TasteChoiceHandler(ReplyMessageService replyMessageService, UserStateService userStateService, TasteService tasteService, TasteBotService tasteBotService, BrandBotService brandBotService) {
        this.replyMessageService = replyMessageService;
        this.userStateService = userStateService;
        this.tasteService = tasteService;
        this.tasteBotService = tasteBotService;
        this.brandBotService = brandBotService;
    }

    @Override
    public SendMessage handle(CallbackQuery callbackQuery) {
        UserState userState = userStateService.getByChatId(Long.valueOf(callbackQuery.getFrom().getId()));
        if (userState != null) {
            if (callbackQuery.getData().equals("endTaste")) {
                userState.setState(BotState.TASTE.getText());
            } else if (callbackQuery.getData().contains("endTaste")) {
                userState.setState(BotState.TASTE.getText());
                userState.getTastes().add(tasteService.getById(
                        Long.valueOf(callbackQuery.getData().substring(0, callbackQuery.getData().lastIndexOf("|")))));
            } else {
                userState.getTastes().add(tasteService.getById(Long.valueOf(callbackQuery.getData())));
            }
        }
        userStateService.save(userState);
        if (callbackQuery.getData().contains("endTaste"))
            return brandBotService.getMessage(Long.valueOf(callbackQuery.getFrom().getId()),
                    replyMessageService.getEmojiReplyText("reply.brand", Emojis.TOBACCO_BRAND));
        return tasteBotService.getCutMessage(Long.valueOf(callbackQuery.getFrom().getId()),
                userState.getKalian().getFortressId(),
                userState.getGlobalTasteId(),
                userState.getTastes(),
                replyMessageService.getEmojiReplyText("reply.taste.more", Emojis.ANYMORE));
    }

    @Override
    public BotState getHandlerName() {
        return BotState.TASTE_CHOICE;
    }
}

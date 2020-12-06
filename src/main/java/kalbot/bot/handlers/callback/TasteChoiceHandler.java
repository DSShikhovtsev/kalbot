package kalbot.bot.handlers.callback;

import kalbot.bot.BotState;
import kalbot.bot.handlers.InputCallbackHandler;
import kalbot.bot.handlers.service.BrandBotService;
import kalbot.bot.handlers.service.TasteMessageService;
import kalbot.bot.service.ReplyMessageService;
import kalbot.bot.utils.Emojis;
import kalbot.domain.UserState;
import kalbot.exceptions.BotException;
import kalbot.service.taste.TasteService;
import kalbot.service.userstate.UserStateService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public class TasteChoiceHandler implements InputCallbackHandler {

    private final ReplyMessageService replyMessageService;
    private final UserStateService userStateService;
    private final TasteService tasteService;
    private final TasteMessageService tasteMessageService;
    private final BrandBotService brandBotService;

    public TasteChoiceHandler(ReplyMessageService replyMessageService, UserStateService userStateService, TasteService tasteService, TasteMessageService tasteMessageService, BrandBotService brandBotService) {
        this.replyMessageService = replyMessageService;
        this.userStateService = userStateService;
        this.tasteService = tasteService;
        this.tasteMessageService = tasteMessageService;
        this.brandBotService = brandBotService;
    }

    @Override
    public SendMessage handle(CallbackQuery callbackQuery) {
        UserState userState = userStateService.getByChatId(Long.valueOf(callbackQuery.getFrom().getId()));
        if (userState == null) {
            throw new BotException();
        }
        if (callbackQuery.getData().equals("endTaste")) {
            userState.setState(BotState.BRAND_CHOICE);
        } else if (callbackQuery.getData().contains("endTaste")) {
            userState.setState(BotState.BRAND_CHOICE);
            userState.getTastes().add(tasteService.getById(
                    Long.valueOf(callbackQuery.getData().substring(0, callbackQuery.getData().lastIndexOf("|")))));
        } else {
            userState.getTastes().add(tasteService.getById(Long.valueOf(callbackQuery.getData())));
        }
        userStateService.save(userState);
        if (callbackQuery.getData().contains("endTaste"))
            return brandBotService.getMessage(Long.valueOf(callbackQuery.getFrom().getId()),
                    replyMessageService.getEmojiReplyText("reply.brand", Emojis.TOBACCO_BRAND));
        return tasteMessageService.getCutMessage(Long.valueOf(callbackQuery.getFrom().getId()),
                userState.getKalian().getFortressId(),
                userState.getGlobalTasteId(),
                userState.getTastes(),
                replyMessageService.getEmojiReplyText("reply.taste.more", Emojis.ANYMORE));
    }

    @Override
    public SendMessage handleLastMessage(BotApiObject botApiObject) {
        CallbackQuery callbackQuery = (CallbackQuery) botApiObject;
        UserState userState = userStateService.getByChatId(Long.valueOf(callbackQuery.getFrom().getId()));
        if (userState == null) {
            throw new BotException();
        }
        if (userState.getTastes().size() == 0) {
            return tasteMessageService.getMessage(Long.valueOf(callbackQuery.getFrom().getId()), callbackQuery.getData(),
                    userState.getKalian().getFortressId(),
                    replyMessageService.getEmojiReplyText("reply.taste", Emojis.TASTE));
        }
        return tasteMessageService.getCutMessage(Long.valueOf(callbackQuery.getFrom().getId()),
                userState.getKalian().getFortressId(),
                userState.getGlobalTasteId(),
                userState.getTastes(),
                replyMessageService.getEmojiReplyText("reply.taste.more", Emojis.ANYMORE));
    }

    @Override
    public BotState getStateForHandling() {
        return BotState.TASTE_CHOICE;
    }
}

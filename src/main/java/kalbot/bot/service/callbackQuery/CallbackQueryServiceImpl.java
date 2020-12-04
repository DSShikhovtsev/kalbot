package kalbot.bot.service.callbackQuery;

import kalbot.bot.BotState;
import kalbot.bot.handlers.InputCallbackHandler;
import kalbot.bot.handlers.service.MainMenuService;
import kalbot.bot.service.ReplyMessageService;
import kalbot.bot.utils.BotUtils;
import kalbot.bot.utils.Emojis;
import kalbot.domain.UserState;
import kalbot.service.userstate.UserStateService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CallbackQueryServiceImpl implements CallbackQueryService {

    private final UserStateService userStateService;
    private final BotUtils botUtils;
    private final MainMenuService mainMenuService;
    private final ReplyMessageService replyMessageService;
    private final Map<BotState, InputCallbackHandler> callbackHandlers = new HashMap<>();

    public CallbackQueryServiceImpl(List<InputCallbackHandler> callbackHandlers, UserStateService userStateService, BotUtils botUtils, MainMenuService mainMenuService, ReplyMessageService replyMessageService) {
        this.userStateService = userStateService;
        this.botUtils = botUtils;
        this.mainMenuService = mainMenuService;
        this.replyMessageService = replyMessageService;
        callbackHandlers.forEach(handler -> this.callbackHandlers.put(handler.getStateForHandling(), handler));
    }

    @Override
    public SendMessage processCallbackQuery(CallbackQuery callbackQuery) {
        Long userId = Long.valueOf(callbackQuery.getFrom().getId());
        UserState userState = userStateService.getByChatId(userId);
        InputCallbackHandler handler = findHandler(botUtils.getBotState(userState.getState()));
        if (callbackQuery.getData().equals("continue")) {
            return handler.handleLastMessage(callbackQuery);
        } else if (callbackQuery.getData().equals("exit")) {
            return mainMenuService.getMessage(Long.valueOf(callbackQuery.getFrom().getId()),
                    replyMessageService.getEmojiReplyText("reply.mainMenu.message", Emojis.HELLO));
        } else {
            return handler.handle(callbackQuery);
        }
    }

    private InputCallbackHandler findHandler(BotState state) {
        return callbackHandlers.get(state);
    }
}

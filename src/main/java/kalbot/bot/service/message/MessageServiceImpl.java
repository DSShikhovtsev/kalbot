package kalbot.bot.service.message;

import kalbot.bot.BotState;
import kalbot.bot.handlers.InputMessageHandler;
import kalbot.bot.utils.BotUtils;
import kalbot.domain.UserState;
import kalbot.exceptions.BotException;
import kalbot.exceptions.StateException;
import kalbot.service.userstate.UserStateService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService {

    private final UserStateService userStateService;
    private final BotUtils botUtils;
    private Map<BotState, InputMessageHandler> messageHandlers = new HashMap<>();

    public MessageServiceImpl(List<InputMessageHandler> messageHandlers, UserStateService userStateService, BotUtils botUtils) {
        this.userStateService = userStateService;
        this.botUtils = botUtils;
        messageHandlers.forEach(handler -> this.messageHandlers.put(handler.getHandlerName(), handler));
    }

    @Override
    public SendMessage handleMessage(Message message) throws BotException {
        Long userId = Long.valueOf(message.getFrom().getId());
        BotState state = botUtils.getBotState(message.getText());
        if (state == null) {
            UserState userState = userStateService.getByChatId(userId);
            if (userState == null || userState.getState() == null
                    || userState.getState().isEmpty()) {
                state = BotState.SHOW_MAIN_MENU;
            } else {
                state = botUtils.getBotState(userState.getState());
            }
        }
        InputMessageHandler handler = findHandler(state);
        if (handler == null) handler = mistakeHandler();
        return handler.handle(message);
    }

    private InputMessageHandler findHandler(BotState state) {
        return messageHandlers.get(state);
    }

    private InputMessageHandler mistakeHandler() {
        return messageHandlers.get(BotState.MISTAKE);
    }
}

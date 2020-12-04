package kalbot.bot.service.message;

import kalbot.bot.BotState;
import kalbot.bot.handlers.InputMessageHandler;
import kalbot.bot.utils.BotUtils;
import kalbot.domain.UserState;
import kalbot.exceptions.BotException;
import kalbot.service.userstate.UserStateService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    private final UserStateService userStateService;
    private final BotUtils botUtils;
    private final Map<BotState, InputMessageHandler> messageHandlers = new HashMap<>();

    public MessageServiceImpl(List<InputMessageHandler> messageHandlers, UserStateService userStateService, BotUtils botUtils) {
        this.userStateService = userStateService;
        this.botUtils = botUtils;
        messageHandlers.forEach(handler -> this.messageHandlers.put(handler.getStateForHandling(), handler));
    }

    @Override
    public SendMessage handleMessage(Message message) throws BotException {
        Long userId = Long.valueOf(message.getFrom().getId());
        BotState state = botUtils.getBotState(message.getText());
        if (state == null) {
            state = Optional.ofNullable(userStateService.getByChatId(userId))
                    .map(UserState::getState)
                    .orElse(BotState.SHOW_MAIN_MENU);
        }
        return findHandler(state).handle(message);
    }

    private InputMessageHandler findHandler(BotState state) {
        return messageHandlers.getOrDefault(state, messageHandlers.get(BotState.MISTAKE));
    }

}

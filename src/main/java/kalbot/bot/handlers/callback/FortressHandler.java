package kalbot.bot.handlers.callback;

import kalbot.bot.BotState;
import kalbot.bot.handlers.InputMessageHandler;
import kalbot.bot.handlers.service.FortressBotService;
import kalbot.bot.service.ReplyMessageService;
import kalbot.bot.utils.Emojis;
import kalbot.domain.Kalian;
import kalbot.domain.UserState;
import kalbot.service.kalian.KalianService;
import kalbot.service.userstate.UserStateService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
public class FortressHandler implements InputMessageHandler {

    private final ReplyMessageService replyMessageService;
    private final FortressBotService fortressBotService;
    private final UserStateService userStateService;
    private final KalianService kalianService;

    public FortressHandler(ReplyMessageService replyMessageService, FortressBotService fortressBotService, UserStateService userStateService, KalianService kalianService) {
        this.replyMessageService = replyMessageService;
        this.fortressBotService = fortressBotService;
        this.userStateService = userStateService;
        this.kalianService = kalianService;
    }

    @Override
    public SendMessage handle(Message message) {
        UserState userState = userStateService.getByChatId(Long.valueOf(message.getFrom().getId()));
        if (userState != null) {
            userState.setState(BotState.FORTRESS.getText());
        } else {
            userState = new UserState(Long.valueOf(message.getFrom().getId()), BotState.FORTRESS.getText());
        }
        userState.setTastes(new ArrayList<>());
        userState.setKalian(initKalian(Long.valueOf(message.getFrom().getId())));
        userStateService.save(userState);
        return fortressBotService.getMessage(message.getChatId(),
                replyMessageService.getEmojiReplyText("reply.fortress", Emojis.FORTRESS));
    }

    @Override
    public SendMessage handleLastMessage(BotApiObject botApiObject) {
        return null;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.START_WORK;
    }

    private Kalian initKalian(Long chatId) {
        Kalian kalian = new Kalian();
        kalian.setUserChatId(chatId);
        kalian.setDate(LocalDateTime.now());
        kalian.setTobaccos(new ArrayList<>());
        return kalianService.save(kalian);
    }
}

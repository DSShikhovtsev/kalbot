package kalbot.bot.handlers.message;

import kalbot.bot.BotState;
import kalbot.bot.handlers.InputMessageHandler;
import kalbot.bot.handlers.service.MistakeMessageBotService;
import kalbot.bot.service.ReplyMessageService;
import kalbot.bot.utils.Emojis;
import kalbot.exceptions.BotException;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class MistakeMessageHandler implements InputMessageHandler {

    private final ReplyMessageService replyMessageService;
    private final MistakeMessageBotService mistakeMessageBotService;

    public MistakeMessageHandler(ReplyMessageService replyMessageService, MistakeMessageBotService mistakeMessageBotService) {
        this.replyMessageService = replyMessageService;
        this.mistakeMessageBotService = mistakeMessageBotService;
    }

    @Override
    public SendMessage handle(Message message) throws BotException {
        return mistakeMessageBotService.getMistakeMessage(Long.valueOf(message.getFrom().getId()),
                        replyMessageService.getEmojiReplyText("reply.mistake", Emojis.STOP));
    }

    @Override
    public SendMessage handleLastMessage(BotApiObject botApiObject) {
        return null;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.MISTAKE;
    }
}

package kalbot.bot.handlers.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class TimeManagementBotService {

    public SendMessage getFirstDateChooseMessage(Long chatId, String message) {
        return createMessage(chatId, message);
    }

    private SendMessage createMessage(Long chatId, String message) {
        SendMessage reply = new SendMessage();
        reply.setChatId(chatId);
        reply.setText(message);
        reply.enableMarkdown(true);
        return reply;
    }
}

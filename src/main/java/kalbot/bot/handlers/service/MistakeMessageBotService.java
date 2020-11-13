package kalbot.bot.handlers.service;

import kalbot.bot.utils.ReplyKeyboardUtil;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Service
public class MistakeMessageBotService {

    private final ReplyKeyboardUtil replyKeyboardUtil;

    public MistakeMessageBotService(ReplyKeyboardUtil replyKeyboardUtil) {
        this.replyKeyboardUtil = replyKeyboardUtil;
    }

    public SendMessage getMistakeMessage(Long chatId, String message) {
        return createMessage(chatId, message);
    }

    private SendMessage createMessage(Long chatId, String message) {
        SendMessage reply = new SendMessage();
        reply.setChatId(chatId);
        reply.setText(message);
        reply.enableMarkdown(true);
        reply.setReplyMarkup(getMenu());
        return reply;
    }

    private InlineKeyboardMarkup getMenu() {
        InlineKeyboardMarkup inlineKeyboardMarkup = replyKeyboardUtil.getInlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = replyKeyboardUtil
                .getTwoButtonsInRow("continue", "Продолжить", "exit", "Выход");
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }
}

package kalbot.bot.handlers.service;

import kalbot.bot.utils.ReplyKeyboardUtil;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
public class FortressBotService {

    private final ReplyKeyboardUtil keyboardUtil;

    public FortressBotService(ReplyKeyboardUtil keyboardUtil) {
        this.keyboardUtil = keyboardUtil;
    }

    public SendMessage getMessage(Long chatId, String message) {
        return createMessage(chatId, message);
    }

    private SendMessage createMessage(Long chatId, String message) {
        SendMessage reply = new SendMessage();
        reply.setChatId(chatId);
        reply.setText(message);
        reply.enableMarkdown(true);
        reply.setReplyMarkup(getMainMenuKeyboard());
        return reply;
    }

    private InlineKeyboardMarkup getMainMenuKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = keyboardUtil.getInlineKeyboardMarkup();

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        addButtons(1, 6, row1);
        addButtons(6, 11, row2);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(row1);
        rowList.add(row2);

        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    private void addButtons(int from, int to, List<InlineKeyboardButton> list) {
        InlineKeyboardButton button;
        for (int i = from; i < to; i++) {
            button = new InlineKeyboardButton().setText(String.valueOf(i));
            button.setCallbackData(String.valueOf(i));
            list.add(button);
        }
    }
}

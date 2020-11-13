package kalbot.bot.handlers.service;

import kalbot.bot.utils.ReplyKeyboardUtil;
import kalbot.domain.GlobalTaste;
import kalbot.service.globaltaste.GlobalTasteService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
public class GlobalTasteBotService {

    private final ReplyKeyboardUtil replyKeyboardUtil;
    private final GlobalTasteService globalTasteService;

    public GlobalTasteBotService(ReplyKeyboardUtil replyKeyboardUtil, GlobalTasteService globalTasteService) {
        this.replyKeyboardUtil = replyKeyboardUtil;
        this.globalTasteService = globalTasteService;
    }

    public SendMessage getMessage(Long chatId, Long fortressId, String emojiMessage) {
        return createMessage(chatId, fortressId, emojiMessage);
    }

    private SendMessage createMessage(Long chatId, Long fortressId, String message) {
        SendMessage reply = new SendMessage();
        reply.setChatId(chatId);
        reply.setText(message);
        reply.enableMarkdown(true);
        reply.setReplyMarkup(getMenu(fortressId));
        return reply;
    }

    private InlineKeyboardMarkup getMenu(Long fortressId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = replyKeyboardUtil.getInlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        List<GlobalTaste> tastes = globalTasteService.getAllWithTasteForFortress(fortressId);
        tastes.forEach(t -> addButtons(rowList, t));
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    private void addButtons(List<List<InlineKeyboardButton>> rowList, GlobalTaste taste) {
        List<InlineKeyboardButton> list = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(taste.getTaste());
        button.setCallbackData(String.valueOf(taste.getId()));
        list.add(button);
        rowList.add(list);
    }
}

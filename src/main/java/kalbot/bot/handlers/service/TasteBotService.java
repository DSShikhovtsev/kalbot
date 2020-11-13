package kalbot.bot.handlers.service;

import kalbot.bot.utils.Emojis;
import kalbot.bot.utils.ReplyKeyboardUtil;
import kalbot.domain.Fortress;
import kalbot.domain.Taste;
import kalbot.service.fortress.FortressService;
import kalbot.service.taste.TasteService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
public class TasteBotService {

    private final TasteService tasteService;
    private final ReplyKeyboardUtil keyboardUtil;

    public TasteBotService(TasteService tasteService, ReplyKeyboardUtil keyboardUtil) {
        this.tasteService = tasteService;
        this.keyboardUtil = keyboardUtil;
    }

    public SendMessage getMessage(Long chatId, String data, Long fortressId, String emojiMessage) {
        return createMessage(chatId, data, fortressId, emojiMessage);
    }

    public SendMessage getCutMessage(Long chatId, Long fortress, Long globalTaste, List<Taste> tastes, String emojiMessage) {
        return createCutMessage(chatId, fortress, globalTaste, tastes, emojiMessage);
    }

    private SendMessage createMessage(Long chatId, String data, Long fortressId, String emojiMessage) {
        SendMessage reply = new SendMessage();
        reply.setChatId(chatId);
        reply.setText(emojiMessage);
        reply.enableMarkdown(true);
        reply.setReplyMarkup(getTasteMenuData(fortressId, data));
        return reply;
    }

    private InlineKeyboardMarkup getTasteMenuData(Long fortressId, String data) {
        InlineKeyboardMarkup inlineKeyboardMarkup = keyboardUtil.getInlineKeyboardMarkup();
        Long globalTasteId = Long.parseLong(data);
        List<Taste> tastes = tasteService.getByFortressIdAndGlobalTasteId(fortressId, globalTasteId);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        if (tastes.size() == 1) {
            generateFinalButton(rowList, tastes.get(0));
        } else {
            rowList = convertTasteToButtons(tastes);
        }
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    private SendMessage createCutMessage(Long chatId, Long fortress, Long globalTaste, List<Taste> tastes, String emojiMessage) {
        SendMessage reply = new SendMessage();
        reply.setChatId(chatId);
        reply.setText(emojiMessage);
        reply.enableMarkdown(true);
        reply.setReplyMarkup(getCutTasteMenuData(tastes, fortress, globalTaste));
        return reply;
    }

    private InlineKeyboardMarkup getCutTasteMenuData(List<Taste> cut, Long fortress, Long globalTaste) {
        InlineKeyboardMarkup inlineKeyboardMarkup = keyboardUtil.getInlineKeyboardMarkup();
        List<Taste> tastes = tasteService.getByFortressIdAndGlobalTasteId(fortress, globalTaste);
        tastes.removeAll(cut);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        if (tastes.size() == 1) {
            generateFinalButton(rowList, tastes.get(0));
        } else {
            rowList = convertTasteToButtons(tastes);
        }
        addEndButton(rowList);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    private List<List<InlineKeyboardButton>> convertTasteToButtons(List<Taste> tastes) {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        tastes.forEach(t -> addButtons(rowList, t));
        return rowList;
    }

    private void addButtons(List<List<InlineKeyboardButton>> rowList, Taste taste) {
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(taste.getTaste() + " - " + taste.getDescription());
        button.setCallbackData(String.valueOf(taste.getId()));
        row.add(button);
        rowList.add(row);
    }

    private void addEndButton(List<List<InlineKeyboardButton>> rowList) {
        InlineKeyboardButton endButton = new InlineKeyboardButton().setCallbackData("endTaste");
        endButton.setText("Завершить выбор " + Emojis.END_OF_CHOICE.getEmoji());
        List<InlineKeyboardButton> end = new ArrayList<>();
        end.add(endButton);
        rowList.add(end);
    }

    private void generateFinalButton(List<List<InlineKeyboardButton>> rowList, Taste taste) {
        InlineKeyboardButton button = new InlineKeyboardButton().setCallbackData(taste.getId() + "|endTaste");
        button.setText(taste.getTaste() + " - " + taste.getDescription());
        List<InlineKeyboardButton> end = new ArrayList<>();
        end.add(button);
        rowList.add(end);
    }

    /*private ReplyKeyboardMarkup getMainMenuKeyboard(String emoji) {
        ReplyKeyboardMarkup replyKeyboardMarkup = keyboardUtil.getReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add(new KeyboardButton("Вернуться в меню " + emoji));
        keyboard.add(row);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    private String convertMessage(String message, String emojiMessage) {
        Integer fortress = Integer.parseInt(message);
        List<Taste> tastes = tasteService.getTasteByFortress(fortress);
        StringBuilder builder = new StringBuilder();
        builder.append(emojiMessage).append("\n");
        int i = 1;
        for (Taste taste : tastes) {
            builder.append(i)
                    .append(". ")
                    .append(taste.getTaste())
                    .append(": ")
                    .append(taste.getDescription())
                    .append("\n");
            i++;
        }
        return builder.toString();
    }*/
}

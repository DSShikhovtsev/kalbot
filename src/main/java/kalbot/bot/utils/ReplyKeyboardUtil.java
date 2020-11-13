package kalbot.bot.utils;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReplyKeyboardUtil {

    public ReplyKeyboardMarkup getReplyKeyboardMarkup() {
        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        return replyKeyboardMarkup;
    }

    public InlineKeyboardMarkup getInlineKeyboardMarkup() {
        return new InlineKeyboardMarkup();
    }

    public List<List<InlineKeyboardButton>> getTwoButtonsInRow(String firstCallback,
                                                               String firstText,
                                                               String secondCallback,
                                                               String secondText) {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton yes = new InlineKeyboardButton();
        InlineKeyboardButton no = new InlineKeyboardButton();
        yes.setCallbackData(firstCallback);
        yes.setText(firstText);
        no.setCallbackData(secondCallback);
        no.setText(secondText);
        row.add(yes);
        row.add(no);
        rowList.add(row);
        return rowList;
    }
}

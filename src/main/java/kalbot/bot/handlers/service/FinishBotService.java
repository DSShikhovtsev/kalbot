package kalbot.bot.handlers.service;

import kalbot.bot.utils.ReplyKeyboardUtil;
import kalbot.domain.Fortress;
import kalbot.domain.UserState;
import kalbot.service.fortress.FortressService;
import kalbot.service.userstate.UserStateService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
public class FinishBotService {

    private final UserStateService userStateService;
    private final ReplyKeyboardUtil replyKeyboardUtil;
    private final FortressService fortressService;

    public FinishBotService(UserStateService userStateService, ReplyKeyboardUtil replyKeyboardUtil, FortressService fortressService) {
        this.userStateService = userStateService;
        this.replyKeyboardUtil = replyKeyboardUtil;
        this.fortressService = fortressService;
    }

    public SendMessage getMessage(Long chatId, String message) {
        return createMessage(chatId, message);
    }

    public SendMessage getFinalMessage(Long chatId, String message) {
        return createFinishMessage(chatId, message);
    }

    private SendMessage createMessage(Long chatId, String message) {
        SendMessage reply = new SendMessage();
        reply.setChatId(chatId);
        reply.setText(generateMessage(message, chatId));
        reply.enableMarkdown(true);
        reply.setReplyMarkup(getMenu());
        return reply;
    }

    private SendMessage createFinishMessage(Long chatId, String message) {
        SendMessage reply = new SendMessage();
        reply.setChatId(chatId);
        reply.setText(message);
        reply.enableMarkdown(true);
        return reply;
    }

    private String generateMessage(String message, Long chatId) {
        UserState userState = userStateService.getByChatId(chatId);
        StringBuilder builder = new StringBuilder();
        builder.append(message);
        userState.getKalian().getTobaccos().forEach(t -> {
            builder.append("Вкус: ")
                    .append(t.getTaste().getTaste())
                    .append(" | Производитель: ")
                    .append(t.getBrand().getFirm())
                    .append("\n");
        });
        Fortress fortress = fortressService.getById(userState.getKalian().getFortressId());
        builder.append("Выбранная крепкость кальяна - ")
                .append(fortress.getScore())
//                .append(" (")
//                .append(fortress.getFortress())
//                .append(")")
                .append("\n");
        if (userState.getKalian().getIce())
            builder.append("Включен дополнительный холодок")
                    .append("\n");
        builder.append("Начать готовить ваш заказ?");
        return builder.toString();
    }

    private InlineKeyboardMarkup getMenu() {
        InlineKeyboardMarkup inlineKeyboardMarkup = replyKeyboardUtil.getInlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = replyKeyboardUtil
                .getTwoButtonsInRow("yes", "Да", "no", "Нет");
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        InlineKeyboardButton mb = new InlineKeyboardButton();
        mb.setText("К определенному времени");
        mb.setCallbackData("atTime");
        row2.add(mb);
        rowList.add(row2);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }
}

package kalbot.bot.facade;

import com.google.common.base.Strings;
import kalbot.bot.service.callbackQuery.CallbackQueryService;
import kalbot.bot.service.message.MessageService;
import kalbot.bot.utils.Emojis;
import kalbot.exceptions.BotException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Service
public class TelegramFacadeImpl implements TelegramFacade {

    private final CallbackQueryService callbackQueryService;
    private final MessageService messageService;

    public TelegramFacadeImpl(CallbackQueryService callbackQueryService, MessageService messageService) {
        this.callbackQueryService = callbackQueryService;
        this.messageService = messageService;
    }

    @Override
    public SendMessage handleMessage(Update update) {
        SendMessage reply = null;
        try {
            if (update.hasCallbackQuery()) {
                log.info("New Callback query from User: {} with Data: {}", update.getCallbackQuery().getFrom().getUserName(),
                        update.getCallbackQuery().getData());
                return callbackQueryService.processCallbackQuery(update.getCallbackQuery());
            }

            Message message = update.getMessage();
            if (message != null && message.hasText()) {
                log.info("New message from User:{}, chatId: {},  with text: {}",
                        message.getFrom().getUserName(), message.getChatId(), message.getText());
                reply = messageService.handleMessage(message);
            }
        } catch (BotException e) {
            reply = new SendMessage();
            reply.setChatId(update.getMessage() != null ? update.getMessage().getChatId() : update.getCallbackQuery().getFrom().getId());
            if (!Strings.isNullOrEmpty(e.getException())) {
                reply.setText(e.getException() + " " + Emojis.HELP_MENU_WELCOME.getEmoji());
            } else {
                reply.setText("Некорректная команда " + Emojis.HELP_MENU_WELCOME.getEmoji());
            }
            reply.enableMarkdown(true);
            e.printStackTrace();
        } catch (Exception e) {
            reply = new SendMessage();
            reply.setChatId(update.getMessage() != null ? update.getMessage().getChatId() : update.getCallbackQuery().getFrom().getId());
            reply.enableMarkdown(true);
            reply.setText("Воспользуйтесь главным меню, пожалуйста");
        }

        return reply;
    }
}

package kalbot.bot.service.callbackQuery;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface CallbackQueryService {
    SendMessage processCallbackQuery(CallbackQuery callbackQuery);
}

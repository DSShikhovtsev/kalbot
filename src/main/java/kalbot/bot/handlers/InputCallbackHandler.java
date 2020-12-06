package kalbot.bot.handlers;

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface InputCallbackHandler extends Handlers {

    SendMessage handleLastMessage(BotApiObject botApiObject);
    SendMessage handle(CallbackQuery callbackQuery);
}

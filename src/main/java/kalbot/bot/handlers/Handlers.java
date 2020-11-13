package kalbot.bot.handlers;

import kalbot.bot.BotState;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface Handlers {

//    SendMessage handleLastMessage(BotApiObject botApiObject);

    BotState getHandlerName();
}

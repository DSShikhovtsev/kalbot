package kalbot.bot.handlers;

import kalbot.exceptions.BotException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface InputMessageHandler extends Handlers {
    SendMessage handle(Message message) throws BotException;
}

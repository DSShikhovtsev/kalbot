package kalbot.bot.service.message;

import kalbot.exceptions.BotException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageService {
    SendMessage handleMessage(Message message) throws BotException;
}

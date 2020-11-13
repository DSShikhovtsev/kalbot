package kalbot.bot.facade;


import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface TelegramFacade {

    SendMessage handleMessage(Update update);
}

package kalbot.bot.handlers.message;

import kalbot.bot.BotState;
import kalbot.bot.handlers.InputMessageHandler;
import kalbot.bot.handlers.service.FinishBotService;
import kalbot.bot.service.ReplyMessageService;
import kalbot.bot.utils.DateUtils;
import kalbot.bot.utils.Emojis;
import kalbot.domain.UserState;
import kalbot.exceptions.BotException;
import kalbot.service.userstate.UserStateService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class TimeManagementHandler implements InputMessageHandler {

    private final ReplyMessageService replyMessageService;
    private final UserStateService userStateService;
    private final FinishBotService finishBotService;
    private final DateUtils dateUtils;

    public TimeManagementHandler(ReplyMessageService replyMessageService, UserStateService userStateService, FinishBotService finishBotService, DateUtils dateUtils) {
        this.replyMessageService = replyMessageService;
        this.userStateService = userStateService;
        this.finishBotService = finishBotService;
        this.dateUtils = dateUtils;
    }

    @Override
    public SendMessage handle(Message message) throws BotException {
        UserState userState = userStateService.getByChatId(Long.valueOf(message.getFrom().getId()));
        if (userState != null) {
            try {
                userState.getKalian().setDateTo(dateUtils.convertToDate(message.getText()));
            } catch (Exception e) {
                e.printStackTrace();
                throw new BotException("Некорректный формат даты");
            }
            userState.setState(BotState.FINISH_WORK.getText());
            userStateService.save(userState);
        }
        assert userState != null;
        return finishBotService.getFinalMessage(Long.valueOf(message.getFrom().getId()),
                replyMessageService.getEmojiReplyText("reply.finish.time.date", Emojis.POINT_RIGHT) +
                        dateUtils.convertDateToString(userState.getKalian().getDateTo()) +
                        replyMessageService.getEmojiReplyText("reply.finish.time.text", Emojis.KISSING) +
                        replyMessageService.getEmojiReplyText("reply.again", Emojis.AGAIN));
    }

    @Override
    public BotState getHandlerName() {
        return BotState.AT_TIME;
    }
}

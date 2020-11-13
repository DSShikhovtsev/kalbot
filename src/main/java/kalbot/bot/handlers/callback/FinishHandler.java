package kalbot.bot.handlers.callback;

import kalbot.bot.BotState;
import kalbot.bot.handlers.InputCallbackHandler;
import kalbot.bot.handlers.service.FinishBotService;
import kalbot.bot.handlers.service.TimeManagementBotService;
import kalbot.bot.service.ReplyMessageService;
import kalbot.bot.utils.DateUtils;
import kalbot.bot.utils.Emojis;
import kalbot.domain.UserState;
import kalbot.service.userstate.UserStateService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.time.LocalDateTime;

@Component
public class FinishHandler implements InputCallbackHandler {

    private final ReplyMessageService replyMessageService;
    private final UserStateService userStateService;
    private final FinishBotService finishBotService;
    private final TimeManagementBotService timeManagementBotService;
    private final DateUtils dateUtils;

    public FinishHandler(ReplyMessageService replyMessageService, UserStateService userStateService, FinishBotService finishBotService, TimeManagementBotService timeManagementBotService, DateUtils dateUtils) {
        this.replyMessageService = replyMessageService;
        this.userStateService = userStateService;
        this.finishBotService = finishBotService;
        this.timeManagementBotService = timeManagementBotService;
        this.dateUtils = dateUtils;
    }

    @Override
    public SendMessage handle(CallbackQuery callbackQuery) {
        UserState userState = userStateService.getByChatId(Long.valueOf(callbackQuery.getFrom().getId()));
        if (userState != null) {
            if (callbackQuery.getData().contains("atTime")) {
                userState.setState(BotState.AT_TIME.getText());
            } else userState.setState(BotState.FINISH_WORK.getText());
            userStateService.save(userState);
        }
        if (callbackQuery.getData().contains("yes")) {
            return finishBotService.getFinalMessage(Long.valueOf(callbackQuery.getFrom().getId()),
                    replyMessageService.getEmojiReplyText("reply.finish.yes", Emojis.FINISH_YES) +
                            replyMessageService.getEmojiReplyText("reply.again", Emojis.AGAIN));
        } else if (callbackQuery.getData().contains("atTime")) {
            return timeManagementBotService.getFirstDateChooseMessage(Long.valueOf(callbackQuery.getFrom().getId()),
                    replyMessageService.getEmojiReplyText("reply.atTime.start", Emojis.POINT_DOWN) +
                            "\n" + dateUtils.convertDateToString(LocalDateTime.now()) +
                            replyMessageService.getEmojiReplyText("reply.atTime.finish", Emojis.POINT_UP));
        } else
            return finishBotService.getFinalMessage(Long.valueOf(callbackQuery.getFrom().getId()),
                    replyMessageService.getEmojiReplyText("reply.finish.no", Emojis.FINISH_NO) +
                            replyMessageService.getEmojiReplyText("reply.again", Emojis.AGAIN));
    }

    @Override
    public BotState getHandlerName() {
        return BotState.ICE;
    }
}

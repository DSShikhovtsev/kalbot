package kalbot.bot.handlers.service;

import kalbot.bot.utils.ReplyKeyboardUtil;
import kalbot.domain.Brand;
import kalbot.domain.Taste;
import kalbot.domain.Tobacco;
import kalbot.domain.UserState;
import kalbot.service.brand.BrandService;
import kalbot.service.tobacco.TobaccoService;
import kalbot.service.userstate.UserStateService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BrandBotService {

    private final BrandService brandService;
    private final ReplyKeyboardUtil keyboardUtil;
    private final UserStateService userStateService;
    private final TobaccoService tobaccoService;

    public BrandBotService(BrandService brandService, ReplyKeyboardUtil keyboardUtil, UserStateService userStateService, TobaccoService tobaccoService) {
        this.brandService = brandService;
        this.keyboardUtil = keyboardUtil;
        this.userStateService = userStateService;
        this.tobaccoService = tobaccoService;
    }

    public SendMessage getMessage(Long chatId, String message) {
        return createMessage(chatId, message);
    }

    public SendMessage getCutMessage(Long chatId, String emojiMessage) {
        return createCutMessage(chatId, emojiMessage);
    }

    private SendMessage createMessage(Long chatId, String message) {
        SendMessage reply = new SendMessage();
        reply.setChatId(chatId);
        reply.setText(message);
        reply.enableMarkdown(true);
        reply.setReplyMarkup(getBrandMenuData(chatId));
        return reply;
    }

    private InlineKeyboardMarkup getBrandMenuData(Long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = keyboardUtil.getInlineKeyboardMarkup();
        UserState userState = userStateService.getByChatId(chatId);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        for (Taste t : userState.getTastes()) {
            rowList.addAll(getButtons(t, brandService.getAllByTasteId(t.getId())));
        }
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    private SendMessage createCutMessage(Long chatId, String emojiMessage) {
        SendMessage reply = new SendMessage();
        reply.setChatId(chatId);
        reply.setText(emojiMessage);
        reply.enableMarkdown(true);
        reply.setReplyMarkup(getCutBrandMenuData(chatId));
        return reply;
    }

    private InlineKeyboardMarkup getCutBrandMenuData(Long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = keyboardUtil.getInlineKeyboardMarkup();
        UserState userState = userStateService.getByChatId(chatId);
        List<List<InlineKeyboardButton>> rowList;

        Set<Taste> tastesInSelectedTobaccos = userState.getKalian().getTobaccos().stream().map(Tobacco::getTaste).collect(Collectors.toSet());
        List<Taste> notResolvedTastes = userState.getTastes().stream().filter(t -> !tastesInSelectedTobaccos.contains(t)).collect(Collectors.toList());

        if (notResolvedTastes.size() == 1) {
            rowList = getFinalButtons(notResolvedTastes.get(0), brandService.getAllByTasteId(notResolvedTastes.get(0).getId()));
        } else {
            rowList = new ArrayList<>();
            for (Taste t : notResolvedTastes) {
                rowList.addAll(getButtons(t, brandService.getAllByTasteId(t.getId())));
            }
        }
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    private List<List<InlineKeyboardButton>> getButtons(Taste taste, List<Brand> brands) {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        for (Brand brand : brands) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(brand.getFirm() + " : " + taste.getTaste());
            button.setCallbackData(brand.getId() + "|" + taste.getId());
            row.add(button);
            rowList.add(row);
        }
        return rowList;
    }

    private List<List<InlineKeyboardButton>> getFinalButtons(Taste taste, List<Brand> brands) {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        for (Brand brand : brands) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(brand.getFirm() + " : " + taste.getTaste());
            button.setCallbackData(brand.getId() + "|" + taste.getId() + "~");
            row.add(button);
            rowList.add(row);
        }
        return rowList;
    }

    public void addTobacco(UserState userState, CallbackQuery callbackQuery) {
        Long brandId = Long.valueOf(callbackQuery.getData()
                .substring(0, callbackQuery.getData().lastIndexOf("|")));
        long tasteId;
        if (callbackQuery.getData().contains("~")) {
            tasteId = Long.parseLong(callbackQuery.getData()
                    .substring(callbackQuery.getData().lastIndexOf("|") + 1,
                            callbackQuery.getData().lastIndexOf("~")));
        } else {
            tasteId = Long.parseLong(callbackQuery.getData()
                    .substring(callbackQuery.getData().lastIndexOf("|") + 1));
        }
        userState.getKalian().getTobaccos().add(tobaccoService.getByBrandIdAndTasteId(brandId, tasteId));
    }
}

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
        userState.getTastes().forEach(t -> {
            List<Brand> brands = brandService.getAllByTasteId(t.getId());
            /*if (brands.size() > 1) */addButtons(rowList, t, brands);
        });
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
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        List<Taste> tastes = userState.getTastes().stream().filter(t -> {
            for (Tobacco tobacco : userState.getKalian().getTobaccos()) {
                if (tobacco.getTaste().getId().equals(t.getId())) return false;
            }
            return true;
        }).collect(Collectors.toList());
        if (tastes.size() == 1) {
            generateFinalButton(rowList, tastes.get(0), brandService.getAllByTasteId(tastes.get(0).getId()));
        } else {
            tastes.forEach(t -> {
                List<Brand> brands = brandService.getAllByTasteId(t.getId());
                addButtons(rowList, t, brands);
            });
        }
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    private void addButtons(List<List<InlineKeyboardButton>> rowList, Taste taste, List<Brand> brands) {
        for (Brand brand : brands) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(brand.getFirm() + " : " + taste.getTaste());
            button.setCallbackData(brand.getId() + "|" + taste.getId());
            row.add(button);
            rowList.add(row);
        }
    }

    private void generateFinalButton(List<List<InlineKeyboardButton>> rowList, Taste taste, List<Brand> brands) {
        for (Brand brand : brands) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(brand.getFirm() + " : " + taste.getTaste());
            button.setCallbackData(brand.getId() + "|" + taste.getId() + "~");
            row.add(button);
            rowList.add(row);
        }
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

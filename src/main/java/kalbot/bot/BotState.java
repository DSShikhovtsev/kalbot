package kalbot.bot;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BotState {

    SHOW_MAIN_MENU("Меню"),
    SHOW_HELP_MENU("Помощь"),
    AGAIN("Сбросить"),
    MISTAKE("Ошибочный вызов"),
    FORTRESS("Крепость"),
    GLOBAL_TASTE("Обобщенный вкус"),
    TASTE("Вкус"),
    TASTE_CHOICE("Выбор вкусов"),
    BRAND("Производитель"),
    BRAND_CHOICE("Выбор производителя"),
    ICE("Холодок"),
    FINISH_WORK("Завершение сборки кальяна"),
    AT_TIME("Выбор времени для заказа"),
    START_WORK("Хочу кальян!");

    private String text;
}

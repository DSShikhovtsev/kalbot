package kalbot.bot.utils;

import com.vdurmont.emoji.EmojiParser;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Emojis {

    HELLO(EmojiParser.parseToUnicode(":new_moon_with_face:") + " " + EmojiParser.parseToUnicode(":full_moon_with_face:")),
    POINT_LEFT(EmojiParser.parseToUnicode(":point_left:")),
    POINT_RIGHT(EmojiParser.parseToUnicode(":point_right:")),
    POINT_UP(EmojiParser.parseToUnicode(":point_up:")),
    POINT_DOWN(EmojiParser.parseToUnicode(":point_down:")),
    KISSING(EmojiParser.parseToUnicode(":kissing_heart:")),
    STOP(EmojiParser.parseToUnicode(":no_entry:")),
    BRAND_MARK(EmojiParser.parseToUnicode(":performing_arts:")),
    FORTRESS(EmojiParser.parseToUnicode(":brick:")),
    TASTE(EmojiParser.parseToUnicode(":thinking:")),
    TASTE_GLOBAL(EmojiParser.parseToUnicode(":lemon:")),
    ICE(EmojiParser.parseToUnicode(":santa:")),
    END_OF_CHOICE(EmojiParser.parseToUnicode(":bellhop_bell:")),
    TOBACCO_BRAND(EmojiParser.parseToUnicode(":triumph:")),
    ANYMORE(EmojiParser.parseToUnicode(":rolling_eyes:")),
    SUCCESS_MARK(EmojiParser.parseToUnicode(":white_check_mark:")),
    FINISH(EmojiParser.parseToUnicode(":poop:")),
    FINISH_YES(EmojiParser.parseToUnicode(":woman_scientist:")),
    FINISH_NO(EmojiParser.parseToUnicode(":unamused:")),
    AGAIN(EmojiParser.parseToUnicode(":bath:")),
    NOTIFICATION_MARK_FAILED(EmojiParser.parseToUnicode(":exclamation:")),
    HELP_MENU_WELCOME(EmojiParser.parseToUnicode(":rage:"));

    private String emoji;

    public String toString() {
        return emoji;
    }
}

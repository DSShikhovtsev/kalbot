package kalbot.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BotException extends RuntimeException {

    public String exception;

    public BotException(String exception) {
        this.exception = exception;
    }
}

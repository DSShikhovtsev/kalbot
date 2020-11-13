package kalbot.bot.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;

@Component
@Getter
@Setter
@ConfigurationProperties("telegrambot")
public class BotProperties {

    private String username;
    private String botToken;
    private String webHookPath;

    private DefaultBotOptions.ProxyType proxyType;
    private String proxyHost;
    private int proxyPort;

    public DefaultBotOptions getOptions() {
        DefaultBotOptions options = ApiContext
                .getInstance(DefaultBotOptions.class);

        options.setProxyHost(this.proxyHost);
        options.setProxyPort(this.proxyPort);
        options.setProxyType(this.proxyType);
        return options;
    }
}

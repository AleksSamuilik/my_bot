package it.alex.telegram.bot.config;

import it.alex.telegram.bot.bot.Bot;
import it.alex.telegram.bot.service.CityService;
import it.alex.telegram.bot.service.MessageReciever;
import it.alex.telegram.bot.service.MessageSender;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;

@Component
@Log
public class BotConfig {

    @Autowired
    private CityService cityService;
    private final String botName = "SamOTS_bot";
    private final String botToken = "1207959370:AAHK-08OX0D0grNk0oUrF-8_r2S9BK8CHRQ";

    private static final int PRIORITY_FOR_SENDER = 1;
    private static final int PRIORITY_FOR_RECEIVER = 3;


    @Bean
    public void init() {
        ApiContextInitializer.init();
        final Bot bot = new Bot(botName, botToken);
        final MessageReciever messageReciever = new MessageReciever(cityService, bot);
        final MessageSender messageSender = new MessageSender(bot);

        bot.botConnect();

        final Thread receiver = new Thread(messageReciever);
        receiver.setDaemon(true);
        receiver.setName("MsgReciever");
        receiver.setPriority(PRIORITY_FOR_RECEIVER);
        receiver.start();

        final Thread sender = new Thread(messageSender);
        sender.setDaemon(true);
        sender.setName("MsgSender");
        sender.setPriority(PRIORITY_FOR_SENDER);
        sender.start();

    }
}

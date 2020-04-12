package it.alex.telegram.bot.handler;

import it.alex.telegram.bot.bot.Bot;
import it.alex.telegram.bot.command.ParsedCommand;
import lombok.extern.java.Log;
import org.telegram.telegrambots.meta.api.objects.Update;

@Log
public class DefaultHandler extends AbstractHandler {
    public DefaultHandler(Bot bot) {
        super(bot);
    }

    @Override
    public String operate(String chatId, ParsedCommand parsedCommand, Update update) {
        return "";
    }
}

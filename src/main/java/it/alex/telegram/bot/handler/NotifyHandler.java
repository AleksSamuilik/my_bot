package it.alex.telegram.bot.handler;

import it.alex.telegram.bot.ability.Notify;
import it.alex.telegram.bot.bot.Bot;
import it.alex.telegram.bot.command.ParsedCommand;
import lombok.extern.java.Log;
import org.telegram.telegrambots.meta.api.objects.Update;

@Log
public class NotifyHandler extends AbstractHandler {


    private static final long DEFAULT_TIME = 30;
    private final int MILLISEC_IN_SEC = 1000;
    private final String WRONG_INPUT_MESSAGE = "Wrong input. Time must be specified as an integer greater than 0";

    public NotifyHandler(final Bot bot) {
        super(bot);
    }

    @Override
    public String operate(final String chatId,final ParsedCommand parsedCommand,final Update update) {
        String text = parsedCommand.getText();
        if ("".equals(text)) {
            return "You must specify the delay time. Like this:\n" +
                    "/notify " + DEFAULT_TIME;
        }
        long timeInSec;
        try {
            timeInSec = Long.parseLong(text.trim());
        } catch (NumberFormatException e) {
            return WRONG_INPUT_MESSAGE;
        }
        if (timeInSec > 0) {
            Thread thread = new Thread(new Notify(bot, timeInSec * MILLISEC_IN_SEC, chatId));
            thread.start();
        } else return WRONG_INPUT_MESSAGE;
        return "";
    }
}
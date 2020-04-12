package it.alex.telegram.bot.handler;

import it.alex.telegram.bot.bot.Bot;
import it.alex.telegram.bot.command.Command;
import it.alex.telegram.bot.command.ParsedCommand;
import lombok.extern.java.Log;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Log
public class SystemHandler extends AbstractHandler {
    private final String END_LINE = "\n";

    public SystemHandler(final Bot bot) {
        super(bot);
    }

    @Override
    public String operate(final String chatId, final ParsedCommand parsedCommand, final Update update) {
        final Command command = parsedCommand.getCommand();

        switch (command) {
            case START:
                bot.sendQueue.add(getMessageStart(chatId));
                break;
            case HELP:
                bot.sendQueue.add(getMessageHelp(chatId));
                break;
        }
        return "";
    }

    private SendMessage getMessageHelp(final String chatID) {
        final SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatID);
        sendMessage.enableMarkdown(true);

        final StringBuilder text = new StringBuilder();
        text.append("*This is help message*").append(END_LINE).append(END_LINE);
        text.append("[/start](/start) - show start message").append(END_LINE);
        text.append("[/help](/help) - show help message").append(END_LINE);
        text.append("/[city] _name_ - get description City").append(END_LINE);
        text.append("[/notify](/notify) _time-in-sec_ - receive notification from me after the specified time").append(END_LINE);

        sendMessage.setText(text.toString());
        return sendMessage;
    }

    private SendMessage getMessageStart(final String chatID) {
        final SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatID);
        sendMessage.enableMarkdown(true);
        final StringBuilder text = new StringBuilder();
        text.append("Hello. I'm  *").append(bot.getBotUsername()).append("*").append(END_LINE);
        text.append("I tell you information about cities").append(END_LINE);
        text.append("All that I can do - you can see calling the command [/help](/help)");
        sendMessage.setText(text.toString());
        return sendMessage;
    }
}

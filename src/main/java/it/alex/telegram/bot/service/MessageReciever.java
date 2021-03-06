package it.alex.telegram.bot.service;

import it.alex.telegram.bot.bot.Bot;
import it.alex.telegram.bot.command.Command;
import it.alex.telegram.bot.command.ParsedCommand;
import it.alex.telegram.bot.command.Parser;
import it.alex.telegram.bot.handler.*;
import lombok.extern.java.Log;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Log
public class MessageReciever implements Runnable {
    private final CityService cityService;
    private final int WAIT_FOR_NEW_MESSAGE_DELAY = 1000;
    private final Bot bot;
    private final Parser parser;

    public MessageReciever(final CityService cityService, final Bot bot) {
        this.cityService = cityService;
        this.bot = bot;
        parser = new Parser(bot.getBotUsername());
    }

    @Override
    public void run() {
        log.info("[STARTED] MsgReciever.  Bot class: " + bot);
        while (true) {
            for (Object object = bot.receiveQueue.poll(); object != null; object = bot.receiveQueue.poll()) {
                log.info("New object for analyze in queue " + object.toString());
                analyze(object);
            }
            try {
                Thread.sleep(WAIT_FOR_NEW_MESSAGE_DELAY);
            } catch (InterruptedException e) {
                log.info("Catch interrupt. Exit\n" + e);
                return;
            }
        }
    }

    private void analyze(final Object object) {
        if (object instanceof Update) {
            Update update = (Update) object;
            log.info("Update recieved: " + update.toString());
            analyzeForUpdateType(update);
        } else log.info("Cant operate type of object: " + object.toString());
    }

    private void analyzeForUpdateType(final Update update) {
        Message message = update.getMessage();
        Long chatId = message.getChatId();

        ParsedCommand parsedCommand = new ParsedCommand(Command.NONE, "");

        if (message.hasText()) {
            parsedCommand = parser.getParsedCommand(message.getText());
        }

        AbstractHandler handlerForCommand = getHandlerForCommand(parsedCommand.getCommand());
        String operationResult = handlerForCommand.operate(chatId.toString(), parsedCommand, update);

        if (!"".equals(operationResult)) {
            SendMessage messageOut = new SendMessage();
            messageOut.setChatId(chatId);
            messageOut.setText(operationResult);
            bot.sendQueue.add(messageOut);
        }
    }

    private AbstractHandler getHandlerForCommand(final Command command) {
        if (command == null) {
            log.info("Null command accepted. This is not good scenario.");
            return new DefaultHandler(bot);
        }
        switch (command) {
            case START:
            case HELP:
                SystemHandler systemHandler = new SystemHandler(bot);
                log.info("Handler for command[" + command.toString() + "] is: " + systemHandler);
                return systemHandler;
            case NOTIFY:
                NotifyHandler notifyHandler = new NotifyHandler(bot);
                log.info("Handler for command[" + command.toString() + "] is: " + notifyHandler);
                return notifyHandler;
            case CITY:
                CityHandler cityHandler = new CityHandler(bot, cityService);
                log.info("Handler for command[" + command.toString() + "] is: " + cityHandler);
                return cityHandler;
            default:
                log.info("Handler for command[" + command.toString() + "] not Set. Return DefaultHandler");
                return new DefaultHandler(bot);
        }
    }
}

package it.alex.telegram.bot.service;

import it.alex.telegram.bot.bot.Bot;
import lombok.extern.java.Log;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.Message;

@Log
public class MessageSender implements Runnable {
    private final int SENDER_SLEEP_TIME = 1000;
    private final Bot bot;

    public MessageSender(final Bot bot) {
        this.bot = bot;
    }

    @Override
    public void run() {
        log.info("[STARTED] MsgSender.  Bot class: " + bot);
        try {
            while (true) {
                for (Object object = bot.sendQueue.poll(); object != null; object = bot.sendQueue.poll()) {
                    log.info("Get new msg to send " + object);
                    send(object);
                }
                try {
                    Thread.sleep(SENDER_SLEEP_TIME);
                } catch (InterruptedException e) {
                    log.info("Take interrupt while operate msg list\n" + e);
                }
            }
        } catch (Exception e) {
            log.info(e.toString());
        }
    }

    private void send(final Object object) {
        try {
            MessageType messageType = messageType(object);
            switch (messageType) {
                case EXECUTE:
                    BotApiMethod<Message> message = (BotApiMethod<Message>) object;
                    log.info("Use Execute for " + object);
                    bot.execute(message);
                    break;
                case STICKER:
                    SendSticker sendSticker = (SendSticker) object;
                    log.info("Use SendSticker for " + object);
                    bot.execute(sendSticker);
                    break;
                default:
                    log.info("Cant detect type of object. " + object);
            }
        } catch (Exception e) {
            log.info(e.getMessage() + "\n" + e);
        }
    }

    private MessageType messageType(final Object object) {
        if (object instanceof SendSticker) return MessageType.STICKER;
        if (object instanceof BotApiMethod) return MessageType.EXECUTE;
        return MessageType.NOT_DETECTED;
    }

    enum MessageType {
        EXECUTE, STICKER, NOT_DETECTED,
    }
}

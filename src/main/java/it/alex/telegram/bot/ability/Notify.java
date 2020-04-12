package it.alex.telegram.bot.ability;

import it.alex.telegram.bot.bot.Bot;
import lombok.Data;
import lombok.ToString;
import lombok.extern.java.Log;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Log
@Data
public class Notify implements Runnable {
    private static final int MILLISEC_IN_SEC = 1000;

    private final Bot bot;
    private final long delayInMillisec;
    private final String chatID;

    @Override
    public void run() {
        log.info("RUN. " + toString());
        bot.sendQueue.add(getFirstMessage());
        try {
            Thread.sleep(delayInMillisec);
            bot.sendQueue.add(Stickers.CHUCK.getSendSticker(chatID));
        } catch (InterruptedException e) {
            log.info(e.getMessage() + "\n" + e);
        }
        log.info("FIHISH. " + toString());
    }

    private SendMessage getFirstMessage() {
        return new SendMessage(chatID, "I will send you notify after " + delayInMillisec / MILLISEC_IN_SEC + "sec");
    }
}
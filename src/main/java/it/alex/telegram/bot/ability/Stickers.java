package it.alex.telegram.bot.ability;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
@RequiredArgsConstructor
public enum Stickers {
    CHUCK("CAACAgUAAxkBAANAXo5dMFrLbnDaknrrq853eg6A4twAAm4DAALpCsgDfDQPP9rsBG0YBA");

    private final String stickerId;

    public SendSticker getSendSticker(String chatId) {
        if ("".equals(chatId)) throw new IllegalArgumentException("ChatId cant be null");
        SendSticker sendSticker = getSendSticker();
        sendSticker.setChatId(chatId);
        return sendSticker;
    }

    public SendSticker getSendSticker() {
        SendSticker sendSticker = new SendSticker();
        sendSticker.setSticker(stickerId);
        return sendSticker;
    }
}

package it.alex.telegram.bot.handler;

import it.alex.telegram.bot.bot.Bot;
import it.alex.telegram.bot.command.ParsedCommand;
import it.alex.telegram.bot.service.CityService;
import lombok.extern.java.Log;
import org.telegram.telegrambots.meta.api.objects.Update;

@Log
public class CityHandler extends AbstractHandler {

    private final CityService cityService;
    private static final String DEFAULT_CITY = "Minsk";

    public CityHandler(Bot bot, CityService cityService) {
        super(bot);
        this.cityService = cityService;
    }

    @Override
    public String operate(String chatId, ParsedCommand parsedCommand, Update update) {
        String text = parsedCommand.getText();
        if (text.isEmpty()) {
            return "You must specify the name of the city. Like this:\n" +
                    "/city " + DEFAULT_CITY;
        }
        return cityService.getCityDescription(text);
    }
}
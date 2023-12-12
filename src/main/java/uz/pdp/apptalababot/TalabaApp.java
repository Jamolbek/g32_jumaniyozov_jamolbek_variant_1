package uz.pdp.apptalababot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import uz.pdp.apptalababot.service.BotService;

public class TalabaApp {

    public static void main(String[] args){

        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(BotService.getInstance());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}

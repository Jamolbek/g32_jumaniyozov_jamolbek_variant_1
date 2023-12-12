package uz.pdp.apptalababot.service;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BotService extends TelegramLongPollingBot {
    private static BotService instance;

    public Set<Long> talabaCreateChatIds = new HashSet<>();

    private BotService() {

    }

    public static BotService getInstance() {
        if (Objects.isNull(instance))
            instance = new BotService();
        return instance;
    }

    @Override
    public String getBotUsername() {
        return "Testingg28bot";
    }

    @Override
    public String getBotToken() {
        return "6446323544:AAE_8yQFWCE9G3YTGYHLcuVI2eeLo9hm3_g";
    }
    @Override
    public void onUpdateReceived(Update update) {

        try {

            if (update.hasMessage()) {

                MessageService messageService = MessageServiceImpl.getInstance();
                Message message = update.getMessage();
                messageService.process(message);

            } else if (update.hasCallbackQuery()) {

                CallbackQuery callbackQuery = update.getCallbackQuery();
                CallbackService callbackService = CallbackServiceImpl.getInstance();
                callbackService.process(callbackQuery);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Message sendMessage(SendMessage sendMessage) {
        try {
            return execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Message sendDoc(SendDocument sendDocument) {
        try {
            return execute(sendDocument);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void editMessageText(EditMessageText editMessageText) {
        try {
            execute(editMessageText);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

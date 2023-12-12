package uz.pdp.apptalababot.service;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import uz.pdp.apptalababot.model.Talaba;

import java.util.List;
import java.util.Objects;

public class CallbackServiceImpl implements CallbackService {
    private static CallbackServiceImpl instance;
    private CallbackServiceImpl(){

    }
    public static CallbackServiceImpl getInstance() {
        if (Objects.isNull(instance))
            instance = new CallbackServiceImpl();
        return instance;
    }

    @Override
    public void process(CallbackQuery callbackQuery) {

        String data = callbackQuery.getData();

        if (data.startsWith("🔙 orqaga"))
            back(callbackQuery);

        else if (data.startsWith("read"))
            readTalaba(callbackQuery);

        else if (data.startsWith("delete"))
            deleteTalaba(callbackQuery);

        else if (data.equals("createTalaba"))
            preCreateTalaba(callbackQuery);

    }

    private void readTalaba(CallbackQuery callbackQuery) {

        Message message = callbackQuery.getMessage();

        int talabaId = Integer.parseInt(callbackQuery.getData().split(":")[1]);

        TalabaService talabaService = TalabaServiceImpl.getInstance();

        Talaba talaba = talabaService.getTalabaById(talabaId);

        String stringBuilder = "id: " + talaba.getId() + "\n" +
                "Name: " + talaba.getName() + "\n" +
                "Surname: " + talaba.getSurname() + "\n" +
                "GroupType: " + talaba.getGroupType() + "\n" +
                "BirthDate: " + talaba.getBirthDate();

        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(message.getChatId());
        editMessageText.setText(stringBuilder);
        editMessageText.setMessageId(message.getMessageId());

        InlineKeyboardMarkup markup = ButtonServiceImpl.getInstance().back();
        editMessageText.setReplyMarkup(markup);

        BotService.getInstance().editMessageText(editMessageText);
    }


    private void deleteTalaba(CallbackQuery callbackQuery) {

        Message message = callbackQuery.getMessage();

        int talabaId = Integer.parseInt(callbackQuery.getData().split(":")[1]);

        TalabaService talabaService = TalabaServiceImpl.getInstance();

        talabaService.delete(talabaId);

        List<Talaba> talabalar = talabaService.read();

        ButtonService buttonService = ButtonServiceImpl.getInstance();

        InlineKeyboardMarkup markup = buttonService.talabaButton(talabalar);

        StringBuilder stringBuilder = talabaService.getTalabaContent(talabalar);

        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(message.getChatId());
        editMessageText.setText(stringBuilder.toString());
        editMessageText.setMessageId(message.getMessageId());
        editMessageText.setReplyMarkup(markup);

        BotService.getInstance().editMessageText(editMessageText);
    }

    private void preCreateTalaba(CallbackQuery callbackQuery) {

        Message message = callbackQuery.getMessage();

        String text = """
                Quyidagi shablon asosida talaba ma'lumotlarini kiriting:\s
                
                Example:
                
                Ismi Abdulla
                Familiya Sadullayev
                GroupType FIRST_SMENA (FIRST_SMENA, SECOND_SMENA, THIRD_SMENA)
                BirthDate (12.12.2002 19:50)
                """;

        ButtonServiceImpl buttonService = ButtonServiceImpl.getInstance();

        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(message.getChatId());
        editMessageText.setText(text);
        editMessageText.setMessageId(message.getMessageId());
        editMessageText.setReplyMarkup(buttonService.back());

        BotService botService = BotService.getInstance();
        botService.editMessageText(editMessageText);
        botService.talabaCreateChatIds.add(message.getChatId());
    }

    private void back(CallbackQuery callbackQuery) {

        Message message = callbackQuery.getMessage();

        ButtonService buttonService = ButtonServiceImpl.getInstance();

        TalabaService talabaService = TalabaServiceImpl.getInstance();

        List<Talaba> talabalar = talabaService.read();

        StringBuilder stringBuilder = talabaService.getTalabaContent(talabalar);

        InlineKeyboardMarkup markup = buttonService.talabaButton(talabalar);

        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(message.getChatId());
        editMessageText.setText(stringBuilder.toString());
        editMessageText.setMessageId(message.getMessageId());
        editMessageText.setReplyMarkup(markup);

        BotService.getInstance().editMessageText(editMessageText);
    }
}

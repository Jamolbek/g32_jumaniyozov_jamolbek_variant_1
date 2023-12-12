package uz.pdp.apptalababot.service;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import uz.pdp.apptalababot.model.Talaba;
import uz.pdp.apptalababot.utils.BotButtonConst;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ButtonServiceImpl implements ButtonService {

    private static ButtonServiceImpl instance;
    private ButtonServiceImpl() {

    }

    public static ButtonServiceImpl getInstance() {
        if (Objects.isNull(instance))
            instance = new ButtonServiceImpl();
        return instance;
    }

    @Override
    public ReplyKeyboardMarkup home() {

        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setResizeKeyboard(true);

        List<KeyboardRow> rows = new ArrayList<>();

        //talaba row
        KeyboardRow talabaRow = new KeyboardRow();
        KeyboardButton talabaButton = new KeyboardButton(BotButtonConst.TALABA);
        talabaRow.add(talabaButton);
        rows.add(talabaRow);

        //download row
        KeyboardRow downloadJson = new KeyboardRow();
        KeyboardButton downloadJsonButton = new KeyboardButton(BotButtonConst.DOWNLOAD_JSON);
        downloadJson.add(downloadJsonButton);
        rows.add(downloadJson);

        //download excel
        KeyboardRow downloadExcel = new KeyboardRow();
        KeyboardButton downloadExcelButton = new KeyboardButton(BotButtonConst.DOWNLOAD_EXCEL);
        downloadExcel.add(downloadExcelButton);
        rows.add(downloadExcel);

        markup.setKeyboard(rows);

        return markup;
    }

    @Override
    public InlineKeyboardMarkup talabaButton(List<Talaba> talabalar) {

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        for (int i = 0; i < talabalar.size(); i++) {

            Talaba talaba = talabalar.get(i);

            InlineKeyboardButton read = new InlineKeyboardButton((i + 1) + ". Ko'rish 👁️‍🗨️️");
            read.setCallbackData("read:" + talaba.getId());

            InlineKeyboardButton delete = new InlineKeyboardButton(" Delete 🗑️");
            delete.setCallbackData("delete:" + talaba.getId());

            List<InlineKeyboardButton> row = List.of(read, delete);
            rows.add(row);
        }

        InlineKeyboardButton createTalaba = new InlineKeyboardButton("➕ Create talaba");
        createTalaba.setCallbackData("createTalaba");

        rows.add(List.of(createTalaba));

        markup.setKeyboard(rows);

        return markup;
    }

    @Override
    public InlineKeyboardMarkup back() {

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        List<InlineKeyboardButton> backRow = new ArrayList<>();

        InlineKeyboardButton backButton = new InlineKeyboardButton();
        backButton.setText("🔙 orqaga");
        backButton.setCallbackData("🔙 orqaga");
        backRow.add(backButton);

        rows.add(backRow);

        markup.setKeyboard(rows);

        return markup;
    }
}

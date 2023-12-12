package uz.pdp.apptalababot.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import uz.pdp.apptalababot.enums.GroupTypeEnum;
import uz.pdp.apptalababot.model.Talaba;
import uz.pdp.apptalababot.utils.BotButtonConst;

import java.io.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class MessageServiceImpl implements MessageService {
    private static MessageServiceImpl instance;
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private MessageServiceImpl() {

    }

    public static MessageServiceImpl getInstance() {
        if (Objects.isNull(instance))
            instance = new MessageServiceImpl();
        return instance;
    }

    @Override
    public void process(Message message) {

        if (message.hasText())
            processText(message);
    }

    private void processText(Message message) {

        String text = message.getText();

        switch (text) {
            case "/start" -> processTextHome(message);
            case BotButtonConst.TALABA -> processTextTalaba(message);
            case BotButtonConst.DOWNLOAD_JSON -> processTextDownloadJson(message);
            case BotButtonConst.DOWNLOAD_EXCEL -> processTextDownloadExcel(message);
            default -> otherText(message);
        }
    }

    private void processTextHome(Message message) {

        ButtonService buttonService = ButtonServiceImpl.getInstance();

        ReplyKeyboardMarkup markup = buttonService.home();

        String firstName = message.getChat().getFirstName();

        SendMessage sendMessage = new SendMessage(
                message.getChatId().toString(),
                "Botga hush kelibsiz " + firstName+ " !!!"
        );

        sendMessage.setReplyMarkup(markup);

        BotService botService = BotService.getInstance();

        botService.sendMessage(sendMessage);
    }

    private void processTextTalaba(Message message) {

        ButtonService buttonService = ButtonServiceImpl.getInstance();

        TalabaService talabaService = TalabaServiceImpl.getInstance();

        List<Talaba> talabalar = talabaService.read();

        StringBuilder stringBuilder = new StringBuilder();

        //talaba mavjud emas
        if (talabalar.isEmpty()) {

            stringBuilder.append("Talabalar list is empty");

        } else {

            stringBuilder.append("Talabalar:\n\n");

            for (int i = 0; i < talabalar.size(); i++) {

                Talaba talaba = talabalar.get(i);

                stringBuilder
                        .append(i + 1)
                        .append(". ")
                        .append(talaba.getName())
                        .append("\n\n");
            }
        }

        SendMessage sendMessage = new SendMessage(
                message.getChatId().toString(),
                stringBuilder.toString()
        );

        InlineKeyboardMarkup markup = buttonService.talabaButton(talabalar);
        sendMessage.setReplyMarkup(markup);

        BotService botService = BotService.getInstance();
        botService.sendMessage(sendMessage);
    }

    private void processTextDownloadJson(Message message) {

        TalabaServiceImpl talabaService = TalabaServiceImpl.getInstance();

        List<Talaba> talabalar = talabaService.read();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String json = gson.toJson(talabalar);

        File file = new File("talabalar.json");

        try (Writer writer = new FileWriter(file)) {

            writer.write(json);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SendDocument sendDocument = new SendDocument(
                message.getChatId().toString(),
                new InputFile(file)
        );

        BotService botService = BotService.getInstance();

        botService.sendDoc(sendDocument);
    }

    private void processTextDownloadExcel(Message message) {

        TalabaServiceImpl talabaService = TalabaServiceImpl.getInstance();

        List<Talaba> talabalar = talabaService.read();

        ExcelService excelService = ExcelServiceImpl.getInstance();

        File file = excelService.writeExcel(talabalar);

        SendDocument sendDocument = new SendDocument(
                message.getChatId().toString(),
                new InputFile(file)
        );

        BotService botService = BotService.getInstance();

        botService.sendDoc(sendDocument);
    }

    private void otherText(Message message) {

        BotService botService = BotService.getInstance();

        if (botService.talabaCreateChatIds.contains(message.getChatId()))
            createTalaba(message, botService);
    }

    private void createTalaba(Message message, BotService botService) {
        String text = message.getText();

        String[] strings = text.split("\n");

        if (strings.length != 4) {
            SendMessage sendMessage = new SendMessage(
                    message.getChatId().toString(),
                    "Ma'lumot xato kiritilgan !"
            );
            botService.sendMessage(sendMessage);
            throw new RuntimeException("Noto'g'ri ma'lumot kiritilgan");
        }

        String title = strings[0];
        String description = strings[1];
        GroupTypeEnum status = GroupTypeEnum.valueOf(strings[2]);
        LocalDateTime localDateTime = LocalDateTime.from(dateTimeFormatter.parse(strings[3]));
        Timestamp dueDate = Timestamp.valueOf(localDateTime);

        TalabaServiceImpl talabaService = TalabaServiceImpl.getInstance();

        Talaba talaba = new Talaba(
                null,
                title,
                description,
                dueDate,
                status
        );

        talabaService.create(talaba);

        ButtonServiceImpl buttonService = ButtonServiceImpl.getInstance();

        List<Talaba> talabalar = talabaService.read();

        InlineKeyboardMarkup markup = buttonService.talabaButton(talabalar);

        StringBuilder stringBuilder = talabaService.getTalabaContent(talabalar);

        SendMessage sendMessage = new SendMessage(
                message.getChatId().toString(),
                stringBuilder.toString()
        );

        sendMessage.setReplyMarkup(markup);

        BotService.getInstance().sendMessage(sendMessage);

        botService.talabaCreateChatIds.remove(message.getChatId());
    }
}

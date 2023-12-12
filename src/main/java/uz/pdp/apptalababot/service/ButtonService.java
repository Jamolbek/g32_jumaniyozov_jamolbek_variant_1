package uz.pdp.apptalababot.service;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import uz.pdp.apptalababot.model.Talaba;

import java.util.List;

public interface ButtonService {

    ReplyKeyboardMarkup home();

    InlineKeyboardMarkup talabaButton(List<Talaba> talabalar);

    InlineKeyboardMarkup back();
}

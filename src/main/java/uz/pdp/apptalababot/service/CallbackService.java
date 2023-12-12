package uz.pdp.apptalababot.service;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface CallbackService {

    void process(CallbackQuery callbackQuery);

}

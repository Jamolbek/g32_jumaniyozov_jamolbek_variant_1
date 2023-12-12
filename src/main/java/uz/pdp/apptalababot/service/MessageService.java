package uz.pdp.apptalababot.service;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageService {

    void process(Message message);

}

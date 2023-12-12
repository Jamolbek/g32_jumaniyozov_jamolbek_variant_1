package uz.pdp.apptalababot.service;

import uz.pdp.apptalababot.model.Talaba;

import java.util.List;

public interface TalabaService {

    List<Talaba> read();

    Talaba create(Talaba talaba);

    Talaba edit(Integer id, Talaba editTalaba);

    boolean delete(Integer id);

    Talaba getTalabaById(Integer id);

    StringBuilder getTalabaContent(List<Talaba> talabalar);
}

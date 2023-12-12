package uz.pdp.apptalababot.service;

import com.github.javafaker.Artist;
import com.github.javafaker.DateAndTime;
import com.github.javafaker.Faker;
import uz.pdp.apptalababot.enums.GroupTypeEnum;
import uz.pdp.apptalababot.model.Talaba;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TalabaServiceImpl implements TalabaService {

    private static TalabaServiceImpl instance;
    public List<Talaba> talabalar = new ArrayList<>(generate());

    private List<Talaba> generate() {


        List<Talaba> talabaList = new ArrayList<>();

        Faker faker = Faker.instance();


        Artist student = faker.artist();

        DateAndTime date = faker.date();

        for (int i = 0; i < 10; i++) {

            Talaba talaba = new Talaba(
                    i + 100,
                    student.name(),
                    student.name(),
                    new Timestamp(date.birthday().getTime()),
                    GroupTypeEnum.FIRST_SMENA
            );
            talabaList.add(talaba);

        }

        return talabaList;
    }

    private TalabaServiceImpl() {

    }

    public static TalabaServiceImpl getInstance() {
        if (Objects.isNull(instance))
            instance = new TalabaServiceImpl();
        return instance;
    }

    @Override
    public List<Talaba> read() {
        return talabalar;
    }

    @Override
    public Talaba create(Talaba talaba) {
        talaba.setId(talabalar.size() + 1);
        talabalar.add(talaba);
        return talaba;
    }

    @Override
    public Talaba edit(Integer id, Talaba editTalaba) {

        Talaba talaba = getTalabaById(id);

        talaba.setName(editTalaba.getName());
        talaba.setSurname(editTalaba.getSurname());
        talaba.setGroupType(editTalaba.getGroupType());
        talaba.setBirthDate(editTalaba.getBirthDate());

        return talaba;
    }

    @Override
    public boolean delete(Integer id) {
        return talabalar.removeIf(talaba -> Objects.equals(talaba.getId(), id));
    }


    @Override
    public Talaba getTalabaById(Integer id) {
        return talabalar
                .stream()
                .filter(talaba -> Objects.equals(talaba.getId(), id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("talaba not found with id: " + id));
    }

    @Override
    public StringBuilder getTalabaContent(List<Talaba> talabalar) {

        StringBuilder stringBuilder = new StringBuilder();

        if (talabalar.isEmpty()) {

            stringBuilder.append("Talaba list is empty");

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
        return stringBuilder;
    }
}

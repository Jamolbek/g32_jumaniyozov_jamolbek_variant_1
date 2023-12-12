package uz.pdp.apptalababot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.apptalababot.enums.GroupTypeEnum;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Talaba {

    private Integer id;
    private String name;

    private String surname;

    private Timestamp birthDate;

    private GroupTypeEnum groupType;

}

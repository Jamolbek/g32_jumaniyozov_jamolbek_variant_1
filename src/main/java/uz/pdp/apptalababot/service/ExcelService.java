package uz.pdp.apptalababot.service;

import uz.pdp.apptalababot.model.Talaba;

import java.io.File;
import java.util.List;

public interface ExcelService {
    File writeExcel(List<Talaba> tasks);
}

package rating.service;

import rating.dao.WidDamageDAO;
import rating.domain.WidDamageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WidDamageService {
    @Autowired
    private WidDamageDAO widDamageDAO;

    public WidDamageEntity getById(final int wantedId) {
//        logger.debug("Retrieving widRoad "+wantedId);
        if (wantedId < 1) {
            WidDamageEntity widDamage = new WidDamageEntity();
            widDamage.setId(0);
            widDamage.setName("");
            return widDamage;
        }
        return widDamageDAO.getById(wantedId);
    }

    public List<WidDamageEntity> getAll() {
        //      logger.debug("Retrieving all widRoad ");
        return widDamageDAO.getAll();
    }

    public void saveRecord(WidDamageEntity widDamageEntity) {
        widDamageDAO.saveRecord(widDamageEntity);
    }

    public void deleteRecord(final int wantedId) {
        widDamageDAO.deleteRecord(wantedId);
    }

    public void insertRecord(final WidDamageEntity widDamageEntity) {
        widDamageDAO.saveRecord(widDamageEntity);
    }

}

package rating.service;

import rating.dao.WidBlobDAO;
import rating.domain.WidBlobEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WidBlobService {
    @Autowired
    private WidBlobDAO widBlobDAO;

    public WidBlobEntity getById(final int wantedId) {
//        logger.debug("Retrieving widRoad "+wantedId);
        if (wantedId < 1) {
            WidBlobEntity widBlob = new WidBlobEntity();
            widBlob.setId(0);
            widBlob.setName("");
			widBlob.setMime("");
            return widBlob;
        }
        return widBlobDAO.getById(wantedId);
    }

    public List<WidBlobEntity> getAll() {
        //      logger.debug("Retrieving all widRoad ");
        return widBlobDAO.getAll();
    }

    public void saveRecord(WidBlobEntity widBlobEntity) {
        widBlobDAO.saveRecord(widBlobEntity);
    }

    public void deleteRecord(final int wantedId) {
        widBlobDAO.deleteRecord(wantedId);
    }

    public void insertRecord(final WidBlobEntity widBlobEntity) {
        widBlobDAO.saveRecord(widBlobEntity);
    }

    public int getIdByName(String name) {
        return widBlobDAO.getIdByName(name.trim());
    }
    public void saveNewWidBlob(String name) {
           widBlobDAO.saveNewWidBlob(name.trim());

    }
}

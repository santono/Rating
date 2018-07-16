package rating.service;

import rating.dao.WidCoverDAO;
import rating.domain.WidCoverEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WidCoverService {
    @Autowired
    private WidCoverDAO widCoverDAO;

    public WidCoverEntity getById(final int wantedId) {
//        logger.debug("Retrieving widRoad "+wantedId);
        if (wantedId < 1) {
            WidCoverEntity widCover = new WidCoverEntity();
            widCover.setId(0);
            widCover.setName("");
            widCover.setPrice(0.00);
            return widCover;
        }
        return widCoverDAO.getById(wantedId);
    }

    public List<WidCoverEntity> getAll() {
        //      logger.debug("Retrieving all widRoad ");
        return widCoverDAO.getAll();
    }

    public void saveRecord(WidCoverEntity widCoverEntity) {
        widCoverDAO.saveRecord(widCoverEntity);
    }

    public void deleteRecord(final int wantedId) {
        widCoverDAO.deleteRecord(wantedId);
    }

    public void insertRecord(final WidCoverEntity widDamageEntity) {
        widCoverDAO.saveRecord(widDamageEntity);
    }

}

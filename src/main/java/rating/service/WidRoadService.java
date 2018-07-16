package rating.service;

import rating.dao.WidRoadDAO;
import rating.domain.WidRoadEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class WidRoadService {
    @Autowired
    private WidRoadDAO widRoadDAO;

    public WidRoadEntity getById(final Integer wantedId) {
//        logger.debug("Retrieving widRoad "+wantedId);
        if (wantedId < 1) {
            WidRoadEntity widRoad = new WidRoadEntity();
            widRoad.setId(0);
            widRoad.setName("");
            return widRoad;
        }
        return widRoadDAO.getById(wantedId);
    }

    public List<WidRoadEntity> getAll() {
        //      logger.debug("Retrieving all widRoad ");
        return widRoadDAO.getAll();
    }

    public void saveRecord(WidRoadEntity widRoadEntity) {
        widRoadDAO.saveRecord(widRoadEntity);
    }

    public void deleteRecord(final int wantedId) {
        widRoadDAO.deleteRecord(wantedId);
    }

    public void insertRecord(final WidRoadEntity widRoadEntity) {
        widRoadDAO.saveRecord(widRoadEntity);
    }


}

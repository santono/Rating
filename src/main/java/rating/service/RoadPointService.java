package rating.service;


import rating.dao.RoadPointDAO;
import rating.domain.RoadPointEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoadPointService {
    @Autowired
    private RoadPointDAO rpDAO;

    public RoadPointEntity getById(final int wantedId) {
        return rpDAO.getById(wantedId);
    }

    public List<RoadPointEntity> getAll() {
        return rpDAO.getAll();
    }

    public List<RoadPointEntity> getAllForRoad(int idroad) {
        return rpDAO.getAllForRoad(idroad);
    }

    public void saveRecord(RoadPointEntity roadPointEntity) {
        rpDAO.saveRecord(roadPointEntity);
    }

    public void deleteRecord(final int wantedId) {
        rpDAO.deleteRecord(wantedId);
    }

    public void insertRecord(final RoadPointEntity roadPointEntity) {
        rpDAO.insertRecord(roadPointEntity);
    }

    public int getCountPointsForRoad(int wantedId) {
        return rpDAO.getCountPointsForRoad(wantedId);
    }


}

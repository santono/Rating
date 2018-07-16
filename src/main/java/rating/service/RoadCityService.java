package rating.service;

import rating.dao.RoadCityDAO;
import rating.domain.RoadCityEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoadCityService {
    @Autowired
    private RoadCityDAO rcDAO;

    public RoadCityEntity getById(final int wantedId) {
        return rcDAO.getById(wantedId);
    }

    public List<RoadCityEntity> getAll() {
        return rcDAO.getAll();
    }

    public List<RoadCityEntity> getAllForRoad(int idroad) {
        return rcDAO.getAllForRoad(idroad);
    }

    public List<RoadCityEntity> getAllForCity(int idcity) {
        return rcDAO.getAllForCity(idcity);
    }

    public void saveRecord(RoadCityEntity roadCityEntity) {
        rcDAO.saveRecord(roadCityEntity);
    }

    public void deleteRecord(final int wantedId) {
        rcDAO.deleteRecord(wantedId);
    }

    public void insertRecord(final RoadCityEntity roadCityEntity) {
        rcDAO.insertRecord(roadCityEntity);
    }


}

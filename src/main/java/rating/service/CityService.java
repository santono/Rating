package rating.service;


import rating.dao.CityDAO;
import rating.domain.CityEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CityService {
    @Autowired
    private CityDAO cityDAO;

    public CityEntity getById(final int wantedId) {
        return cityDAO.getById(wantedId);
    }

    public List<CityEntity> getAll() {
        return cityDAO.getAll();
    }

    public void saveRecord(CityEntity cityEntity) {
        cityDAO.saveRecord(cityEntity);
    }

    public void deleteRecord(final int wantedId) {
        cityDAO.deleteRecord(wantedId);
    }

    public void insertRecord(final CityEntity cityEntity) {
        cityDAO.insertRecord(cityEntity);
    }


}

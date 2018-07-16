package rating.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rating.dao.PokazDAO;
import rating.domain.PokazEntity;

import java.util.List;

@Service
public class PokazService {
    @Autowired
    private PokazDAO pokazDAO;
    public PokazEntity getById(final int wantedId) {
        return pokazDAO.getById(wantedId);
    }

    public List<PokazEntity> getAll() {
        return pokazDAO.getAll();
    }

    public void saveRecord(PokazEntity pokazEntity) {
        pokazDAO.saveRecord(pokazEntity);
    }

    public void deleteRecord(final int wantedId) {
        pokazDAO.deleteRecord(wantedId);
    }

    public void insertRecord(final PokazEntity pokazEntity) {
        pokazDAO.insertRecord(pokazEntity);
    }

}

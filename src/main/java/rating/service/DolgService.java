package rating.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rating.dao.DolgDAO;
import rating.domain.DolgEntity;

import java.util.List;

@Service
public class DolgService {
    @Autowired
    private DolgDAO dolgDAO;
    public DolgEntity getById(final int wantedId) {
        return dolgDAO.getById(wantedId);
    }

    public List<DolgEntity> getAll() {
        return dolgDAO.getAll();
    }

    public void saveRecord(DolgEntity dolgEntity) {
        dolgDAO.saveRecord(dolgEntity);
    }

    public void deleteRecord(final int wantedId) {
        dolgDAO.deleteRecord(wantedId);
    }

    public void insertRecord(final DolgEntity dolgEntity) {
        dolgDAO.insertRecord(dolgEntity);
    }

}

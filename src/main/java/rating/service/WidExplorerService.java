package rating.service;

import rating.dao.WidExplorerDAO;
import rating.domain.WidExplorerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WidExplorerService {

    @Autowired
    private WidExplorerDAO widExplorerDAO;

    public WidExplorerEntity getById(final int wantedId) {
//        logger.debug("Retrieving widRoad "+wantedId);
        if (wantedId < 1) {
            WidExplorerEntity widExplorer = new WidExplorerEntity();
            widExplorer.setId(0);
            widExplorer.setName("");
            return widExplorer;
        }
        return widExplorerDAO.getById(wantedId);
    }

    public List<WidExplorerEntity> getAll() {
        //      logger.debug("Retrieving all widRoad ");
        return widExplorerDAO.getAll();
    }

    public void saveRecord(WidExplorerEntity widExplorerEntity) {
        widExplorerDAO.saveRecord(widExplorerEntity);
    }

    public void deleteRecord(final int wantedId) {
        widExplorerDAO.deleteRecord(wantedId);
    }

    public void insertRecord(final WidExplorerEntity widExplorerEntity) {
        widExplorerDAO.saveRecord(widExplorerEntity);
    }


}

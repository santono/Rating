package rating.service;

import rating.dao.StatisticDAO;
import rating.domain.StatisticEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticService {
    protected static Logger logger = Logger.getLogger("service");
    @Autowired
    StatisticDAO sDAO;

    public List<StatisticEntity> findAll() {
        return sDAO.findAll();
    }

    public StatisticEntity getById(int wantedId) {
        return sDAO.getById(wantedId);
    }

    public void saveRecord(StatisticEntity sEntity) {
        sDAO.saveRecord(sEntity);
    }
}

package rating.dao;


import org.springframework.stereotype.Component;
import rating.domain.RoadCityEntity;

import java.util.List;
@Component
public interface RoadCityDAO {
    public RoadCityEntity getById(final int wantedId);

    public List<RoadCityEntity> getAll();

    public List<RoadCityEntity> getAllForRoad(int idroad);

    public List<RoadCityEntity> getAllForCity(int idcity);

    public void saveRecord(RoadCityEntity roadCityEntity);

    public void deleteRecord(final int wantedId);

    public void insertRecord(final RoadCityEntity roadCityEntity);

}

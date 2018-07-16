package rating.dao;


import rating.domain.RoadDetEntity;

import java.util.List;

public interface RoadDetDAO {
    public RoadDetEntity getById(final int wantedId);

    public List<RoadDetEntity> getAll();

    public List<RoadDetEntity> getAllForRoad(int idroad);

    public List<RoadDetEntity> getAllForPodr(int idpodr);

    public void saveRecord(RoadDetEntity roadDetEntity);

    public void deleteRecord(final int wantedId);

    public void insertRecord(final RoadDetEntity roadDetEntity);

    public RoadDetEntity getSumDetForRoad(final int roadId);

    public RoadDetEntity getSumDetForWidRoad(final int shifridwr);

}

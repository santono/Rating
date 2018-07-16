package rating.dao;


import rating.domain.RoadPointEntity;

import java.util.List;

public interface RoadPointDAO {
    public RoadPointEntity getById(final int wantedId);

    public List<RoadPointEntity> getAll();

    public List<RoadPointEntity> getAllForRoad(int idroad);

    public void saveRecord(RoadPointEntity roadPointEntity);

    public void deleteRecord(final int wantedId);

    public void insertRecord(final RoadPointEntity roadPointEntity);

    public int getCountPointsForRoad(int wantedId);
}

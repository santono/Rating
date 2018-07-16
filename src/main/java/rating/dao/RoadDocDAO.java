package rating.dao;


import rating.domain.RoadDocEntity;

import java.util.List;

public interface RoadDocDAO {
    public RoadDocEntity getById(final int wantedId);

    public List<RoadDocEntity> getAll();

    public List<RoadDocEntity> getAllForRoad(int idroad);

    public List<RoadDocEntity> getAllForDet(int iddet);

    public void saveRecord(RoadDocEntity roadDocEntity);

    public void deleteRecord(final int wantedId);

    public void insertRecord(final RoadDocEntity roadDocEntity);

    public void updateDocument(String fileName,int id);

    public void updateDocument(byte[] bytes,int id);

    public byte[] getBlobAsBytes(long wantedId);

}

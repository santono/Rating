package rating.dao;


import rating.domain.WidRoadEntity;

import java.util.List;

public interface WidRoadDAO {
    public WidRoadEntity getById(final Integer wantedId);

    public List<WidRoadEntity> getAll();

    public void saveRecord(WidRoadEntity widRoadEntity);

    public void deleteRecord(final int wantedId);

    public void insertRecord(final WidRoadEntity widRoadEntity);
}

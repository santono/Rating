package rating.dao;


import rating.domain.DolgEntity;

import java.util.List;

public interface DolgDAO {
    public DolgEntity getById(final int wantedId);

    public List<DolgEntity> getAll();

    public void saveRecord(DolgEntity pokazEntity);

    public void deleteRecord(final int wantedId);

    public void insertRecord(final DolgEntity dolgEntity);

}

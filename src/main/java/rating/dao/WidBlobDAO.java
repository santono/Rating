package rating.dao;


import rating.domain.WidBlobEntity;

import java.util.List;

public interface WidBlobDAO {
    public WidBlobEntity getById(final int wantedId);

    public List<WidBlobEntity> getAll();

    public void saveRecord(WidBlobEntity widBlobEntity);

    public void deleteRecord(final int wantedId);

    public void insertRecord(final WidBlobEntity widBlobEntity);

    public int getIdByName(String name);

    public void saveNewWidBlob(String name);


}

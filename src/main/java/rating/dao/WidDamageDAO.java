package rating.dao;


import rating.domain.WidDamageEntity;

import java.util.List;

public interface WidDamageDAO {
    public WidDamageEntity getById(final int wantedId);

    public List<WidDamageEntity> getAll();

    public void saveRecord(WidDamageEntity widDamageEntity);

    public void deleteRecord(final int wantedId);

    public void insertRecord(final WidDamageEntity widDamageEntity);

}

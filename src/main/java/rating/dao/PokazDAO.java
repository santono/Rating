package rating.dao;


import rating.domain.PokazEntity;

import java.util.List;

public interface PokazDAO {
    public PokazEntity getById(final int wantedId);

    public List<PokazEntity> getAll();

    public void saveRecord(PokazEntity pokazEntity);

    public void deleteRecord(final int wantedId);

    public void insertRecord(final PokazEntity pokazEntity);

}

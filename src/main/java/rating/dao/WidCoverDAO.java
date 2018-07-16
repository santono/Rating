package rating.dao;


import rating.domain.WidCoverEntity;

import java.util.List;

public interface WidCoverDAO {
    public WidCoverEntity getById(final int wantedId);

    public List<WidCoverEntity> getAll();

    public void saveRecord(WidCoverEntity widCoverEntity);

    public void deleteRecord(final int wantedId);

    public void insertRecord(final WidCoverEntity widCoverEntity);

}

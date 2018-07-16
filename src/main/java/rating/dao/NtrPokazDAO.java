package rating.dao;


import rating.domain.NtrPokazEntity;

import java.util.List;

public interface NtrPokazDAO {
    public NtrPokazEntity getById(final int wantedId);

    public List<NtrPokazEntity> getAllForNtr(final int wantedId);

    public void saveRecord(NtrPokazEntity ntrPokazEntity);

    public void deleteRecord(final int wantedId);

    public void deleteRecordsForNtr(final int wantedId);

    public void insertRecord(final NtrPokazEntity ntrPokazEntity);
}

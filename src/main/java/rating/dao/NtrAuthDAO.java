package rating.dao;


import rating.domain.NtrAuthEntity;

import java.util.List;

public interface NtrAuthDAO {
    public NtrAuthEntity getById(final int wantedId);

    public List<NtrAuthEntity> getAllForNtr(final int wantedId);

    public void saveRecord(NtrAuthEntity ntrAuthEntity);

    public void deleteRecord(final int wantedId);

    public void insertRecord(final NtrAuthEntity ntrAuthEntity);

    public void deleteAllForOwner(int id);
}

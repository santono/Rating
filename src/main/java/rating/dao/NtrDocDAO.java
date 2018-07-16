package rating.dao;


import rating.domain.NtrDocEntity;

import java.util.List;

public interface NtrDocDAO {
    public NtrDocEntity getById(final int wantedId);

    public List<NtrDocEntity> getAllForNtr(int idntr);

    public void saveRecord(NtrDocEntity ntrDocEntity);

    public void deleteRecord(final int wantedId);

    public void deleteAllForOwner(int id);

    public void insertRecord(final NtrDocEntity ntrDocEntity);

    public void updateDocument(String fileName,int id);

    public void updateDocument(byte[] bytes,int id);

    public byte[] getBlobAsBytes(long wantedId);

    public void updateIdNtr(int idntr,int iddoc);

}

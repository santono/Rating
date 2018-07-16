package rating.dao;


import rating.domain.NtrEntity;
import rating.dto.SemanticUISearchDTO;
import rating.dto.SemanticUISearchItemDTO;

import java.util.List;

public interface NtrDAO {
    public NtrEntity getById(final int wantedId);

    public NtrEntity getByName(final String wantedName);

    public List<NtrEntity> getAll();

    public List<NtrEntity> getAllForPre(int shifrpre);

    public List<NtrEntity> getAllForNPR(int shifrnpr);

    public void saveRecord(NtrEntity ntrEntity);

    public void deleteRecord(final int wantedId);

    public void insertRecord(final NtrEntity ntrEntity);

    public void approveNtrRec(int idntr,int idUser,String fio);

    public void dismissApproveNtrRec(int idntr);

    public List<SemanticUISearchItemDTO> getSourceForSemanticUISearch(String wantedName);

}

package rating.dao;


import rating.domain.PodrEntity;
import rating.dto.PodrEntityDTO;
import rating.dto.PokazEntityDTO;

import java.util.List;

public interface PodrDAO {
    public PodrEntity getById(final Integer wantedId);

    public PodrEntity getByOwnerId(final Integer wantedId);

    public List<PodrEntity> getAll();

    public List<PodrEntity> getAllForOwner(int wantedId);

    public List<PodrEntityDTO> getAllForOwnerDTO(int wantedId);

    public void saveRecord(PodrEntity podrEntity);

    public void deleteRecord(final int wantedId);

    public void insertRecord(final PodrEntity podrEntity);

    public int getLevelById(final Integer wantedId);

    public List<PokazEntityDTO> getPokazAllForPre(final int wantedId,final int wantedY);
}

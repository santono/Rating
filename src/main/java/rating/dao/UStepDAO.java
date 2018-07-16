package rating.dao;


import rating.domain.UStepEntity;

import java.util.List;

public interface UStepDAO {
    public UStepEntity getById(final int wantedId);

    public List<UStepEntity> getAll();

    public void saveRecord(UStepEntity uStepEntity);

    public void deleteRecord(final int wantedId);

    public void insertRecord(final UStepEntity uStepEntity);


}

package rating.dao;



import rating.domain.WidExplorerEntity;

import java.util.List;

public interface WidExplorerDAO {
    public WidExplorerEntity getById(final int wantedId);

    public List<WidExplorerEntity> getAll();

    public void saveRecord(WidExplorerEntity widExplorerEntity);

    public void deleteRecord(final int wantedId);

    public void insertRecord(final WidExplorerEntity widExplorerEntity);

}

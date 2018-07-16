package rating.dao;

import rating.domain.RoadEntity;
import rating.dto.RoadRecDTO;
import rating.util.forbsgridcontroller.SortingClass;

import java.util.List;


public interface RoadDAO {
    public RoadEntity getById(final int wantedId);

    public List<RoadEntity> getAll();

    public List<RoadEntity> getAllForType(int kodtype);

    public List<RoadEntity> getAllForWidRoad(int shifridwr);

    public void saveRecord(RoadEntity roadEntity);

    public void deleteRecord(final int wantedId);

    public void insertRecord(final RoadEntity roadEntity);

    public List<RoadRecDTO> getListGroup();

    public List<RoadRecDTO> getPageListGroup(final int pageNo, final int pageRows, List<SortingClass> sorting, String whereStmnt);

    public int getCountRec();

    public int getCountRecForWantedShifrWR(int shifrWR);


    public int getCountRecWithFilter(String whereStmnt);
}

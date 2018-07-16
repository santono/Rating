package rating.dao;


import rating.domain.UsersRolesEntity;

import java.util.List;

public interface UsersRolesDAO {
    public UsersRolesEntity getById(final Integer wantedId);

    public List<UsersRolesEntity> getAll();

    public List<UsersRolesEntity> getAllForUser(final Integer wantedId);

    public List<String> getAllRoleNamesForUser(Integer wantedId);

    public void saveRecord(UsersRolesEntity urEntity);

    public void deleteRecord(final int wantedId);

    public void insertRecord(final UsersRolesEntity urEntity);

    public void deleteRecordsForUser(final int userId);

}

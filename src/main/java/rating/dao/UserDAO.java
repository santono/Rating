package rating.dao;


import rating.domain.UserEntity;
import rating.dto.ChgPwdDTO;
import rating.dto.UserNPRDTOForVUEList;

import java.util.List;


public interface UserDAO {
    public UserEntity getById(final int id);

    public UserEntity getByLogin(final String login);

    public int getCountByLogin(final String login);

    public void saveRecord(UserEntity uEntity);

    public void setVerified(UserEntity uEntity);

    public void setPassword(ChgPwdDTO up);

    public void deleteRecord(final int wantedId);

    public List<UserEntity> getAll();

    public List<UserEntity> getAllForUser(String whereStmnt);

    public List<UserEntity> getAllForUniFacKaf(int shifruni,int shifrfac,int shifrkaf);

    public List<UserEntity> getAllForPodr(int shifrPodr);

    public List<UserEntity> getPageForPodr(int shifrPodr,int pageNo,int pageSize,int order);

    public int getCountUserForPodr(int shifrPodr);

    public List<UserEntity> getAllForQuery(String query);

    public boolean checkLogin(String login);

}

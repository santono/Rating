package rating.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rating.dao.NtrAuthDAO;
import rating.domain.NtrAuthEntity;
import rating.util.ShortFio;

import java.util.List;

@Service
public class NtrAuthService {
    @Autowired
    private NtrAuthDAO ntrAuthDAO;
    @Autowired
    private UserService userService;
    public NtrAuthEntity getById(final int wantedId) {
        return ntrAuthDAO.getById(wantedId);
    }

    public List<NtrAuthEntity> getAllForNtr(final int wantedId) {
          return ntrAuthDAO.getAllForNtr(wantedId);
    }

    public void saveRecord(NtrAuthEntity ntrAuthEntity) {
        ntrAuthDAO.saveRecord(ntrAuthEntity);
    }

    public void deleteRecord(final int wantedId) {
        ntrAuthDAO.deleteRecord(wantedId);
    }

    public void insertRecord(final NtrAuthEntity ntrAuthEntity) {
        ntrAuthDAO.insertRecord(ntrAuthEntity);
    }
    public String getAuthorsForNtr(int idntr,int shifrnpr) {
        //shifrnpr =0    все иначе соавторы
        String retVal;
        List<NtrAuthEntity> l = getAllForNtr(idntr);
        if (l==null) retVal="";
        else
        if (l.size()<1) retVal="";
        else {
            int iCount;
            iCount=0;
            StringBuilder sb=new StringBuilder();
            for (NtrAuthEntity au:l) {
//                System.out.println("getauth: idntr ="+idntr+" au.getIdauth() "+au.getIdauth()+" shifrnpr="+shifrnpr);
                if (shifrnpr>0)
                    if (shifrnpr==au.getIdauth())
                        continue;
                if (iCount>0)sb.append(", ");
                iCount++;
                if (au.getIdauth()>0) {
                    sb.append(userService.getShortCompoundName(au.getIdauth()));
                } else 
                if (au.getName()!=null)
                    if (au.getName().trim().length()>0) {
                        String f=au.getName().trim();
                        sb.append(ShortFio.getShortFio(f));
                    }
            }
            retVal=sb.toString().trim();

        }
        
        return retVal;
    }
    public void deleteAllForOwner(int id) {
        ntrAuthDAO.deleteAllForOwner(id);
    }
}

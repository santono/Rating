package rating.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rating.dao.NtrPokazDAO;
import rating.domain.NtrPokazEntity;

import java.util.List;
@Service
public class NtrPokazService {
    @Autowired
    private NtrPokazDAO ntrPokazDAO;

    @Autowired
    private PokazService pokazService;

    public NtrPokazEntity getById(final int wantedId) {
        return ntrPokazDAO.getById(wantedId);
    }

    public List<NtrPokazEntity> getAllForNtr(final int wantedId) {
        return ntrPokazDAO.getAllForNtr(wantedId);
    }

    public void saveRecord(NtrPokazEntity ntrPokazEntity) {
        ntrPokazDAO.saveRecord(ntrPokazEntity);
    }

    public void deleteRecord(final int wantedId) {
        ntrPokazDAO.deleteRecord(wantedId);
    }

    public void deleteRecordsForNtr(final int wantedId) {
        ntrPokazDAO.deleteRecordsForNtr(wantedId);
    }
    

    public void insertRecord(final NtrPokazEntity ntrPokazEntity) {
        ntrPokazDAO.insertRecord(ntrPokazEntity);
    }
    public String getPokazForNtr(int idntr) {
        String retVal;
        List<NtrPokazEntity> l =getAllForNtr(idntr);
        if (l==null) retVal="";
        else
        if (l.size()<1) retVal="";
        else {
            int iCount;
            iCount=0;
            StringBuilder sb=new StringBuilder();
            for (NtrPokazEntity po:l) {
                if (po.getIdpokaz()>0) {
                    if (iCount>0)sb.append(", ");
                    iCount++;
                    sb.append(pokazService.getById(po.getIdpokaz()).getShortname());
                }
            }
            retVal=sb.toString().trim();
        }
        return retVal;
    }

}

package rating.service;

import rating.dao.PodrDAO;
import rating.domain.PodrEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rating.dto.ItemErrorDTO;
import rating.dto.PodrEntityDTO;
import rating.dto.PodrTreeDTO;
import rating.dto.PokazEntityDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class PodrService {
    @Autowired
    private PodrDAO podrDAO;

    public PodrEntity getById(final Integer wantedId) {
//        logger.debug("Retrieving podra "+wantedId);
        if (wantedId < 1) {
            PodrEntity podr = new PodrEntity();
            podr.setId(0);
            podr.setName("");
            podr.setShifrIdOwner(0);
            return podr;
        }
        return podrDAO.getById(wantedId);
    }
    public PodrEntity getByOwnerId(final Integer wantedOwnerId) {
//        logger.debug("Retrieving podra "+wantedId);
        return podrDAO.getByOwnerId(wantedOwnerId);
    }

    public List<PodrEntity> getAll() {
        //      logger.debug("Retrieving all kafedras ");
        return podrDAO.getAll();
    }


    public List<PodrEntity> getAllForOwner(int wantedId) {
        //      logger.debug("Retrieving all kafedras ");
        return podrDAO.getAllForOwner(wantedId);
    }

    public List<PodrEntityDTO> getAllForOwnerDTO(int wantedId) {
        return podrDAO.getAllForOwnerDTO(wantedId);
    }
    public PodrTreeDTO getAllForOwnerDTORecursive(int wantedId) {
        List<PodrTreeDTO> childs = new ArrayList<PodrTreeDTO>();
        PodrTreeDTO   treeDTO;
        PodrEntityDTO podrEntityDTO;
        PodrEntity pe = podrDAO.getById(wantedId);
        if (pe!=null) {
            podrEntityDTO=new PodrEntityDTO();
            podrEntityDTO.setId(pe.getId());
            podrEntityDTO.setCanbedeleted(false);
            podrEntityDTO.setName(pe.getName());
            podrEntityDTO.setShortName(pe.getShortName());
            podrEntityDTO.setShifrIdOwner(pe.getShifrIdOwner());
            treeDTO = new PodrTreeDTO();
            treeDTO.setPodrEntityDTO(podrEntityDTO);
            treeDTO.setNodes(null);
        } else {
            podrEntityDTO=null;
            treeDTO = null;
            return treeDTO;
        }
        List<PodrEntity> peList=podrDAO.getAllForOwner(wantedId);
        if (peList==null) {
            treeDTO.setNodes(null);
            return treeDTO;
        }
        if (peList.size()<1) {
            treeDTO.setNodes(null);
            return treeDTO;
        }
        for(PodrEntity pe1:peList) {
            PodrTreeDTO treeDTO1=getAllForOwnerDTORecursive((int)pe1.getId());
            childs.add(treeDTO1);
        }
        treeDTO.setNodes(childs);
        return treeDTO;
    }


    public void saveRecord(PodrEntity podrEntity) {
        podrDAO.saveRecord(podrEntity);
    }

    public void deleteRecord(final int wantedId) {
        podrDAO.deleteRecord(wantedId);
    }

    public void insertRecord(final PodrEntity widRoadEntity) {
        podrDAO.saveRecord(widRoadEntity);
    }

    public List<ItemErrorDTO> getListDTOForOwner(int wantedId) {
          List<PodrEntity> l=podrDAO.getAllForOwner(wantedId);
          List<ItemErrorDTO> list=new ArrayList<ItemErrorDTO>();
        for (PodrEntity podr:l) {
            ItemErrorDTO itemErrorDTO=new ItemErrorDTO((int) podr.getId(),podr.getName(),"");
            list.add(itemErrorDTO);

        }
        Collections.sort(list);
        return list;
    }
    public int getLevelById(final Integer wantedId) {
        return podrDAO.getLevelById(wantedId);
    }
    public List<Integer> getUniFacKaf(int shifrPre) {
        List<Integer> retVal = new ArrayList<Integer>();
        PodrEntity pEntity   = getById(shifrPre);
        int level    = getLevelById(shifrPre);
        int shifrUni = 0 ;
        int shifrFac = 0 ;
        int shifrKaf = 0 ;
        if (level==3) {
            shifrKaf = shifrPre;
            shifrFac = (int) pEntity.getShifrIdOwner();
            shifrUni = (int) getById(shifrFac).getShifrIdOwner();
        } else
        if (level==2) {
            shifrKaf = 0;
            shifrFac = shifrPre;
            shifrUni = (int) pEntity.getShifrIdOwner();
        } else
        if (level==1) {
            shifrKaf = 0;
            shifrFac = 0;
            shifrUni = shifrPre;
        }
        retVal.add(shifrUni);
        retVal.add(shifrFac);
        retVal.add(shifrKaf);
        return retVal;
    }
    public String buildCompoundName(int shifrPre) {
           StringBuilder sb=new StringBuilder("");
           PodrEntity pe=podrDAO.getById(shifrPre);
           if (pe==null)
               return sb.toString();
           sb.append(pe.getName().trim());
           int shifrId = 0;
           shifrId     = (int) pe.getShifrIdOwner();
           while (shifrId>0) {
                 shifrId = (int) pe.getShifrIdOwner();
                 pe=podrDAO.getById(shifrId);
                 if (pe == null)
                    break;
                 sb.insert(0," / ");
                 sb.insert(0,pe.getName().trim());
                 shifrId = (int) pe.getShifrIdOwner();
           }
           return sb.toString();
    }
    public List<PokazEntityDTO> getPokazAllForPre(final int wantedId,final int wantedY) {
           return podrDAO.getPokazAllForPre(wantedId,wantedY);
    }

}

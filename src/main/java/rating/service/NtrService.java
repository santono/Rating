package rating.service;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rating.dao.NtrDAO;
import rating.domain.NtrAuthEntity;
import rating.domain.NtrEntity;
import rating.domain.UserEntity;
import rating.dto.*;
import rating.util.NormalizeString;
import rating.util.UserInfo;
import rating.util.Util;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class NtrService {
    @Autowired
    private NtrDAO ntrDAO;
    @Autowired
    private NtrDocService ntrDocService;
    @Autowired
    private NtrAuthService ntrAuthService;
    @Autowired
    private NtrPokazService ntrPokazService;
    @Autowired
    private PodrService podrService;
    @Autowired
    UserService userService;
    public NtrEntity getById(final int wantedId) {
        return ntrDAO.getById(wantedId);
    }

    public List<NtrEntity> getAll(){
        return ntrDAO.getAll();
    }

    public void saveRecord(NtrEntity ntrEntity) {
        ntrDAO.saveRecord(ntrEntity);
    }

    public int saveNtrRecFromJSONDTO(NtrRecFromJSONDTO ntrRecFromJSONDTO) {
        if (ntrRecFromJSONDTO==null) return 0;
        UserInfo userInfo=new UserInfo();
        int shifrIdUser;
        shifrIdUser=(int)userInfo.getUserDTO().getId();
        int  shifrPre;
        shifrPre=(int) userInfo.getUserDTO().getShifrPodr();
        int retVal=0;
        if (ntrRecFromJSONDTO.getId()<1) {
            NtrEntity ntrEntity = new NtrEntity();
            ntrEntity.setId(ntrRecFromJSONDTO.getId());
            String name;
            name= NormalizeString.executeNormalizeString(ntrRecFromJSONDTO.getName());
//            ntrEntity.setName(ntrRecFromJSONDTO.getName());
            ntrEntity.setName(name);
            ntrEntity.setParametry(ntrRecFromJSONDTO.getParametry());
            ntrEntity.setShifrwrk(shifrIdUser);
            ntrEntity.setFiowrk(userInfo.getUserDTO().getFIO());
            if (ntrRecFromJSONDTO.getDatePublJava()!=null)
                ntrEntity.setDatepubl(ntrRecFromJSONDTO.getDatePublJava());
            if (ntrRecFromJSONDTO.getShifrPre()<1)
                ntrEntity.setShifrpre(shifrPre);
            ntrEntity.setIdRinc(ntrRecFromJSONDTO.getIdRinc());
            ntrEntity.setHrefRinc(ntrRecFromJSONDTO.getHrefRinc());
            saveRecord(ntrEntity);
            retVal=ntrEntity.getId();
        } else {
            NtrEntity ntrEntity=getById(ntrRecFromJSONDTO.getId());
            String name;
            name= NormalizeString.executeNormalizeString(ntrRecFromJSONDTO.getName());
//            ntrEntity.setName(ntrRecFromJSONDTO.getName());
            ntrEntity.setName(name);
            ntrEntity.setParametry(ntrRecFromJSONDTO.getParametry());
            ntrEntity.setShifrwrk(shifrIdUser);
            ntrEntity.setFiowrk(userInfo.getUserDTO().getFIO());
            if (ntrRecFromJSONDTO.getDatePublJava()!=null)
                ntrEntity.setDatepubl(ntrRecFromJSONDTO.getDatePublJava());
            if (
                (shifrPre>0) &&
                (ntrEntity.getShifrpre()<=0)
               )
               ntrEntity.setShifrpre(shifrPre);
            ntrEntity.setIdRinc(ntrRecFromJSONDTO.getIdRinc());
            ntrEntity.setHrefRinc(ntrRecFromJSONDTO.getHrefRinc());
            saveRecord(ntrEntity);
            retVal=ntrEntity.getId();
        }
        return retVal;
    }

    public void deleteRecord(final int wantedId) {
        ntrAuthService.deleteAllForOwner(wantedId);
        ntrDocService.deleteAllForOwner(wantedId);
        ntrPokazService.deleteRecordsForNtr(wantedId);
        ntrDAO.deleteRecord(wantedId);
    }
    

    public void insertRecord(final NtrEntity ntrEntity) {
         ntrDAO.insertRecord(ntrEntity);
    }
    
    public List<NtrRecDTO> getNtrListForPre(int shifrpre) {
        List<NtrEntity> ntrList=ntrDAO.getAllForPre(shifrpre);
        List<NtrRecDTO> lrec=makeNtrListDTO(ntrList,0);

        return lrec;
    }

    public List<NtrRecDTO> getPageNtrListForPre(int kind,int shifrpre,int yfrom,int yto,int pageNo,int pageSize,int order,int shifridnprforfilter,int shifriddetforfilter) {
//        List<NtrEntity> ntrList;
//        if (pageNo<1) {
//            ntrList = ntrDAO.getAllForPre(shifrpre);
//        } else {
//            ntrList = ntrDAO.getPageForPre(shifrpre,pageNo,pageSize,order);
//        }
//        List<NtrRecDTO> lrec=makeNtrListDTO(ntrList,0);
        List<NtrRecDTO> lrec=ntrDAO.getPageForPreFromFn(kind,shifrpre,yfrom,yto,pageNo,pageSize,order,shifridnprforfilter,shifriddetforfilter);

        return lrec;
    }
    public int getCountNtr(int kind,int shifrpre,int yfr,int yto,int shifridnprforfilter,int shifriddetforfilter) {
        // mode=0 - predp mode=1 user
        int retVal;
        retVal=ntrDAO.getCountNtr(kind,shifrpre,yfr,yto,shifridnprforfilter,shifriddetforfilter);
        return retVal;
    }
    
    public List<NtrRecDTO> getNtrListForNPR(int shifrnpr) {
        List<NtrEntity> ntrList=ntrDAO.getAllForNPR(shifrnpr);
//        List<NtrRecDTO> lrec=makeNtrListDTO(ntrList,shifrnpr);
        List<NtrRecDTO> lrec=ntrDAO.getPageForPreFromFn(1,shifrnpr,1980,2030,1,500,0,0,0);

        return lrec;
    }
    private List<NtrRecDTO> makeNtrListDTO(List<NtrEntity> ntrList,int shifrnpr) {
        List<NtrRecDTO> lrec=new ArrayList<NtrRecDTO>();
        for (NtrEntity ntrEntity:ntrList){
            NtrRecDTO ntrRecDTO=new NtrRecDTO();
            ntrRecDTO.setId(ntrEntity.getId());
            String s="";
            if (ntrEntity.getDatepubl()!=null)
                if (ntrEntity.getDatepubl().get(Calendar.YEAR)>1950) {
                    SimpleDateFormat sdf=new SimpleDateFormat("dd-M-YYYY");
                    try {
                        s=sdf.format(ntrEntity.getDatepubl().getTime());
                    } catch (IllegalArgumentException e)  {
                        s="";
                    }
                }
            ntrRecDTO.setDataapproved(s);
            if (ntrEntity.getStatus()==1) {
                ntrRecDTO.setApproved("Подтверждено");
            } else {
                ntrRecDTO.setApproved("");
            }
            ntrRecDTO.setName(ntrEntity.getName());
            ntrRecDTO.setHasattachement(getHasAttachement(ntrEntity.getId()));
            ntrRecDTO.setAuthors(getAuthors(ntrEntity.getId(),shifrnpr));
            ntrRecDTO.setParametry(ntrEntity.getParametry());
            ntrRecDTO.setAmntOfImages(getAmntOfImagesForNtr(ntrEntity.getId()));
            ntrRecDTO.setAmntOfDocs(getAmntOfDocsForNtr(ntrEntity.getId()));
            ntrRecDTO.setFioapproved(ntrEntity.getFioapprove());
            ntrRecDTO.setPokaz(ntrPokazService.getPokazForNtr(ntrEntity.getId()));
            s="";
            if (ntrEntity.getShifrpre()>0)  {
                s = podrService.getById(ntrEntity.getShifrpre()).getShortName();
                if ((s==null) || (s.trim().length()<1))
                    s = podrService.getById(ntrEntity.getShifrpre()).getName();
            }
            ntrRecDTO.setNamepre(s);
            s="";
            s=ntrPokazService.getPokazForNtr(ntrEntity.getId());
            lrec.add(ntrRecDTO);
        }
        return lrec;

    }

    public boolean getHasAttachement(int id) {
        int i;
        i=ntrDocService.getAnmtOfDocForNtr(id);
        boolean retVal=false;
        if (i>0) {
            retVal=true;
        }
        return retVal;
    }
    public String getAuthors(int id,int shifrnpr) {
        return ntrAuthService.getAuthorsForNtr(id,shifrnpr);
    }
    public void saveNtrAuthorsList(NtrAuthorsDTO ntrAuthorsDTO) {
        if (ntrAuthorsDTO==null) {
            return;
        }
        int idntr=ntrAuthorsDTO.getId();
        if (idntr<1) {
            return;
        }
        NtrEntity ntrEntity=getById(idntr);
        if (ntrEntity==null) {
            return;
        }
        ntrAuthService.deleteAllForOwner(idntr);
        if (ntrAuthorsDTO.getAuthors()==null) {
            return;
        }
        if (ntrAuthorsDTO.getAuthors().length<1) {
            return;
        }
        for (NtrAuthorsDetDTO ntrAuthorsDetDTO:ntrAuthorsDTO.getAuthors()) {
            NtrAuthEntity ntrAuthEntity=new NtrAuthEntity();
            ntrAuthEntity.setAmode(ntrAuthorsDetDTO.getAmode());
            ntrAuthEntity.setIdntr(idntr);
            ntrAuthEntity.setProcent(ntrAuthorsDetDTO.getProcent());
            if (ntrAuthorsDetDTO.getId()>0)
                ntrAuthEntity.setIdauth(ntrAuthorsDetDTO.getId());
            if (ntrAuthorsDetDTO.getName()!=null) 
                if (ntrAuthorsDetDTO.getName().trim().length()>0)
                    ntrAuthEntity.setName(ntrAuthorsDetDTO.getName());
            ntrAuthService.saveRecord(ntrAuthEntity);
        }
    }
    public int getAmntOfImagesForNtr(int idntr) {
        int retVal;
        retVal=0;
        retVal = ntrDocService.getAnmtOfImagesForNtr(idntr);
        if (retVal<0) {
            retVal=0;
        }
        return retVal;
    }
    public int getAmntOfDocsForNtr(int idntr) {
        int retVal;
        retVal=0;
        retVal = ntrDocService.getAnmtOfDocForNtr(idntr);
        if (retVal<0) {
            retVal=0;
        }
        return retVal;
    }
    public void approveNtrRec(int idntr,int idUser) {
        String fio;
        UserEntity userEntity = userService.getById(idUser);
        try {
           fio = userEntity.getFam().trim()+" "+userEntity.getNam().trim()+" "+userEntity.getOtc().trim();
        } catch (NullPointerException npe) {
            fio = userEntity.getName();
        }
        ntrDAO.approveNtrRec(idntr,idUser,fio);

    }
    public void dismissApproveNtrRec(int idntr) {
        ntrDAO.dismissApproveNtrRec(idntr);

    }
    public int chkExistsSuchNTR(String newName) {
        int retVal=0;
        if (newName==null)
            return retVal;
        if (newName.trim().length()<1)
            return retVal;
        String name;
        name=NormalizeString.executeNormalizeString(newName);
        String n=name+"\n";
//        try {
//          System.out.println("chkExistsSuchNTR="+n.getBytes("cp866"));
//        } catch (Exception e) {}
        NtrEntity ne=ntrDAO.getByName(name);
        if (ne!=null)  {
            retVal=ne.getId();
        }
        return retVal;
    }
    public SemanticUISearchDTO getSourceForSemanticUISearch(String wantedName) {
        List<SemanticUISearchItemDTO> ls=ntrDAO.getSourceForSemanticUISearch(wantedName);
        if (ls==null) {
            return null;
        }
        SemanticUISearchDTO susd=new SemanticUISearchDTO();
        susd.setTotalCount(ls.size());
        susd.setIncompleteSearch(true);
        for (SemanticUISearchItemDTO item:ls) {
            int idntr=item.getId();
            if (idntr>0) {
               item.setDescription(getAuthors(idntr,0));
            } else {
                item.setDescription("Автор не указан.");
            }

        }
        susd.setItems(ls);
        return susd;
    }

    public JRDataSource getPokazDataSource(int shifrPre) {
        int lineNo=0;
        int y=Util.getCurrentYear();
        List<PokazListForJasperReportDTO> tPokazList=new ArrayList<PokazListForJasperReportDTO>();
        //    logger.debug("Amnt of road in datasource= "+roadService.getAllForWidRoad(shifridwr).size());
        List<PokazEntityDTO> pl=podrService.getPokazAllForPre(shifrPre,y);
        System.out.println("Amnt of rec for reports="+pl.size());
        for (PokazEntityDTO re:pl) {
            PokazListForJasperReportDTO reJr=new PokazListForJasperReportDTO();
            reJr.setName(re.getName());
            reJr.setLineno(re.getLineno());
            String amnt;
            if (Math.abs(re.getAmnt())<0.001) {
                amnt="";
            }
            else
            if (re.getLineno()==31 || (re.getAmnt()-Math.floor(re.getAmnt()))>0.01) {
                amnt= Util.setPrecision(re.getAmnt(), 2);
            }
            else {
                amnt=Util.setPrecision(re.getAmnt(),0);
            }
            reJr.setAmnt(amnt);
            if (Math.abs(re.getAmntn())<0.001) {
                amnt="";
            }
            else
            if (re.getLineno()==31 || (re.getAmntn()-Math.floor(re.getAmntn()))>0.01) {
                amnt= Util.setPrecision(re.getAmntn(), 2);
            }
            else {
                amnt=Util.setPrecision(re.getAmntn(),0);
            }
            reJr.setAmntn(amnt);
            tPokazList.add(reJr);

        }



        Collections.sort(tPokazList, new Comparator<PokazListForJasperReportDTO>() {
            @Override
            public int compare(PokazListForJasperReportDTO o1, PokazListForJasperReportDTO o2) {
                return o1.compareTo(o2);
            }
        });
//        logger.debug("Amnt of rec in list= " + tRDJrList.size());

        //    logger.debug("amount of groups="+lg.size());
        JRDataSource ds = new JRBeanCollectionDataSource(tPokazList);
        return ds;
    }

}

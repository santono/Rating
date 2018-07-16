package rating.service;

import rating.dao.RoadDAO;
import rating.domain.RoadEntity;
import rating.dto.RoadListForJasperReportDTO;
import rating.dto.RoadRecDTO;
import rating.util.forbsgridcontroller.SortingClass;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class RoadService {

    @Autowired
    private RoadDAO roadDAO;

    @Autowired
    private RoadPointService rpService;

    public RoadEntity getById(final int wantedId) {
        return roadDAO.getById(wantedId);
    }

    public List<RoadEntity> getAll() {
        return roadDAO.getAll();
    }

    public List<RoadEntity> getAllForType(int kodtype) {
        return roadDAO.getAllForType(kodtype);
    }

    public List<RoadEntity> getAllForWidRoad(int shifridwr) {
        return roadDAO.getAllForWidRoad(shifridwr);
    }

    public void saveRecord(RoadEntity roadEntity) {
        roadDAO.saveRecord(roadEntity);
    }

    public void deleteRecord(final int wantedId) {
        roadDAO.deleteRecord(wantedId);
    }

    public void insertRecord(final RoadEntity roadEntity) {
        roadDAO.insertRecord(roadEntity);
    }

    public List<RoadRecDTO> getListRoads() {
        List<RoadRecDTO> l = new ArrayList<RoadRecDTO>();
        for (RoadEntity r : getAll()) {
            RoadRecDTO rDTO = new RoadRecDTO();
            rDTO.setId(r.getId());
            rDTO.setKod(r.getKod());
            rDTO.setCode(r.getCode());
            rDTO.setName(r.getName());
            rDTO.setKodtype(r.getKodtype());
            rDTO.setDlina(r.getDlina());
            rDTO.setShifridwr(r.getShifridwr());
            rDTO.setIndex(r.getIndex());
            rDTO.setMenur("");
            l.add(rDTO);
        }
        return l;
    }

    public List<RoadRecDTO> getListRoadsGos() {
        List<RoadRecDTO> l = new ArrayList<RoadRecDTO>();
        for (RoadEntity r : getAllForType(1)) {
            RoadRecDTO rDTO = new RoadRecDTO();
            rDTO.setId(r.getId());
            rDTO.setKod(r.getKod());
            rDTO.setCode(r.getCode());
            rDTO.setName(r.getName());
            rDTO.setKodtype(r.getKodtype());
            rDTO.setDlina(r.getDlina());
            rDTO.setShifridwr(r.getShifridwr());
            rDTO.setIndex(r.getIndex());
            rDTO.setMenur("");
            l.add(rDTO);
        }
        return l;
    }


    public List<RoadRecDTO> getPageListRoad(final int pageNo, final int pageRows, List<SortingClass> sorting, String whereStmnt) {
        //   logger.debug("Count of rec="+groupDAO.getListGroup().size());
        StringBuilder sb;
        List<RoadRecDTO> grlDTO = roadDAO.getPageListGroup(pageNo, pageRows, sorting, whereStmnt);
        for (RoadRecDTO g : grlDTO) {
            int countPoints = rpService.getCountPointsForRoad(g.getId());
            sb = new StringBuilder();
            sb.append("<nobr>");
            sb.append("<button type=\"button\" class=\"btn btn-primary btn-xs editRoad\" onclick=\"javascript:return editRoad(" + g.getId() + ");\"><span class=\"glyphicon glyphicon-pencil\"></span></button>");
            sb.append("<button type=\"button\" class=\"btn btn-primary btn-xs browseRoadPoint\" idroad=\"" + g.getId() + "\" onclick=\"javascript:return browseRoadPoints(" + g.getId() + ");\"><span class=\"glyphicon glyphicon-th\"></span></button>");
            sb.append("<button type=\"button\" class=\"btn btn-primary btn-xs browseRoadDet\" idroad=\"" + g.getId() + "\" onclick=\"javascript:return browseRoadDets(" + g.getId() + ");\"><span class=\"glyphicon glyphicon-list\"></span></button>");
            sb.append("<button type=\"button\" class=\"btn btn-primary btn-xs browseRoadDoc\" idroad=\"" + g.getId() + "\" onclick=\"javascript:return browseRoadDocs(" + g.getId() + ");\"><span class=\"glyphicon glyphicon-file\"></span></button>");
            if (countPoints > 0) {
                sb.append("<button type=\"button\" class=\"btn btn-primary btn-xs browseRoadMap\" idroad=\"" + g.getId() + "\" dlina=\"" + g.getDlina() + "\"><span class=\"glyphicon glyphicon-globe\"></span></button>");
            }
            //     sb.append("<button type=\"button\" class=\"btn btn-xs delrecbutton\" onclick=\"deleteRoad("+g.getId()+","+g.getName()+")\"   idroad=\""+g.getId()+"\" nameroad=\""+g.getName()+"\"><span class=\"glyphicon glyphicon-remove\"></span></button>");
            sb.append("</nobr>");
            g.setMenur(sb.toString());
        }
        return grlDTO;
    }

    public int getCountRec() {
        return roadDAO.getCountRec();
    }

    public int getCountRecForWantedShifrWR(int shifrWR) {
        return roadDAO.getCountRecForWantedShifrWR(shifrWR);
    }

    public int getCountRecWithFilter(String whereStmnt) {
        return roadDAO.getCountRecWithFilter(whereStmnt);
    }

    public JRDataSource getDataSource(int shifridwr) {
        int wantedIdRoad;
        int lineNo=0;
        int currDRSU=0;
        List<RoadListForJasperReportDTO> tRoadList=new ArrayList<RoadListForJasperReportDTO>();
    //    logger.debug("Amnt of road in datasource= "+roadService.getAllForWidRoad(shifridwr).size());

        for (RoadEntity re:getAllForWidRoad(shifridwr)) {
            wantedIdRoad=re.getId();
            RoadListForJasperReportDTO reJr=new RoadListForJasperReportDTO();
            reJr.setName(re.getName());
            reJr.setCode(re.getCode());
            reJr.setLineno(++lineNo);
            reJr.setDlina(re.getDlina());
            tRoadList.add(reJr);

        }



        Collections.sort(tRoadList, new Comparator<RoadListForJasperReportDTO>() {
            @Override
            public int compare(RoadListForJasperReportDTO o1, RoadListForJasperReportDTO o2) {
                return o1.compareTo(o2);
            }
        });
//        logger.debug("Amnt of rec in list= " + tRDJrList.size());

        //    logger.debug("amount of groups="+lg.size());
        JRDataSource ds = new JRBeanCollectionDataSource(tRoadList);
        return ds;
    }
    
    public int getCountRoadsForWantedWR(int wantedWr) {
        int retVal=getCountRecForWantedShifrWR(wantedWr);
        return retVal;
    }
    

}

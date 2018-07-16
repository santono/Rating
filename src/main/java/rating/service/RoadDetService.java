package rating.service;

import rating.dao.RoadDetDAO;
import rating.domain.RoadDetEntity;
import rating.domain.RoadEntity;
import rating.dto.ColsDTO;
import rating.dto.ItemRDColsDTO;
import rating.dto.RoadDetDTO;
import rating.dto.RoadDetForJasperReportDTO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.text.DecimalFormat;
import java.util.*;

@Service
public class RoadDetService {

    protected static Logger logger = Logger.getLogger("controller");


    @Autowired
    private RoadDetDAO rdDAO;

    @Autowired
    private PodrService podrService;

    @Autowired
    private RoadService roadService;

    public RoadDetEntity getById(final int wantedId) {
        return rdDAO.getById(wantedId);
    }

    public RoadDetEntity getSumDetForRoad(final int roadId) {
        return rdDAO.getSumDetForRoad(roadId);
    }

    public RoadDetEntity getSumDetForWidRoad(final int shifridwr) {
        return rdDAO.getSumDetForWidRoad(shifridwr);
    }

    public List<RoadDetEntity> getAll() {
        return rdDAO.getAll();
    }

    public List<RoadDetEntity> getAllForRoad(int idroad) {
        return rdDAO.getAllForRoad(idroad);
    }

    public List<RoadDetEntity> getAllForPodr(int idpodr) {
        return rdDAO.getAllForPodr(idpodr);
    }

    public void saveRecord(RoadDetEntity roadDetEntity) {
        rdDAO.saveRecord(roadDetEntity);
    }

    public void deleteRecord(final int wantedId) {
        rdDAO.deleteRecord(wantedId);
    }

    public void insertRecord(final RoadDetEntity roadDetEntity) {
        rdDAO.insertRecord(roadDetEntity);
    }

    public List<RoadDetDTO> getAllScreenDetRowsForRoad(int wantedid) {
           StringBuilder sb;
           List<RoadDetDTO> listDTO=new ArrayList<RoadDetDTO>();
           int currPodr=0;
           for (RoadDetEntity rde:getAllForRoad(wantedid)) {
                if (currPodr!=rde.getIdpodr()) {
                    RoadDetDTO rdd=new RoadDetDTO();
                   currPodr=(int) rde.getIdpodr();
                    rdd.setName(podrService.getById(currPodr).getShortName());
                    rdd.setMenur(" ");
                    listDTO.add(rdd);
                }
                RoadDetDTO rdd=new RoadDetDTO();
                String name=getName(rde);
                sb = new StringBuilder();
//                sb.append("<nobr>");
                sb.append("<button type=\"button\" class=\"btn btn-primary btn-xs editRec\" onclick=\"javascript:return editRec(" + rde.getId() + ");\"><span class=\"glyphicon glyphicon-pencil\"></span></button>");
                sb.append("<button type=\"button\" class=\"btn btn-primary btn-xs deleteRecf\" onclick=\"javascript:return deleteRecf(" + rde.getId() + ",&quot;"+name.trim()+"&quot;);\"><span class=\"glyphicon glyphicon-remove\"></span></button>");
                //     sb.append("<button type=\"button\" class=\"btn btn-xs delrecbutton\" onclick=\"deleteRoad("+g.getId()+","+g.getName()+")\"   idroad=\""+g.getId()+"\" nameroad=\""+g.getName()+"\"><span class=\"glyphicon glyphicon-remove\"></span></button>");
//                sb.append("</nobr>");
                rdd.setMenur(sb.toString());
               rdd.setName(name);
               rdd.setComment(rde.getComment());
                rdd.setWsegodorog(rde.getWsegodorog());
                rdd.setWsegodoroghardcover(rde.getWsegodoroghardcover());
                rdd.setProcent(rde.getProcent());
                rdd.setCementbeton(rde.getCementbeton());
                rdd.setAsfaltbeton(rde.getAsfaltbeton());
                rdd.setChernshosse(rde.getChernshosse());
                rdd.setBeloeshosse(rde.getBeloeshosse());
                rdd.setBruschatka(rde.getBruschatka());
                rdd.setDegtegrunt(rde.getDegtegrunt());
                rdd.setGruntovye(rde.getGruntovye());
                rdd.setPokrkat1(rde.getPokrkat1());
                rdd.setPokrkat2(rde.getPokrkat2());
                rdd.setPokrkat3(rde.getPokrkat3());
                rdd.setPokrkat4(rde.getPokrkat4());
                rdd.setPokrkat5(rde.getPokrkat5());
                rdd.setMostsht(rde.getMostsht());
                rdd.setMostpm(rde.getMostpm());
                rdd.setMostshtder(rde.getMostshtder());
                rdd.setMostpmder(rde.getMostpmder());
                rdd.setTrubysht(rde.getTrubysht());
                rdd.setTrubypm(rde.getTrubypm());
                listDTO.add(rdd);
           }
           
           return listDTO;
    }
    
    
    private int getFrac(double d) {
        DecimalFormat df = new DecimalFormat("#.#####################################################################");
        String s;
        String str = String.valueOf(df.format(d));
        int index = str.lastIndexOf(",");
        if (index<0) {
            index = str.lastIndexOf(".");
        }
        if (index<0) {
            s = "0";
        } else {
           s=str.substring(index+1,str.length());
        }    
        int retVal=Integer.parseInt(s);
        return retVal;
    }
    private String getMeters(int m) {
        StringBuffer sb=new StringBuffer(""+m);
        if (m>1000) {
            m=m % 1000;
        }
        if (m<10) {
            sb=sb.insert(0, "00");
        } else
        if (m<100) {
            sb=sb.insert(0, "0");
        }



        return sb.toString();
    }
    
    private String getName(RoadDetEntity rde) {
        int kmfr=(int) Math.floor(rde.getPosstart());
        String mfr=getMeters(getFrac(rde.getPosstart()));
        int kmto=(int) Math.floor(rde.getPosend());
        String mto=getMeters(getFrac(rde.getPosend()));
        String name="км."+kmfr+"+"+mfr+" - "+kmto+"+"+mto;
        return name;
    }

    public JRDataSource getDataSource(int shifridwr) {
        int wantedIdRoad;
        int lineNo=0;
        int rowNum=0;
        int currDRSU=0;
        List<RoadDetForJasperReportDTO> tRDJrList=new ArrayList<RoadDetForJasperReportDTO>();
        logger.debug("Amnt of road in datasource= "+roadService.getAllForWidRoad(shifridwr).size());
        
        for (RoadEntity re:roadService.getAllForWidRoad(shifridwr)) {
           wantedIdRoad=re.getId();
           RoadDetEntity tRD;
           RoadDetForJasperReportDTO tRDJr=new RoadDetForJasperReportDTO();
           tRDJr.setName(roadService.getById(re.getId()).getCode()+" "+roadService.getById(re.getId()).getName());
           tRDJr.setRowNum(++rowNum);
           tRDJr.setKindForPrint(1);  // Название дороги
           tRDJrList.add(tRDJr);

           List<RoadDetEntity> tRDList= getAllForRoad(wantedIdRoad);

           for (RoadDetEntity tRDet : tRDList) {
               if (currDRSU!=tRDet.getIdpodr()) {
                  currDRSU=(int)tRDet.getIdpodr();
                  RoadDetForJasperReportDTO tRDDRSUJr=new RoadDetForJasperReportDTO();
                  tRDDRSUJr.setName(podrService.getById(currDRSU).getName());
                  tRDDRSUJr.setRowNum(++rowNum);
                  tRDDRSUJr.setKindForPrint(2);  // Название дороги
                  tRDJrList.add(tRDDRSUJr);
               }

               if (tRDet.getComment()!=null)
                   if (tRDet.getComment().trim().length()>0) {
                       RoadDetForJasperReportDTO tRDCommentJr=new RoadDetForJasperReportDTO();
                       tRDCommentJr.setName(getName(tRDet).trim());
                       tRDCommentJr.setComment(tRDet.getComment().trim());
                       tRDCommentJr.setRowNum(++rowNum);
                       tRDCommentJr.setKindForPrint(3);  // Комментарий
                       tRDJrList.add(tRDCommentJr);
                       continue;
                   }


               RoadDetForJasperReportDTO tRDetJr=new RoadDetForJasperReportDTO();
               tRDetJr.setName(getName(tRDet));
               tRDetJr.setLineno(++lineNo);
               tRDetJr.setRowNum(++rowNum);
               tRDetJr.setWsegodorog(tRDet.getWsegodorog());
               tRDetJr.setWsegodoroghardcover(tRDet.getWsegodoroghardcover());
               tRDetJr.setProcent(tRDet.getProcent());
               tRDetJr.setCementbeton(tRDet.getCementbeton());
               tRDetJr.setAsfaltbeton(tRDet.getAsfaltbeton());
               tRDetJr.setChernshosse(tRDet.getChernshosse());
               tRDetJr.setBeloeshosse(tRDet.getBeloeshosse());
               tRDetJr.setDegtegrunt(tRDet.getDegtegrunt());
               tRDetJr.setGruntovye(tRDet.getGruntovye());
               tRDetJr.setPokrkat1(tRDet.getPokrkat1());
               tRDetJr.setPokrkat2(tRDet.getPokrkat2());
               tRDetJr.setPokrkat3(tRDet.getPokrkat3());
               tRDetJr.setPokrkat4(tRDet.getPokrkat4());
               tRDetJr.setPokrkat5(tRDet.getPokrkat5());
               tRDetJr.setMostsht(tRDet.getMostsht());
               tRDetJr.setMostpm(tRDet.getMostpm());
               tRDetJr.setMostshtder(tRDet.getMostshtder());
               tRDetJr.setMostpmder(tRDet.getMostpmder());
               tRDetJr.setTrubysht(tRDet.getTrubysht());
               tRDetJr.setTrubypm(tRDet.getTrubypm());
               tRDetJr.setKindForPrint(0);
               tRDJrList.add(tRDetJr);
            //       logger.debug("name="+tR1P5DTJr.getName()+" amnt="+tR1P5DTJr.getAmnt());
           }
        }

        RoadDetForJasperReportDTO tRDSumJr=new RoadDetForJasperReportDTO();
        RoadDetEntity rde=getSumDetForWidRoad(shifridwr);
        tRDSumJr.setName("Итого");
        tRDSumJr.setRowNum(++rowNum);
        tRDSumJr.setWsegodorog(rde.getWsegodorog());
        tRDSumJr.setWsegodoroghardcover(rde.getWsegodoroghardcover());
        tRDSumJr.setProcent(rde.getProcent());
        tRDSumJr.setCementbeton(rde.getCementbeton());
        tRDSumJr.setAsfaltbeton(rde.getAsfaltbeton());
        tRDSumJr.setChernshosse(rde.getChernshosse());
        tRDSumJr.setBeloeshosse(rde.getBeloeshosse());
        tRDSumJr.setDegtegrunt(rde.getDegtegrunt());
        tRDSumJr.setGruntovye(rde.getGruntovye());
        tRDSumJr.setPokrkat1(rde.getPokrkat1());
        tRDSumJr.setPokrkat2(rde.getPokrkat2());
        tRDSumJr.setPokrkat3(rde.getPokrkat3());
        tRDSumJr.setPokrkat4(rde.getPokrkat4());
        tRDSumJr.setPokrkat5(rde.getPokrkat5());
        tRDSumJr.setMostsht(rde.getMostsht());
        tRDSumJr.setMostpm(rde.getMostpm());
        tRDSumJr.setMostshtder(rde.getMostshtder());
        tRDSumJr.setMostpmder(rde.getMostpmder());
        tRDSumJr.setTrubysht(rde.getTrubysht());
        tRDSumJr.setTrubypm(rde.getTrubypm());
        tRDSumJr.setKindForPrint(4);
        tRDJrList.add(tRDSumJr);


        Collections.sort(tRDJrList, new Comparator<RoadDetForJasperReportDTO>() {
            @Override
            public int compare(RoadDetForJasperReportDTO o1, RoadDetForJasperReportDTO o2) {
                return o1.compareTo(o2);
            }
        });
        logger.debug("Amnt of rec in list= " + tRDJrList.size());
        
        //    logger.debug("amount of groups="+lg.size());
        JRDataSource ds = new JRBeanCollectionDataSource(tRDJrList);
        return ds;
    }

    
    public List<ItemRDColsDTO> getColsList() {
        List<ItemRDColsDTO> listRDCols=new ArrayList<ItemRDColsDTO>();
        listRDCols.add(new ItemRDColsDTO(3,"Всего дорог",true));
        listRDCols.add(new ItemRDColsDTO(4,"В т.ч. с тверд.поркыт.",true));
        listRDCols.add(new ItemRDColsDTO(5,"процент дорог с твердым покрытием",true));
        listRDCols.add(new ItemRDColsDTO(6,"цементо-бетонные покрытия",true));
        listRDCols.add(new ItemRDColsDTO(7,"асфальто-бетонные покрытия",true));
        listRDCols.add(new ItemRDColsDTO(8,"черное шоссе",true));
        listRDCols.add(new ItemRDColsDTO(9,"белое шоссе",true));
        listRDCols.add(new ItemRDColsDTO(10,"брусчатка",true));
        listRDCols.add(new ItemRDColsDTO(11,"дегте-грунтовые",true));
        listRDCols.add(new ItemRDColsDTO(12,"грунтовые",true));    // Последний true
        listRDCols.add(new ItemRDColsDTO(13,"1-я категория покрытия",true));
        listRDCols.add(new ItemRDColsDTO(14,"2-я категория покрытия",true));
        listRDCols.add(new ItemRDColsDTO(15,"3-я категория покрытия",true));
        listRDCols.add(new ItemRDColsDTO(16,"4-я категория покрытия",true));
        listRDCols.add(new ItemRDColsDTO(17,"5-я категория покрытия",true));
        listRDCols.add(new ItemRDColsDTO(18,"Мосты всего штук",true));
        listRDCols.add(new ItemRDColsDTO(19,"Мосты всего пог.метров",true));
        listRDCols.add(new ItemRDColsDTO(20,"Деревянные мосты штук",true));
        listRDCols.add(new ItemRDColsDTO(21,"Деревянные мосты пог.метров",true));
        listRDCols.add(new ItemRDColsDTO(22,"Трубы штук",true));
        listRDCols.add(new ItemRDColsDTO(23,"Трубы пог.метров",true));
        return listRDCols;
    }
    public Map<String,String> getColsMap(List<ItemRDColsDTO> l) {
        Map<String,String> mapRDCols=new HashMap<String,String>();

        for (ItemRDColsDTO i:l) {
            if (i.isSelected()) {
                mapRDCols.put(""+i.getId(),"1");
            } else {
                mapRDCols.put(""+i.getId(),"0");
            }
        }
        return mapRDCols;
    }
    public int getColSpan(Map<String,String> m) {
        int retVal=0;
        for(String i:m.keySet()) {
            if (m.get(i)=="1") {
                retVal++;
            }
        }
        return retVal;
    }
    public ColsDTO getColsDTO(Map<String,String> m) {
        ColsDTO colsDTO=new ColsDTO();
        int j=0;
        int cols[]=new int[getColSpan(m)];
        for(String i:m.keySet()) {
            if (m.get(i)=="1") {
                cols[j++]=Integer.parseInt(i);
            }
        }
        colsDTO.setCols(cols);
        return colsDTO;
    }
    
    public List<ItemRDColsDTO> setCols(ColsDTO colsDTO,List<ItemRDColsDTO> listRDCols) {
        if (colsDTO.getCols().length>0) {
            for (ItemRDColsDTO col:listRDCols) {
                col.setSelected(false);
            }
            for (int j:colsDTO.getCols()) {
                for (ItemRDColsDTO col:listRDCols) {
                    if (col.getId()==j) {
                        col.setSelected(true);
                        break;
                    }
                }
                
            }
        }
        return listRDCols;
    }

}

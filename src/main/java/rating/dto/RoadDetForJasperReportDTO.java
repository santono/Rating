package rating.dto;


import java.io.Serializable;

public class RoadDetForJasperReportDTO   implements Serializable, Comparable<RoadDetForJasperReportDTO>{
    private int lineno;
    private String name;
    private double wsegodorog;
    private double wsegodoroghardcover;
    private double procent;
    private double cementbeton;
    private double asfaltbeton;
    private double chernshosse;
    private double beloeshosse;
    private double bruschatka;
    private double degtegrunt;
    private double gruntovye;
    private double pokrkat1;
    private double pokrkat2;
    private double pokrkat3;
    private double pokrkat4;
    private double pokrkat5;
    private double mostsht;
    private double mostpm;
    private double mostshtder;
    private double mostpmder;
    private double trubysht;
    private double trubypm;
    private String comment;
    private int kindForPrint; //0 - обычная строка 1-название лороги - 2 название ДРСУ 3 - комментарий
    private int rowNum;
    public double getWsegodorog() {
        return wsegodorog;
    }

    public void setWsegodorog(double wsegodorog) {
        this.wsegodorog = wsegodorog;
    }

    public double getWsegodoroghardcover() {
        return wsegodoroghardcover;
    }

    public void setWsegodoroghardcover(double wsegodoroghardcover) {
        this.wsegodoroghardcover = wsegodoroghardcover;
    }

    public double getProcent() {
        return procent;
    }

    public void setProcent(double procent) {
        this.procent = procent;
    }

    public double getCementbeton() {
        return cementbeton;
    }

    public void setCementbeton(double cementbeton) {
        this.cementbeton = cementbeton;
    }

    public double getAsfaltbeton() {
        return asfaltbeton;
    }

    public void setAsfaltbeton(double asfaltbeton) {
        this.asfaltbeton = asfaltbeton;
    }

    public double getChernshosse() {
        return chernshosse;
    }

    public void setChernshosse(double chernshosse) {
        this.chernshosse = chernshosse;
    }

    public double getBeloeshosse() {
        return beloeshosse;
    }

    public void setBeloeshosse(double beloeshosse) {
        this.beloeshosse = beloeshosse;
    }

    public double getBruschatka() {
        return bruschatka;
    }

    public void setBruschatka(double bruschatka) {
        this.bruschatka = bruschatka;
    }

    public double getDegtegrunt() {
        return degtegrunt;
    }

    public void setDegtegrunt(double degtegrunt) {
        this.degtegrunt = degtegrunt;
    }

    public double getGruntovye() {
        return gruntovye;
    }

    public void setGruntovye(double gruntovye) {
        this.gruntovye = gruntovye;
    }

    public double getPokrkat1() {
        return pokrkat1;
    }

    public void setPokrkat1(double pokrkat1) {
        this.pokrkat1 = pokrkat1;
    }

    public double getPokrkat2() {
        return pokrkat2;
    }

    public void setPokrkat2(double pokrkat2) {
        this.pokrkat2 = pokrkat2;
    }

    public double getPokrkat3() {
        return pokrkat3;
    }

    public void setPokrkat3(double pokrkat3) {
        this.pokrkat3 = pokrkat3;
    }

    public double getPokrkat4() {
        return pokrkat4;
    }

    public void setPokrkat4(double pokrkat4) {
        this.pokrkat4 = pokrkat4;
    }

    public double getPokrkat5() {
        return pokrkat5;
    }

    public void setPokrkat5(double pokrkat5) {
        this.pokrkat5 = pokrkat5;
    }

    public double getMostsht() {
        return mostsht;
    }

    public void setMostsht(double mostsht) {
        this.mostsht = mostsht;
    }

    public double getMostpm() {
        return mostpm;
    }

    public void setMostpm(double mostpm) {
        this.mostpm = mostpm;
    }

    public double getMostshtder() {
        return mostshtder;
    }

    public void setMostshtder(double mostshtder) {
        this.mostshtder = mostshtder;
    }

    public double getMostpmder() {
        return mostpmder;
    }

    public void setMostpmder(double mostpmder) {
        this.mostpmder = mostpmder;
    }

    public double getTrubysht() {
        return trubysht;
    }

    public void setTrubysht(double trubysht) {
        this.trubysht = trubysht;
    }

    public double getTrubypm() {
        return trubypm;
    }

    public void setTrubypm(double trubypm) {
        this.trubypm = trubypm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLineno() {
        return lineno;
    }

    public void setLineno(int lineno) {
        this.lineno = lineno;
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getKindForPrint() {
        return kindForPrint;
    }

    public void setKindForPrint(int kindForPrint) {
        this.kindForPrint = kindForPrint;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    @Override
    public int compareTo(RoadDetForJasperReportDTO o) {
        int retVal=0;
        if (getRowNum()>o.getRowNum()) {
            retVal=1;
        } else if (getRowNum()<o.getRowNum()) {
            retVal=-1;
        } else {
            retVal=0;
        }
        return retVal;
    }
}

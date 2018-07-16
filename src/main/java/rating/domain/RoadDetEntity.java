package rating.domain;

import java.io.Serializable;

/**
 * Таблица подразделений.
 */
public class RoadDetEntity implements Serializable {
    private static final long serialVersionUID = -5527566246002296042L;

    private long id;
    private long idroad;
    private long idpodr;
    private String comment;
    private double latitudefr;
    private double longitudefr;
    private double latitudeto;
    private double longitudeto;
    private double posstart;
    private double posend;
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
    private int shifrwrk;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdroad() {
        return idroad;
    }

    public void setIdroad(long idroad) {
        this.idroad = idroad;
    }

    public long getIdpodr() {
        return idpodr;
    }

    public void setIdpodr(long idpodr) {
        this.idpodr = idpodr;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getLatitudefr() {
        return latitudefr;
    }

    public void setLatitudefr(double latitudefr) {
        this.latitudefr = latitudefr;
    }

    public double getLongitudefr() {
        return longitudefr;
    }

    public void setLongitudefr(double longitudefr) {
        this.longitudefr = longitudefr;
    }

    public double getLatitudeto() {
        return latitudeto;
    }

    public void setLatitudeto(double latitudeto) {
        this.latitudeto = latitudeto;
    }

    public double getLongitudeto() {
        return longitudeto;
    }

    public void setLongitudeto(double longitudeto) {
        this.longitudeto = longitudeto;
    }

    public double getPosstart() {
        return posstart;
    }

    public void setPosstart(double posstart) {
        this.posstart = posstart;
    }

    public double getPosend() {
        return posend;
    }

    public void setPosend(double posend) {
        this.posend = posend;
    }

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

    public int getShifrwrk() {
        return shifrwrk;
    }

    public void setShifrwrk(int shifrwrk) {
        this.shifrwrk = shifrwrk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoadDetEntity that = (RoadDetEntity) o;

        if (id != that.id) return false;
        if (idroad != that.idroad) return false;
        if (idpodr != that.idpodr) return false;
        if (idpodr != that.idpodr) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) id;
        result = 31 * result + (int) idroad;
        result = 31 * result + (int) idpodr;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (int) Math.round(posstart);
        result = 31 * result + (int) Math.round(posend);
        return result;
    }

    @Override
    public String toString() {
        String retVal;
        StringBuilder sb = new StringBuilder();
        sb.append("RoadDetEntity id=" + id);
        sb.append(", comment=" + (comment != null ? comment : ""));
        sb.append(", idroad=" + idroad);
        sb.append(", idpodr=" + idpodr);
        retVal = sb.toString();
        return retVal;
    }

}


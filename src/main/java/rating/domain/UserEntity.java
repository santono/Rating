package rating.domain;

import java.io.Serializable;
import java.sql.Date;


public class UserEntity implements Serializable {
    private static final long serialVersionUID = -5527566248002296042L;

    private long id;
    private String name;
    private Date dataCreate;
    private Date dataDelete;
    private boolean active;
    private String login;
    private String password;
    private int tabno;
    private int shifrPodr;
    private String email;
    private String fam;
    private String nam;
    private String otc;
    private int uzwan;
    private int ustep;
    private String dopInf;
    private String engFio;
    private String authorIdEliblaryRu;
    private String hrefElibraryRu;
    private String orcIdScopusCom;
    private String hrefScopusCom;
    private String reseacherIdWebOfKnoledgeCom;
    private String hrefWebOfKnoledgeCom;
    private String dopInfForSearch;
    private int shifrKat;
    private int shifrDol;
    private int shifrUni;
    private int shifrFac;
    private int shifrKaf;
    private int shifrWr;
    private String dolgOsnMr;
    private String dopSlInfo;
    private long userCode;
    private int statusCode;
    private Date dataVerification;
    private long shifrIdSup;
    private String verifiedSupFIO;
    private StatisticEntity sEntity;

    public UserEntity() {
    }

    public int getTabno() {
        return tabno;
    }

    public void setTabno(int tabno) {
        this.tabno = tabno;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Date getDataCreate() {
        return dataCreate;
    }

    public void setDataCreate(Date dataCreate) {
        this.dataCreate = dataCreate;
    }

    public Date getDataDelete() {
        return dataDelete;
    }

    public void setDataDelete(Date dateDelete) {
        this.dataDelete = dateDelete;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public int getShifrPodr() {
        return shifrPodr;
    }

    public void setShifrPodr(int shifrPodr) {
        this.shifrPodr = shifrPodr;
    }

    public StatisticEntity getsEntity() {
        return sEntity;
    }

    public void setsEntity(StatisticEntity sEntity) {
        this.sEntity = sEntity;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFam() {
        return fam;
    }

    public void setFam(String fam) {
        this.fam = fam;
    }

    public String getNam() {
        return nam;
    }

    public void setNam(String nam) {
        this.nam = nam;
    }

    public String getOtc() {
        return otc;
    }

    public void setOtc(String otc) {
        this.otc = otc;
    }

    public int getUzwan() {
        return uzwan;
    }

    public void setUzwan(int uzwan) {
        this.uzwan = uzwan;
    }

    public int getUstep() {
        return ustep;
    }

    public void setUstep(int ustep) {
        this.ustep = ustep;
    }

    public String getDopInf() {
        return dopInf;
    }

    public void setDopInf(String dopInf) {
        this.dopInf = dopInf;
    }

    public String getEngFio() {
        return engFio;
    }

    public void setEngFio(String engFio) {
        this.engFio = engFio;
    }

    public String getAuthorIdEliblaryRu() {
        return authorIdEliblaryRu;
    }

    public void setAuthorIdEliblaryRu(String authorIdEliblaryRu) {
        this.authorIdEliblaryRu = authorIdEliblaryRu;
    }

    public String getHrefElibraryRu() {
        return hrefElibraryRu;
    }

    public void setHrefElibraryRu(String hrefElibraryRu) {
        this.hrefElibraryRu = hrefElibraryRu;
    }

    public String getOrcIdScopusCom() {
        return orcIdScopusCom;
    }

    public void setOrcIdScopusCom(String orcIdScopusCom) {
        this.orcIdScopusCom = orcIdScopusCom;
    }

    public String getHrefScopusCom() {
        return hrefScopusCom;
    }

    public void setHrefScopusCom(String hrefScopusCom) {
        this.hrefScopusCom = hrefScopusCom;
    }

    public String getReseacherIdWebOfKnoledgeCom() {
        return reseacherIdWebOfKnoledgeCom;
    }

    public void setReseacherIdWebOfKnoledgeCom(String reseacherIdWebOfKnoledgeCom) {
        this.reseacherIdWebOfKnoledgeCom = reseacherIdWebOfKnoledgeCom;
    }

    public String getHrefWebOfKnoledgeCom() {
        return hrefWebOfKnoledgeCom;
    }

    public void setHrefWebOfKnoledgeCom(String hrefWebOfKnoledgeCom) {
        this.hrefWebOfKnoledgeCom = hrefWebOfKnoledgeCom;
    }

    public String getDopInfForSearch() {
        return dopInfForSearch;
    }

    public void setDopInfForSearch(String dopInfForSearch) {
        this.dopInfForSearch = dopInfForSearch;
    }

    public int getShifrKat() {
        return shifrKat;
    }

    public void setShifrKat(int shifrKat) {
        this.shifrKat = shifrKat;
    }

    public int getShifrDol() {
        return shifrDol;
    }

    public void setShifrDol(int shifrDol) {
        this.shifrDol = shifrDol;
    }

    public int getShifrUni() {
        return shifrUni;
    }

    public void setShifrUni(int shifrUni) {
        this.shifrUni = shifrUni;
    }

    public int getShifrFac() {
        return shifrFac;
    }

    public void setShifrFac(int shifrFac) {
        this.shifrFac = shifrFac;
    }

    public int getShifrKaf() {
        return shifrKaf;
    }

    public void setShifrKaf(int shifrKaf) {
        this.shifrKaf = shifrKaf;
    }

    public int getShifrWr() {
        return shifrWr;
    }

    public void setShifrWr(int shifrWr) {
        this.shifrWr = shifrWr;
    }

    public String getDolgOsnMr() {
        return dolgOsnMr;
    }

    public void setDolgOsnMr(String dolgOsnMr) {
        this.dolgOsnMr = dolgOsnMr;
    }

    public long getUserCode() {
        return userCode;
    }

    public void setUserCode(long userCode) {
        this.userCode = userCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Date getDataVerification() {
        return dataVerification;
    }

    public void setDataVerification(Date dataVerification) {
        this.dataVerification = dataVerification;
    }

    public long getShifrIdSup() {
        return shifrIdSup;
    }

    public void setShifrIdSup(long shifrIdSup) {
        this.shifrIdSup = shifrIdSup;
    }

    public String getDopSlInfo() {
        return dopSlInfo;
    }

    public void setDopSlInfo(String dopSlInfo) {
        this.dopSlInfo = dopSlInfo;
    }

    public String getVerifiedSupFIO() {
        return verifiedSupFIO;
    }

    public void setVerifiedSupFIO(String verifiedSupFIO) {
        this.verifiedSupFIO = verifiedSupFIO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (id != that.id) return false;
        if (userCode != that.userCode) return false;
        return true;
    }



    @Override
    public int hashCode() {
        long result = (long) id;
        result = 31 * result + userCode;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return (int )result;
    }

    @Override
    public String toString() {
        String retVal;
        StringBuilder sb = new StringBuilder();
        sb.append("UserEntity id=" + id);
        sb.append(", name=" + (name != null ? name : ""));
        sb.append(", fam=" + (fam != null ? fam : ""));
        sb.append(", nam=" + (nam != null ? nam : ""));
        sb.append(", otc=" + (otc != null ? otc : ""));
        sb.append(", userCode=" + userCode);
        retVal = sb.toString();
        return retVal;
    }

}

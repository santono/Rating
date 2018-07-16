package rating.dto;

import java.io.Serializable;


public class PokazListForJasperReportDTO implements Serializable, Comparable<PokazListForJasperReportDTO> {
        private String name;
        private int lineno;
        private String amnt;
        private String amntn;

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

        public String getAmnt() {
            return amnt;
        }

        public void setAmnt(String amnt) {
            this.amnt = amnt;
        }

        public String getAmntn() {
            return amntn;
        }

        public void setAmntn(String amntn) {
            this.amntn = amntn;
        }

       @Override
        public int compareTo(PokazListForJasperReportDTO o) {
            int retVal=0;
            if (getLineno()>o.getLineno()) {
                retVal=1;
            } else if (getLineno()<o.getLineno()) {
                retVal=-1;
            } else {
                retVal=0;
            }
            return retVal;
        }


}

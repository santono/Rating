package rating.dto;


public enum RolesEnum {
        NPR   (1),  //Научно-педагогические работникиcalls constructor with value 3
        DOD   (2),  //Уполномоченные за занесение данных (ДОД)calls constructor with value 2
        DNID  (3),  //Уполномоченные за занесение данных (ДНИД)
        SUP   (4),   //Руководители подразделений НПРcalls constructor with value 1
        DEP   (5),   //Экспертные группы СП(Ф)calls constructor with value 1
        COMISSION(6),//Комиссия по верификации КФУcalls constructor with value 1
        APDATA(7),   //Администраторы данных (ДУКИПР)calls constructor with value 1
        ADMIN (8)    //Администраторы системыcalls constructor with value 1
        ; // semicolon needed when fields / methods follow


        private final int roleCode;

        RolesEnum(int roleCode) {
            this.roleCode = roleCode;
        }

        public int getRoleCode() {
            return this.roleCode;
        }

}

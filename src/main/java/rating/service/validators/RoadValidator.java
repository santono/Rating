package rating.service.validators;


import rating.domain.RoadEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RoadValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return RoadEntity.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RoadEntity row = (RoadEntity) target;
        int maxKod = 1000;
        double maxDlina = 400;

        int kod = row.getKod();
        if (!((kod >= 1) && (kod < maxKod))) {
            errors.rejectValue("kod", "kod.WrongValue", "Неверно указан код дороги. ( 1.." + maxKod + ")");
        }
        double dlina = row.getDlina();
        if (!((dlina >= 0.1) && (dlina < maxDlina))) {
            errors.rejectValue("dlina", "dlina.WrongValue", "Неверно указана длина дороги. (0,1 .." + maxDlina + ")");
        }
        int kodtype = row.getKodtype();
        if (!((kodtype == 1) || (kodtype == 2))) {
            errors.rejectValue("kodtype", "kodtype.WrongValue", "Неверно указан тип дороги. (региональная или межмуниципальная)");
        }

    }

}

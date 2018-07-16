package rating.service.validators;

import rating.domain.RoadDetEntity;
import rating.domain.RoadDocEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class RoadDocValidator implements Validator {


    @Override
    public boolean supports(Class<?> aClass) {
        return RoadDetEntity.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RoadDocEntity item = (RoadDocEntity) target;
        double minLimitXX = 30;
        double maxLimitXX = 40;
        double minLimitYY = 30;
        double maxLimitYY = 40;
        if (item.getComment()== null) {
            errors.rejectValue("comment", "comment.WrongValue1", "Не заполнено описание документа.");
        }

/*
        double xx = row.getLatitudefr();
        if (!((xx >= minLimitXX) && (xx < maxLimitXX))) {
            errors.rejectValue("xx", "xx.WrongLatitude", "Неверно указана широта точки. (" + minLimitXX + " ... " + maxLimitXX + ")");
        }
        double yy = row.getYy();
        if (!((yy >= minLimitYY) && (xx < maxLimitYY))) {
            errors.rejectValue("yy", "yy.WrongLongitude", "Неверно указана долгтоа точки. (" + minLimitYY + " ... " + maxLimitYY + ")");
        }
        int lineno = row.getLineno();
        if (!((lineno > 0) && (lineno < 100))) {
            errors.rejectValue("lineno", "lineno.WrongNumber", "Неверно указан номер точки. (0.1..100)");
        }
*/
    }

}

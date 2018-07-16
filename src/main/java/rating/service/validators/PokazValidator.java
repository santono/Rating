package rating.service.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import rating.domain.PokazEntity;


@Component
public class PokazValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return PokazEntity.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PokazEntity pokaz = (PokazEntity) target;
        try {
            if (pokaz==null)  {
                errors.reject("Пуста запись показателя");
            }

            if (pokaz.getName()== null) {
                errors.rejectValue("name", "name.WrongValue1", "Не заполнено название показателя.");
            }

            if ((pokaz.getName().length() < 1) || (pokaz.getName().length() > 256)) {
                errors.rejectValue("name", "name.WrongValue", "Неверно указано название показателя.");
            }
            if (pokaz.getShortname()== null) {
                errors.rejectValue("shortName", "shortName.WrongValue1", "Не заполнено краткое название показателя.");
            }

            if ((pokaz.getShortname().length() < 1) || (pokaz.getShortname().length() > 128)) {
                errors.rejectValue("shortName", "shortName.WrongValue", "Неверно указано краткое название показателя.");
            }
            if ((pokaz.getLineno()<1) || (pokaz.getLineno() > 999)) {
                errors.rejectValue("lineno", "lineno.WrongValue", "Неверно указан номер строки.");
            }
        } catch (Exception e){
            errors.reject("Ошибка в информации о показателе");

        }



    }
    
}

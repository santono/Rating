package rating.service.validators;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import rating.domain.DolgEntity;

@Component
public class DolgValidator  implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return DolgEntity.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        DolgEntity dolg = (DolgEntity) target;
        try {
            if (dolg==null)  {
                errors.reject("Пуста запись должности");
            }

            if (dolg.getName()== null) {
                errors.rejectValue("name", "name.WrongValue1", "Не заполнено название дожности.");
            }

            if ((dolg.getName().length() < 1) || (dolg.getName().length() > 256)) {
                errors.rejectValue("name", "name.WrongValue", "Неверно указано название должности.");
            }
            if (dolg.getShortname()== null) {
                errors.rejectValue("shortName", "shortName.WrongValue1", "Не заполнено краткое название должности.");
            }

            if ((dolg.getShortname().length() < 1) || (dolg.getShortname().length() > 128)) {
                errors.rejectValue("shortName", "shortName.WrongValue", "Неверно указано краткое название должности.");
            }
            if ((dolg.getKind()<0) || (dolg.getKind() > 999)) {
                errors.rejectValue("lineno", "lineno.WrongValue", "Неверно указан код категории должности.");
            }
        } catch (Exception e){
            errors.reject("Ошибка в информации о должности.");

        }
    }
}

package rating.service.validators;


import rating.domain.PodrEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PodrValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return PodrEntity.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PodrEntity podr = (PodrEntity) target;
        try {
           if (podr==null)  {
               errors.reject("Пуста запись подразделения");
           }

           if (podr.getName()== null) {
               errors.rejectValue("name", "name.WrongValue1", "Не заполнено название предприятия.");
           }

           if ((podr.getName().length() < 1) || (podr.getName().length() > 100)) {
               errors.rejectValue("name", "name.WrongValue", "Неверно указано название предприятия.");
           }
           if (podr.getShortName()== null) {
               errors.rejectValue("shortName", "shortName.WrongValue1", "Не заполнено краткое название предприятия.");
           }

           if ((podr.getShortName().length() < 1) || (podr.getShortName().length() > 100)) {
               errors.rejectValue("shortName", "shortName.WrongValue", "Неверно указано краткое название предприятия.");
           }
        } catch (Exception e){
            errors.reject("Ошибка в информации о подразделении");

        }



    }

}

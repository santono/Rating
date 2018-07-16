package rating.service.validators;

import rating.domain.WidBlobEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class WidBlobValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return WidBlobEntity.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        WidBlobEntity widBlob = (WidBlobEntity) target;

        if (widBlob.getName()== null) {
            errors.rejectValue("name", "name.WrongValue", "Не заполнено название типа документа.");
        }

        if ((widBlob.getName().length() < 1) || (widBlob.getName().length() > 100)) {
            errors.rejectValue("name", "name.WrongValue1", "Неверно указано название типа документа.");
        }

    }


}

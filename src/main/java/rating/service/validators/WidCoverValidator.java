package rating.service.validators;

import rating.domain.WidCoverEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class WidCoverValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return WidCoverEntity.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        WidCoverEntity widCover = (WidCoverEntity) target;

        if (widCover.getName()== null) {
            errors.rejectValue("name", "name.WrongValue", "Не заполнено название вида покрытия.");
        }

        if ((widCover.getName().length() < 1) || (widCover.getName().length() > 100)) {
            errors.rejectValue("name", "name.WrongValue1", "Неверно указано название вида покрытия.");
        }

    }

}

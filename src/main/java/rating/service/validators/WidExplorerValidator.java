package rating.service.validators;

import rating.domain.WidExplorerEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class WidExplorerValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return WidExplorerEntity.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        WidExplorerEntity widDamage = (WidExplorerEntity) target;

        if (widDamage.getName()== null) {
            errors.rejectValue("name", "name.WrongValue", "Не заполнено название вида повреждения.");
        }

        if ((widDamage.getName().length() < 1) || (widDamage.getName().length() > 100)) {
            errors.rejectValue("name", "name.WrongValue1", "Неверно указано название вида повреждения.");
        }

    }

}

package rating.service.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import rating.domain.UserEntity;

@Component
public class UserValidator  implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return UserEntity.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserEntity uEntity = (UserEntity) target;
//        System.out.println("1");
        try {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "", "Не заполнено поле логин.");
        } catch(Exception e) {
            errors.rejectValue("login25","login.wrongValue25", "Ошибка обработки поля ЛОГИН.");
        }
//        System.out.println("2");
//
//        if (uEntity.getLogin()== null) {
//            errors.rejectValue("login", "login.WrongValue", "Не заполнено поле логин.");
//        }
//        else
        if ((uEntity.getLogin().length() < 1) || (uEntity.getLogin().length() > 16)) {
            errors.rejectValue("login", "name.WrongValue1", "Длина поля логин не более 16 символов.");
        }
//        System.out.println("3");
        try {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "", "Не заполнено поле Email.");
        } catch (Exception e) {
            errors.rejectValue("email25", "email.WrongValue25", "Ошибка работы с полем email.");
        }
//        System.out.println("4");
        if (uEntity.getEmail()==null) {
            errors.rejectValue("email2","email.wrongValue2", "Незаполнено поле Email.");
        } else
        if (!uEntity.getEmail().contains("@")) {
            errors.rejectValue("email3","email.wrongValue3", "Некорректный Email.");
        }
//        System.out.println("5");

/*
        if (uEntity.getEmail()== null) {
            errors.rejectValue("email", "email", "Не заполнено поле email.");
        }
        else
        if ((uEntity.getEmail().length() < 1) || (uEntity.getEmail().length() > 32)) {
            errors.rejectValue("email", "shortName.WrongValue1", "Длина поля email не более 32 символов.");
        }
*/
    }

}

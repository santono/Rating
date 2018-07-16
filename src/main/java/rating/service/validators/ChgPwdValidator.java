package rating.service.validators;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import rating.dto.ChgPwdDTO;

@Component
public class ChgPwdValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return ChgPwdDTO.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ChgPwdDTO cpDTO = (ChgPwdDTO) target;
        try {
            if (cpDTO==null)  {
                errors.reject("Пуста запись смены пароля пользователя.");
            }

            if (cpDTO.getIduser()<1) {
                errors.rejectValue("iduser", "isuser.WrongValue", "Не указан код пользователя.");
            }

            if ((cpDTO.getPwd()==null) || (cpDTO.getPwd().length() > 16)|| (cpDTO.getPwd().length() <1)) {
                errors.rejectValue("pwd", "pwd.WrongValue", "Неверная длина пароля.");
            }
            if (!matchesPolicy(cpDTO.getPwd())) {
                errors.rejectValue("pwd", "pwd.WrongValue1", "Неверные символі в пароле.");
            }
        } catch (Exception e){
            errors.reject("Ошибка в информации о смене пароля");

        }
    }
    private boolean  matchesPolicy(String pwd) {
        if (pwd.trim().length() < 1) return false;
        if (pwd.trim().length() > 16) return false;
        if (pwd.trim().contains(" ")) return false;
        if (pwd.trim().contains("\t")) return false;
        if (pwd.trim().contains("\n")) return false;
        if (pwd.trim().contains("\r")) return false;
        if (pwd.trim().contains("\b")) return false;
        if (pwd.trim().contains("\f")) return false;
        if (pwd.trim().contains("\"")) return false;
        if (pwd.trim().contains("\'")) return false;
        if (pwd.trim().contains("\\")) return false;
        if (pwd.trim().contains("<")) return false;
        if (pwd.trim().contains(">")) return false;
        if (pwd.trim().toLowerCase().contains("true")) return false;
        return true;
    }    


}

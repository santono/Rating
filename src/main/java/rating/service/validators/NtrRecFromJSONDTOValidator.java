package rating.service.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import rating.dto.NtrRecFromJSONDTO;

import java.util.Calendar;


@Component
public class NtrRecFromJSONDTOValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return NtrRecFromJSONDTO.class.isAssignableFrom(aClass);
    }
    @Override
    public void validate(Object target, Errors errors) {
        NtrRecFromJSONDTO ntrRecFromJSONDTO = (NtrRecFromJSONDTO) target;
        try {
            if (ntrRecFromJSONDTO==null)  {
                errors.reject("Пуста запись научно-технической публикации");
            }

            if (ntrRecFromJSONDTO.getName()== null) {
                errors.rejectValue("name", "name.WrongValue", "Не заполнено название работы.");
            }

            if ((ntrRecFromJSONDTO.getName().length() < 1) || (ntrRecFromJSONDTO.getName().length() > 512)) {
                errors.rejectValue("name", "name.WrongValue1", "Неверно указано название работы.");
            }
            if (ntrRecFromJSONDTO.getParametry()== null) {
                errors.rejectValue("parametry", "parametry.WrongValue", "Не заполнены выходные данные работы.");
            }
            if ((ntrRecFromJSONDTO.getParametry().length() < 1) || (ntrRecFromJSONDTO.getParametry().length() > 512)) {
                errors.rejectValue("name", "parametry.WrongValue1", "Неверно указаны выходные данные работы.");
            }
            if (ntrRecFromJSONDTO.getDatePublJava()!=null)
                if ((ntrRecFromJSONDTO.getDatePublJava().get(Calendar.YEAR)<1960) ||
                        (ntrRecFromJSONDTO.getDatePublJava().get(Calendar.YEAR)>2020))
                    errors.rejectValue("datepubl", "datepubl.WrongValue", "Неверно указана дата публикации работы.");

            if (ntrRecFromJSONDTO.getShifrPre()<0)
                    errors.rejectValue("shifrPre", "shifrPre.WrongValue", "Неверно указан шифр предприятия.");

        } catch (Exception e){
            errors.reject("Ошибка в информации о подразделении");

        }



    }

}

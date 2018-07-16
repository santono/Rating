package rating.service.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import rating.dto.NtrAuthorsDTO;
import rating.dto.NtrAuthorsDetDTO;

@Component
public class NtrAuthorsDTOValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return NtrAuthorsDTO.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        NtrAuthorsDTO ntrAuthorsDTO = (NtrAuthorsDTO) target;
        try {
            if (ntrAuthorsDTO==null)  {
                errors.reject("Пуста запись списка авторов");
            }

            if (ntrAuthorsDTO.getId()<1) {
                errors.rejectValue("id", "id.WrongValue", "Неверный код научно-технической работы.");
            }
            if (ntrAuthorsDTO.getAuthors()==null) {
                errors.rejectValue("authors", "authors.WrongValue", "Не указан список авторов работы.");
            }
            if (ntrAuthorsDTO.getAuthors().length>0)
                for (NtrAuthorsDetDTO ntrAuthorsDetDTO : ntrAuthorsDTO.getAuthors()) {
                    if ((ntrAuthorsDetDTO.getAmode()<0) || (ntrAuthorsDetDTO.getAmode()>2)) {
                        errors.rejectValue("authorsRec", "authorsRec.WrongAModeValue", "Неверно указан тип автора.");

                    }
                }
        } catch (Exception e){
            errors.reject("Ошибка в информации о подразделении");

        }



    }

}

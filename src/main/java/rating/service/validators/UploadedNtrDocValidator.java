package rating.service.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Component
public class UploadedNtrDocValidator implements Validator {

    public boolean supports(Class<?> clazz) {
        return UploadedNtrDocValidator.class.isAssignableFrom(clazz);
    }

    public void validate(Object obj, Errors errors) {
        MultipartFile file = (MultipartFile) obj;

        if(file!=null){
            if (file.getSize() < 1) {
                errors.rejectValue("file", "Не указан файл для загрузки.");
            }
        }
    }
}

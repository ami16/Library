package edu.cursor.library.validator;

import edu.cursor.library.model.TblUser;
import edu.cursor.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return TblUser.class.equals(aClass);
    }
    //need realization
    @Override
    public void validate(Object o, Errors errors) {
        TblUser user = (TblUser) o;

//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userEmail", "Required");
//        if (user.getEmail().length() < 8 || user.getEMail().length() > 32) {
//            errors.rejectValue("userEmail", "Size.userForm.userEmail");
//        }
//
//        if (userService.findByEmail(user.getEMail()) != null) {
//            errors.rejectValue("userEmail", "Duplicate.userForm.userEmail");
//        }
//
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Required");
//        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
//            errors.rejectValue("password", "Size.userForm.password");
//        }
//
//        if (!user.getConfirmPassword().equals(user.getPassword())) {
//            errors.rejectValue("confirmPassword", "Different.userForm.password");
//        }
    }
}

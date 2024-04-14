package shop.goodspia.goods.common.validator;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

@Slf4j
public class EnumValidator implements ConstraintValidator<ValidEnum, Enum> {

    private Object[] enumValues;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        this.enumValues = constraintAnnotation.enumClass().getEnumConstants();
    }

    @Override
    public boolean isValid(Enum value, ConstraintValidatorContext context) {
        boolean result = false;
        log.info(Arrays.toString(enumValues));
        if (enumValues != null) {
            for (Object enumValue : enumValues) {
                if (value == enumValue) {
                    log.info("enum value: {}", enumValue);
                    result = true;
                    break;
                }
            }
        }

        return result;
    }
}

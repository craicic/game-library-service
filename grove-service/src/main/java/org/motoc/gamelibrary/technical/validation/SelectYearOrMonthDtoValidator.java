package org.motoc.gamelibrary.technical.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.motoc.gamelibrary.domain.dto.GameDto;
import org.motoc.gamelibrary.technical.validation.annotation.SelectYearOrMonth;

public class SelectYearOrMonthDtoValidator implements ConstraintValidator<SelectYearOrMonth, GameDto> {

    @Override
    public void initialize(SelectYearOrMonth constraintAnnotation) {

    }

    @Override
    public boolean isValid(GameDto game, ConstraintValidatorContext constraintValidatorContext) {
        return game.getMinAge() == 0 || game.getMinMonth() == 0;
    }
}

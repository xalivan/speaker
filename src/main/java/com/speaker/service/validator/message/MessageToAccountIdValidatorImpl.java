package com.speaker.service.validator.message;

import com.speaker.dto.MessageDTO;
import com.speaker.dto.ValidatorError;
import com.speaker.entities.EntityField;
import com.speaker.service.validator.AbstractValidator;
import com.speaker.service.validator.Validator;
import com.speaker.service.validator.type.ErrorType;
import com.speaker.service.validator.type.FieldType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
public class MessageToAccountIdValidatorImpl extends AbstractValidator implements Validator<MessageDTO> {

    @Override
    public ValidatorError validate(EntityField entityField) {
        if ((int) entityField.getField() <= 0) {
            return createValidatorError(ErrorType.NOT_VALID);
        }
        return null;
    }

    @Override
    public List<ValidatorError> validateList(List<EntityField> entityFields) {
        return entityFields.stream()
                .map(this::validate)
                .collect(Collectors.toList());
    }

    @Override
    public EntityField getField(MessageDTO messageDTO) {
        return new EntityField(messageDTO.getToAccountId());
    }

    @Override
    public List<EntityField> getFields(List<MessageDTO> messageDTOs) {
        return messageDTOs.stream()
                .map(this::getField)
                .collect(Collectors.toList());
    }

    @Override
    protected FieldType getType() {
        return FieldType.TO_ACCOUNT_ID;
    }
}

package com.speaker.service.validator.message;

import com.speaker.dto.MessageDTO;
import com.speaker.dto.ValidatorError;
import com.speaker.entities.EntityField;
import com.speaker.service.validator.AbstractValidator;
import com.speaker.service.validator.Validator;
import com.speaker.service.validator.type.ErrorType;
import com.speaker.service.validator.type.FieldType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
public class MessageDataValidatorImpl extends AbstractValidator implements Validator<MessageDTO> {
    private final long CURRENT_TIME = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

    @Override
    public ValidatorError validate(EntityField entityField) {
        LocalDateTime localDateTime = (LocalDateTime) entityField.getField();
        if (isNull(localDateTime)) {
            return createValidatorError(ErrorType.EMPTY);
        }
        if (checkIsFutureTime(localDateTime)) {
            return createValidatorError(ErrorType.NOT_VALID);
        }
        return null;
    }

    @Override
    public EntityField getField(MessageDTO messageDTO) {
        return new EntityField(messageDTO.getDate());
    }

    @Override
    public List<ValidatorError> validateList(List<EntityField> entityFields) {
        return entityFields.stream()
                .map(this::validate)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }


    @Override
    public List<EntityField> getFields(List<MessageDTO> messageDTOs) {
        return messageDTOs.stream()
                .map(this::getField)
                .collect(Collectors.toList());
    }

    @Override
    protected FieldType getType() {
        return FieldType.DATE;
    }

    public boolean checkIsFutureTime(LocalDateTime localDateTime) {
        return CURRENT_TIME < localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}

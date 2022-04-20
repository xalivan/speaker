package com.speaker.service.validator.message;

import com.speaker.dto.MessageDTO;
import com.speaker.dto.ValidatorError;
import com.speaker.entities.Status;
import com.speaker.service.validator.type.ErrorType;
import com.speaker.service.validator.type.FieldType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Supplier;

@Component
@Qualifier("messageStatusValidatorImpl")
public class MessageStatusValidatorImpl extends AbstractMessageCheckValidator {

    @Override
    public ValidatorError validate(String status) {
        if (!Objects.equals(status, Status.NEW.name())) {
            return ValidatorError.builder()
                    .message(FieldType.STATUS.getField() + NEW + ErrorType.NOT_FOUND.getError())
                    .field(FieldType.STATUS.getField())
                    .build();
        }
        return null;
    }

    @Override
    public Supplier<String> getField(MessageDTO messageDTO) {
        return () -> convertToString(messageDTO.getStatus());
    }

    private String convertToString(Status status) {
        return status.name();
    }
}

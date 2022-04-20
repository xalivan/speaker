package com.speaker.service.validator.message;

import com.speaker.dto.MessageDTO;
import com.speaker.dto.ValidatorError;
import com.speaker.service.validator.type.ErrorType;
import com.speaker.service.validator.type.FieldType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
@Qualifier("messageTextValidatorImpl")
public class MessageTextValidatorImpl extends AbstractMessageCheckValidator {

    @Override
    public ValidatorError validate(String message) {
        if (checkOrNull(message)) {
            return ValidatorError.builder()
                    .message(FieldType.TEXT.getField()+ ErrorType.EMPTY.getError())
                    .field(FieldType.TEXT.getField())
                    .build();
        }
        return null;
    }

    @Override
    public Supplier<String> getField(MessageDTO messageDTO) {
        return messageDTO::getText;
    }

}

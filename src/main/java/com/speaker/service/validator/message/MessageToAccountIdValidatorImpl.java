package com.speaker.service.validator.message;

import com.speaker.dto.MessageDTO;
import com.speaker.dto.ValidatorError;
import com.speaker.service.validator.type.ErrorType;
import com.speaker.service.validator.type.FieldType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
@Qualifier("messageToAccountIdValidatorImpl")
public class MessageToAccountIdValidatorImpl extends AbstractMessageCheckValidator {

    @Override
    public ValidatorError validate(String data) {
        if (checkOrNull(data)) {
            return ValidatorError.builder()
                    .message(FieldType.TO_ACCOUNT_ID.getField() + ErrorType.EMPTY.getError())
                    .field(FieldType.TO_ACCOUNT_ID.getField())
                    .build();
        }
        if (parsToInt(data) >= 0) {
            return ValidatorError.builder()
                    .message(FieldType.TO_ACCOUNT_ID.getField() + ErrorType.NOT_VALID.getError())
                    .field(FieldType.TO_ACCOUNT_ID.getField())
                    .build();
        }
        return null;
    }

    @Override
    public Supplier<String> getField(MessageDTO messageDTO) {
        return () -> convertToString(messageDTO.getFromAccountId());
    }

    private String convertToString(int fromAccountId) {
        return String.valueOf(fromAccountId);
    }

    private int parsToInt(String id) {
        return Integer.parseInt(id);
    }

}

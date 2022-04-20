package com.speaker.service.validator.message;

import com.speaker.dto.MessageDTO;
import com.speaker.dto.ValidatorError;
import com.speaker.service.validator.type.ErrorType;
import com.speaker.service.validator.type.FieldType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.regex.Pattern;

@Component
@Primary
@Qualifier("messageDataValidatorImpl")
public class MessageDataValidatorImpl extends AbstractMessageCheckValidator {

    @Override
    public ValidatorError validate(String data) {
        if (checkOrNull(data)) {
            return ValidatorError.builder()
                    .message(FieldType.DATE.getField() + ErrorType.EMPTY.getError())
                    .field(FieldType.DATE.getField())
                    .build();
        }
        if (splitByT(data).isEmpty()) {
            return ValidatorError.builder()
                    .message(FieldType.DATE.getField() + ErrorType.NOT_VALID.getError())
                    .field(FieldType.DATE.getField())
                    .build();
        }
        Optional<String[]> strings = splitByT(data);
        if (!checkFormat(strings.get()[0])) {
            return ValidatorError.builder()
                    .message(FieldType.DATE.getField() + ErrorType.NOT_VALID.getError())
                    .field(FieldType.DATE.getField())
                    .build();
        }

        return null;
    }

    @Override
    public Supplier<String> getField(MessageDTO messageDTO) {
        return () -> convertToString(messageDTO.getDate());
    }

    private String convertToString(LocalDateTime localDateTime) {
        return String.valueOf(localDateTime);
    }

    public Optional<String[]> splitByT(String names) {
        String[] splitNames = names.trim().split("T");
        if (splitNames.length == 2) {
            return Optional.of(splitNames);
        }
        return Optional.empty();
    }

    public boolean checkFormat(String date) {
        Pattern DATE_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
        return DATE_PATTERN.matcher(date).matches();
    }
}

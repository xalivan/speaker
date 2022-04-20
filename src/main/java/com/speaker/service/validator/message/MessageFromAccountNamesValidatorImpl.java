package com.speaker.service.validator.message;

import com.speaker.dto.MessageDTO;
import com.speaker.dto.ValidatorError;
import com.speaker.repository.AccountRepository;
import com.speaker.service.validator.type.ErrorType;
import com.speaker.service.validator.type.FieldType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

import static com.speaker.service.util.StringParser.splitBySpace;

@Component
@RequiredArgsConstructor
@Qualifier("messageFromAccountNamesValidatorImpl")
public class MessageFromAccountNamesValidatorImpl extends AbstractMessageCheckValidator {
    private final AccountRepository accountRepository;

    @Override
    public ValidatorError validate(String names) {
        if (checkOrNull(names)) {
            return ValidatorError.builder()
                    .message(FieldType.FROM_ACCOUNT_NAMES.getField() + ErrorType.EMPTY.getError())
                    .field(FieldType.FROM_ACCOUNT_NAMES.getField())
                    .build();
        }
        if (splitBySpace(names).isEmpty()) {
            return ValidatorError.builder()
                    .message(FieldType.FROM_ACCOUNT_NAMES.getField() + ErrorType.NOT_VALID.getError())
                    .field(FieldType.FROM_ACCOUNT_NAMES.getField())
                    .build();
        }
        if (findAccountByNames(names)) {
            return ValidatorError.builder()
                    .message(FieldType.FROM_ACCOUNT_NAMES.getField() + ErrorType.NOT_FOUND.getError())
                    .field(FieldType.FROM_ACCOUNT_NAMES.getField())
                    .build();
        }
        return null;
    }

    @Override
    public Supplier<String> getField(MessageDTO messageDTO) {
        return messageDTO::getFromAccountNames;
    }

    private boolean findAccountByNames(String names) {
        return splitBySpace(names)
                .flatMap(name ->
                        accountRepository.findAccountIdByNameAndLastName(name.getFirst(), name.getSecond())).isEmpty();
    }
}

package com.speaker.service.validator.message;

import com.speaker.dto.MessageDTO;
import com.speaker.dto.ValidatorError;
import com.speaker.entities.EntityField;
import com.speaker.repository.AccountRepository;
import com.speaker.service.validator.AbstractValidator;
import com.speaker.service.validator.Validator;
import com.speaker.service.validator.type.ErrorType;
import com.speaker.service.validator.type.FieldType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.speaker.service.util.StringParser.splitBySpace;
import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class MessageToAccountNamesValidatorImpl extends AbstractValidator implements Validator<MessageDTO> {
    private final AccountRepository accountRepository;

    @Override
    public ValidatorError validate(EntityField entityField) {
        Object fieldName = entityField.getField();
        if (isNull(fieldName)) {
            return createValidatorError(ErrorType.EMPTY, entityField);
        }
        if (splitBySpace(fieldName.toString()).isEmpty()) {
            return createValidatorError(ErrorType.NOT_VALID, entityField);
        }
        if (findAccountByNames(fieldName.toString())) {
            return createValidatorError(ErrorType.NOT_FOUND, entityField);
        }
        return null;
    }

    @Override
    public EntityField getField(MessageDTO messageDTO) {
        return EntityField.builder()
                .field(messageDTO.getToAccountNames())
                .entityId(messageDTO.getId())
                .build();
    }

    private boolean findAccountByNames(String names) {
        return splitBySpace(names)
                .flatMap(name ->
                        accountRepository.findAccountIdByNameAndLastName(name.getFirst(), name.getSecond())).isEmpty();
    }

    @Override
    protected FieldType getType() {
        return FieldType.TO_ACCOUNT_NAMES;
    }
}

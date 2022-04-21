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
public class MessageFromAccountNamesValidatorImpl extends AbstractValidator implements Validator<MessageDTO> {
    private final AccountRepository accountRepository;

    @Override
    public ValidatorError validate(EntityField entityField) {
        if (isNull(entityField.getField().toString())) {
            return createValidatorError(ErrorType.EMPTY);
        }
        if (splitBySpace(entityField.getField().toString()).isEmpty()) {
            return createValidatorError(ErrorType.NOT_VALID);
        }
        if (findAccountByNames(entityField.getField().toString())) {
            return createValidatorError(ErrorType.NOT_FOUND);
        }
        return null;
    }

    @Override
    public EntityField getField(MessageDTO messageDTO) {
        return new EntityField(messageDTO.getFromAccountNames());
    }

    private boolean findAccountByNames(String names) {
        return splitBySpace(names)
                .flatMap(name ->
                        accountRepository.findAccountIdByNameAndLastName(name.getFirst(), name.getSecond())).isEmpty();
    }

    @Override
    protected FieldType getType() {
        return FieldType.FROM_ACCOUNT_NAMES;
    }
}

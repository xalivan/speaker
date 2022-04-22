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

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
            return createValidatorError(ErrorType.EMPTY);
        }
        if (splitBySpace(fieldName.toString()).isEmpty()) {
            return createValidatorError(ErrorType.NOT_VALID);
        }
        if (findAccountByNames(fieldName.toString())) {
            return createValidatorError(ErrorType.NOT_FOUND);
        }
        return null;
    }

    @Override
    public List<ValidatorError> validateList(List<EntityField> entityFields) {
        return entityFields.stream()
                .map(this::validate)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public EntityField getField(MessageDTO messageDTO) {
        return new EntityField(messageDTO.getToAccountNames());
    }

    @Override
    public List<EntityField> getFields(List<MessageDTO> messageDTOs) {
        return messageDTOs.stream()
                .map(this::getField)
                .collect(Collectors.toList());
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

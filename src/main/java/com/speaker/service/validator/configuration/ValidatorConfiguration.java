package com.speaker.service.validator.configuration;

import com.speaker.dto.AccountDTO;
import com.speaker.dto.MessageDTO;
import com.speaker.dto.ValidatorError;
import com.speaker.service.validator.FieldValidators;
import com.speaker.service.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class ValidatorConfiguration {
    private final List<Validator<MessageDTO>> messageValidators;
    private final List<Validator<AccountDTO>> accountValidators;

    @Bean
    public FieldValidators<MessageDTO> messageDTOValidators() {
        return new FieldValidators<MessageDTO>() {
            @Override
            public List<ValidatorError> validate(MessageDTO entity) {
                return validateEntity(messageValidators, entity);
            }

            @Override
            public List<List<ValidatorError>> validate(List<MessageDTO> entities) {
                return validateEntityList(messageValidators, entities);
            }
        };
    }

    @Bean
    public FieldValidators<AccountDTO> accountDTOValidators() {
        return new FieldValidators<AccountDTO>() {
            @Override
            public List<ValidatorError> validate(AccountDTO entity) {
                return validateEntity(accountValidators, entity);
            }

            @Override
            public List<List<ValidatorError>> validate(List<AccountDTO> entities) {
                return validateEntityList(accountValidators, entities);
            }
        };
    }

    private <T> List<ValidatorError> validateEntity(List<Validator<T>> validatorList, T entity) {
        return validatorList.stream()
                .map(validator -> validator.validate(validator.getField(entity)))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private <T> List<List<ValidatorError>> validateEntityList(List<Validator<T>> validatorList, List<T> entity) {
        return validatorList.stream()
                .map(validator -> validator.validateList(validator.getFields(entity)))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}

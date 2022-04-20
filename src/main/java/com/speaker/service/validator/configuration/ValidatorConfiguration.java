package com.speaker.service.validator.configuration;

import com.speaker.dto.AccountDTO;
import com.speaker.dto.MessageDTO;
import com.speaker.dto.ValidatorError;
import com.speaker.service.validator.FieldValidators;
import com.speaker.service.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class ValidatorConfiguration<T, E> {
    private final List<Validator<MessageDTO, String>> messageValidators;
    private final List<Validator<AccountDTO, String>> accountValidators;
    private final Validator<T, E> validator;

    @Bean
    public FieldValidators<MessageDTO> messageDTOValidators() {
        return new FieldValidators<MessageDTO>() {
            @Override
            public List<ValidatorError> validate(MessageDTO entity) {
                return validateEntity(messageValidators, entity);
            }
        };
    }

    private <T, E> List<ValidatorError> validateEntity(List<Validator<T, E>> validatorList, T entity) {
        return validatorList.stream()
                .map(validator -> validator.validate(validator.getField(entity).get()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Bean
    public FieldValidators<AccountDTO> accountDTOValidators() {
        return new FieldValidators<AccountDTO>() {
            @Override
            public List<ValidatorError> validate(AccountDTO entity) {
                return validateEntity(accountValidators, entity);
            }
        };
    }


}

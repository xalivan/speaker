package com.speaker.service.validator.message;

import com.speaker.dto.MessageDTO;
import com.speaker.service.validator.Validator;

import static java.util.Objects.isNull;

public  abstract class AbstractMessageCheckValidator implements Validator<MessageDTO, String> {
    public final String NEW = " NEW ";
    public boolean checkOrNull(String field) {
       return isNull(field);
    }
}

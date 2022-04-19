package com.speaker.repository;

import com.speaker.entities.Message;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.speaker.db.jooq.generated.tables.Message.MESSAGE;

@Repository
@RequiredArgsConstructor
public class MessageRepositoryImpl implements MessageRepository {
    private final DSLContext dsl;

    @Override
    public List<Message> findAllByAccountId(int id) {
        return dsl.select(MESSAGE.ID, MESSAGE.FROM_ACCOUNT_ID, MESSAGE.TO_ACCOUNT_ID, MESSAGE.TEXT, MESSAGE.DATE, MESSAGE.STATUS)
                .from(MESSAGE)
                .where(MESSAGE.FROM_ACCOUNT_ID.eq(id))
                .fetchInto(Message.class);
    }

    @Override
    public boolean createMessages(List<Message> messageList) {
        int sum = Arrays.stream(dsl.batch(messageList.stream()
                        .map(message -> dsl.insertInto(MESSAGE,
                                        MESSAGE.FROM_ACCOUNT_ID, MESSAGE.TO_ACCOUNT_ID,
                                        MESSAGE.DATE, MESSAGE.TEXT, MESSAGE.STATUS)
                                .values(message.getFromAccountId(), message.getToAccountId(),
                                        Timestamp.valueOf(LocalDateTime.now()), message.getText(), message.getStatus().toString()))
                        .collect(Collectors.toList())).execute())
                .sum();
        return sum == messageList.size() && sum > 0;
    }
}

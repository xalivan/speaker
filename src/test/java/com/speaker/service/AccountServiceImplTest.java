package com.speaker.service;

import com.speaker.convertors.AccountConverter;
import com.speaker.convertors.FriendConverter;
import com.speaker.dto.AccountDTO;
import com.speaker.dto.FriendDTO;
import com.speaker.entities.Account;
import com.speaker.entities.CityName;
import com.speaker.entities.CountryName;
import com.speaker.entities.Friend;
import com.speaker.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static com.speaker.utils.AccountDTOGenerator.*;
import static com.speaker.utils.AccountGenerator.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {
    private static final String ACCOUNT_FIRST_NAME = "Ivan";
    private static final String LAST_NAME = "Mazur";
    private static final String SEPARATOR = " ";
    private static final String ACCOUNT_FIRST_LAST_NAME = ACCOUNT_FIRST_NAME + SEPARATOR + LAST_NAME;
    private static final String FRIEND_FIRST_NAME = "Victor";
    private static final String FRIEND_FIRST_LAST_NAME = FRIEND_FIRST_NAME + SEPARATOR + LAST_NAME;
    private static final String FRIEND_FIRST_LAST_NAME_WITH_SPACES = SEPARATOR + SEPARATOR + FRIEND_FIRST_NAME
            + SEPARATOR + SEPARATOR + LAST_NAME + SEPARATOR + SEPARATOR;
    private static final String ACCOUNT_FIRST_LAST_NAME_WITH_SPACES = SEPARATOR + SEPARATOR + ACCOUNT_FIRST_NAME
            + SEPARATOR + SEPARATOR + LAST_NAME + SEPARATOR + SEPARATOR;
    private static final String ACCOUNT_WITH_THREE_FIRST_NAMES= "Ivan Mazur Mazur";
    private static final int NUMBER_INSERTED_RECORDS = 1;
    private static final int ACCOUNT_ID_ONE = 1;
    private static final int FRIEND_ID_TWO = 2;

    @Mock
    private FriendConverter friendConverter;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AccountConverter accountConverter;
    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    public void prepare() {
        when(accountRepository.findAllCountryAndCity())
                .thenReturn(List.of(generateAccount(generateCountry(CountryName.UKRAINE, generateCity(CityName.KYIV)))
                        .getCountry()));
        this.accountService.init();
    }

    @Test
    public void findAllAccounts() {
        Account account = generateAccount(generateCountry(CountryName.UKRAINE, generateCity(CityName.KYIV)));
        when(accountRepository.findAll()).thenReturn(List.of(account));
        AccountDTO accountDTO = generateAccountDTO(generateCountryDTO(CountryName.UKRAINE, generateCityDTO(CityName.KYIV)));
        when(accountConverter.convertToAccountDTO(account)).thenReturn(accountDTO);
        assertThat(accountService.findAll(), is(List.of(accountDTO)));
    }

    @Test
    public void findAllFriendsByAccountId() {
        Account account = generateAccount(generateCountry(CountryName.UKRAINE, generateCity(CityName.KYIV)));
        when(accountRepository.findAllFriendsByAccountId(ACCOUNT_ID_ONE)).thenReturn(List.of(account));
        assertThat(accountService.findAllFriendsByAccountId(ACCOUNT_ID_ONE), is(List.of(account)));
    }

    @Test
    public void accountCreatedSuccess() {
        Account account = generateAccount(generateCountry(CountryName.UKRAINE, generateCity(CityName.KYIV)));
        AccountDTO accountDTO = generateAccountDTO(generateCountryDTO(CountryName.UKRAINE, generateCityDTO(CityName.KYIV)));
        when(accountConverter.convertToAccount(accountDTO, accountDTO.getCountry().getId(),
                accountDTO.getCountry().getCityDTO().getId())).thenReturn(account);
        when(accountRepository.insert(account)).thenReturn(NUMBER_INSERTED_RECORDS);
        assertThat(accountService.create(accountDTO), is(Response.TRUE));
    }

    @Test
    public void accountNotCreatedWhenCountryIsNull() {
        AccountDTO accountDTO = generateAccountDTO(null);
        assertThat(accountService.create(accountDTO), is(Response.FALSE));
    }

    @Test
    public void accountNotCreatedWhenCountryNameIsNull() {
        AccountDTO accountDTO = generateAccountDTO(generateCountryDTO(null, generateCityDTO(CityName.KYIV)));
        assertThat(accountService.create(accountDTO), is(Response.FALSE));
    }

    @Test
    public void accountNotCreatedWhenCityIsNull() {
        AccountDTO accountDTO = generateAccountDTO(generateCountryDTO(CountryName.UKRAINE, null));
        assertThat(accountService.create(accountDTO), is(Response.FALSE));
    }

    @Test
    public void accountNotCreatedWhenCityNameIsNull() {
        AccountDTO accountDTO = generateAccountDTO(generateCountryDTO(CountryName.UKRAINE, generateCityDTO(null)));
        assertThat(accountService.create(accountDTO), is(Response.FALSE));
    }

    @Test
    public void addFriendSuccess() {
        FriendDTO friendDTO = generateFriendDTO(ACCOUNT_FIRST_LAST_NAME, FRIEND_FIRST_LAST_NAME);
        Friend friend = generateFriend();
        when(accountRepository.findAccountIdByNameAndLastName(ACCOUNT_FIRST_NAME, LAST_NAME)).thenReturn(Optional.of(ACCOUNT_ID_ONE));
        when(accountRepository.findAccountIdByNameAndLastName(FRIEND_FIRST_NAME, LAST_NAME)).thenReturn(Optional.of(FRIEND_ID_TWO));
        when(friendConverter.convertToFriend(ACCOUNT_ID_ONE, FRIEND_ID_TWO)).thenReturn(friend);
        when(accountRepository.addFriend(friend)).thenReturn(NUMBER_INSERTED_RECORDS);
        assertThat(accountService.addFriend(friendDTO), is(Response.TRUE));
    }

    @Test
    public void addFriendSuccessWithSpaces() {
        FriendDTO friendDTO = generateFriendDTO(ACCOUNT_FIRST_LAST_NAME_WITH_SPACES, FRIEND_FIRST_LAST_NAME_WITH_SPACES);
        Friend friend = generateFriend();
        when(accountRepository.findAccountIdByNameAndLastName(ACCOUNT_FIRST_NAME, LAST_NAME)).thenReturn(Optional.of(ACCOUNT_ID_ONE));
        when(accountRepository.findAccountIdByNameAndLastName(FRIEND_FIRST_NAME, LAST_NAME)).thenReturn(Optional.of(FRIEND_ID_TWO));
        when(friendConverter.convertToFriend(ACCOUNT_ID_ONE, FRIEND_ID_TWO)).thenReturn(friend);
        when(accountRepository.addFriend(friend)).thenReturn(NUMBER_INSERTED_RECORDS);
        assertThat(accountService.addFriend(friendDTO), is(Response.TRUE));
    }

    @Test
    public void friendNotCreatedWhenAccountNameIsNull() {
        FriendDTO friendDTO = generateFriendDTO(null, FRIEND_FIRST_LAST_NAME);
        assertThat(accountService.addFriend(friendDTO), is(Response.FALSE));
    }

    @Test
    public void friendNotCreatedWhenAccountNamesHasThreeFirstNames() {
        FriendDTO friendDTO = generateFriendDTO(ACCOUNT_WITH_THREE_FIRST_NAMES, FRIEND_FIRST_LAST_NAME);
        assertThat(accountService.addFriend(friendDTO), is(Response.FALSE));
        verify(accountRepository).findAccountIdByNameAndLastName(FRIEND_FIRST_NAME, LAST_NAME);
        verify(accountRepository, never()).addFriend(any(Friend.class));
    }

    @Test
    public void friendNotCreatedWhenAccountNamesHasOnlyFirstName() {
        FriendDTO friendDTO = generateFriendDTO(ACCOUNT_FIRST_NAME, FRIEND_FIRST_LAST_NAME);
        assertThat(accountService.addFriend(friendDTO), is(Response.FALSE));
        verify(accountRepository, never()).findAccountIdByNameAndLastName(ACCOUNT_FIRST_NAME, null);
        verify(accountRepository).findAccountIdByNameAndLastName(FRIEND_FIRST_NAME, LAST_NAME);
        verify(accountRepository, never()).addFriend(any(Friend.class));
    }

    private Friend generateFriend() {
       final Random RANDOM = new Random();
        return Friend.builder()
                .id(RANDOM.nextInt(10))
                .accountId(ACCOUNT_ID_ONE)
                .friendAccountId(FRIEND_ID_TWO)
                .build();
    }

    private FriendDTO generateFriendDTO(String accountName, String friendName) {
        return FriendDTO.builder()
                .accountFirstLastNames(accountName)
                .friendFirstLastNames(friendName)
                .build();
    }
}
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

import static com.speaker.utils.AccountDTOGenerator.*;
import static com.speaker.utils.AccountGenerator.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {
    final String IVAN_MAZUR = "Ivan Mazur";
    final String VICTOR_MAZUR = "Victor Mazur";
    final String IVAN = "Ivan";
    final String MAZUR = "Mazur";
    final String VICTOR = "Victor";
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
        when(accountRepository.findAllFriendsByAccountId(1)).thenReturn(List.of(account));
        assertThat(accountService.findAllFriendsByAccountId(1), is(List.of(account)));
    }

    @Test
    public void accountCreatedSuccess() {
        Account account = generateAccount(generateCountry(CountryName.UKRAINE, generateCity(CityName.KYIV)));
        AccountDTO accountDTO = generateAccountDTO(generateCountryDTO(CountryName.UKRAINE, generateCityDTO(CityName.KYIV)));
        when(accountConverter.convertToAccount(accountDTO, accountDTO.getCountry().getId(),
                accountDTO.getCountry().getCityDTO().getId())).thenReturn(account);
        when(accountRepository.insert(account)).thenReturn(1);
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
        FriendDTO friendDTO = FriendDTO.builder()
                .accountFirstLastNames(IVAN_MAZUR)
                .friendFirstLastNames(VICTOR_MAZUR)
                .build();
        Friend friend = Friend.builder()
                .friendAccountId(1)
                .friendAccountId(2)
                .build();
        when(accountRepository.findAccountIdByNameAndLastName(IVAN, MAZUR)).thenReturn(Optional.of(1));
        when(accountRepository.findAccountIdByNameAndLastName(VICTOR, MAZUR)).thenReturn(Optional.of(2));
        when(friendConverter.convertToFriend(1, 2)).thenReturn(friend);
        when(accountRepository.addFriend(friend)).thenReturn(1);
        assertThat(accountService.addFriend(friendDTO), is(Response.TRUE));
    }

    @Test
    public void addFriendSuccessWithSpaces() {
        FriendDTO friendDTO = FriendDTO.builder()
                .accountFirstLastNames("  Ivan   Mazur  ")
                .friendFirstLastNames(" Victor  Mazur  ")
                .build();
        Friend friend = Friend.builder()
                .friendAccountId(1)
                .friendAccountId(2)
                .build();
        when(accountRepository.findAccountIdByNameAndLastName(IVAN, MAZUR)).thenReturn(Optional.of(1));
        when(accountRepository.findAccountIdByNameAndLastName(VICTOR, MAZUR)).thenReturn(Optional.of(2));
        when(friendConverter.convertToFriend(1, 2)).thenReturn(friend);
        when(accountRepository.addFriend(friend)).thenReturn(1);
        assertThat(accountService.addFriend(friendDTO), is(Response.TRUE));
    }

    @Test
    public void friendNotCreatedWhenAccountNameIsNull() {
        FriendDTO friendDTO = FriendDTO.builder()
                .accountFirstLastNames(null)
                .accountFirstLastNames(VICTOR_MAZUR)
                .build();
        assertThat(accountService.addFriend(friendDTO), is(Response.FALSE));
    }

    @Test
    public void friendNotCreatedWhenAccountNamesHasThreeFirstNames() {
        FriendDTO friendDTO = FriendDTO.builder()
                .accountFirstLastNames("Ivan Mazur Mazur")
                .accountFirstLastNames(VICTOR_MAZUR)
                .build();
        assertThat(accountService.addFriend(friendDTO), is(Response.FALSE));
        verify(accountRepository).findAccountIdByNameAndLastName(VICTOR, MAZUR);
        verify(accountRepository, never()).addFriend(any(Friend.class));
    }

    @Test
    public void friendNotCreatedWhenAccountNamesHasOnlyFirstName() {
        FriendDTO friendDTO = FriendDTO.builder()
                .accountFirstLastNames(IVAN)
                .accountFirstLastNames(VICTOR_MAZUR)
                .build();
        assertThat(accountService.addFriend(friendDTO), is(Response.FALSE));
        verify(accountRepository, never()).findAccountIdByNameAndLastName(IVAN, null);
        verify(accountRepository).findAccountIdByNameAndLastName(VICTOR, MAZUR);
        verify(accountRepository, never()).addFriend(any(Friend.class));
    }
}
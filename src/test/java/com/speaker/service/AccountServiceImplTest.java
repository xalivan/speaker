package com.speaker.service;

import com.speaker.convertors.AccountConverter;
import com.speaker.dto.AccountDTO;
import com.speaker.entities.Account;
import com.speaker.entities.CityName;
import com.speaker.entities.CountryName;
import com.speaker.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.speaker.utils.AccountDTOGenerator.generateAccountDTO;
import static com.speaker.utils.AccountGenerator.generateAccount;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AccountConverter accountConverter;
    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    public void prepare() {
        when(accountRepository.findAllCountryAndCity())
                .thenReturn(List.of(generateAccount("present",
                        CountryName.UKRAINE, "present", CityName.KYIV).getCountry()));
        this.accountService.init();
    }

    @Test
    void findAll() {
        Account account = generateAccount("present", CountryName.UKRAINE, "present", CityName.KYIV);
        when(accountConverter.convertToAccountDTO(account))
                .thenReturn(generateAccountDTO("present", CountryName.UKRAINE, "present", CityName.KYIV));
        when(accountRepository.findAll()).thenReturn(List.of(account));
        assertThat(accountService.findAll(), containsInAnyOrder(accountConverter.convertToAccountDTO(account)));
    }

    @Test
    void findAllFriendsByAccountId() {
        Account account = generateAccount("present", CountryName.UKRAINE, "present", CityName.KYIV);
        when(accountRepository.findAllFriendsByAccountId(1)).thenReturn(List.of(account));
        assertThat(accountService.findAllFriendsByAccountId(1), is(List.of(account)));
    }

    @Test
    void createSuccess() {
        Account account = generateAccount("present", CountryName.UKRAINE, "present", CityName.KYIV);
        AccountDTO accountDTO = generateAccountDTO("present", CountryName.UKRAINE, "present", CityName.KYIV);
        when(accountConverter.convertToAccount(accountDTO, accountDTO.getCountry().getId(),
                accountDTO.getCountry().getCityDTO().getId())).thenReturn(account);
        when(accountRepository.insert(account)).thenReturn(1);
        assertThat(accountService.create(accountDTO), is(Response.TRUE));
    }

    @Test
    void createIfCountryNull() {
        AccountDTO accountDTO = generateAccountDTO(null, CountryName.UKRAINE, "present", CityName.KYIV);
        assertThat(accountService.create(accountDTO), is(Response.FALSE));
    }

    @Test
    void createIfCountryNameNull() {
        AccountDTO accountDTO = generateAccountDTO("present", null, "present", CityName.KYIV);
        assertThat(accountService.create(accountDTO), is(Response.FALSE));
    }

    @Test
    void createIfCityNull() {
        AccountDTO accountDTO = generateAccountDTO("present", CountryName.UKRAINE, null, CityName.KYIV);
        assertThat(accountService.create(accountDTO), is(Response.FALSE));
    }

    @Test()
    void createIfCityNameNull() {
        AccountDTO accountDTO = generateAccountDTO("present", CountryName.UKRAINE, "present", null);
        assertThat(accountService.create(accountDTO), is(Response.FALSE));
    }
}
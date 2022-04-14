package com.speaker.service;

import com.speaker.convertors.AccountConverter;
import com.speaker.convertors.FriendConverter;
import com.speaker.dto.AccountDTO;
import com.speaker.dto.CityDTO;
import com.speaker.dto.CountryDTO;
import com.speaker.dto.FriendDTO;
import com.speaker.entities.*;
import com.speaker.repository.AccountRepository;
import com.speaker.service.util.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private Map<CountryName, Map<CityName, Pair<Integer, Integer>>> mapCountryAndCity;
    private final AccountRepository accountRepository;
    private final AccountConverter accountConverter;
    private final FriendConverter friendConverter;

    @PostConstruct
    public void init() {
        mapCountryAndCity = accountRepository.findAllCountryAndCity().stream()
                .map(country -> new Pair<CountryName, Pair<CityName, Pair<Integer, City>>>(country.getName(),
                        new Pair<CityName, Pair<Integer, City>>(country.getCity().getName(),
                                new Pair<Integer, City>(country.getId(), country.getCity()))
                )).collect(Collectors.groupingBy(Pair::getFirst, Collectors.mapping(Pair::getSecond,
                        Collectors.toMap(Pair::getFirst, this::convertToIds, (city1, city2) -> city1))));
    }

    @Override
    public List<Account> findAllFriendsByAccountId(int accountId) {
        return accountRepository.findAllFriendsByAccountId(accountId);
    }

    @Override
    public Response addFriend(FriendDTO friendDTO) {
        Optional<Integer> accountId = convertFirstAndLastNameToId(friendDTO.getAccountFirstLastNames());
        Optional<Integer> friendId = convertFirstAndLastNameToId(friendDTO.getFriendFirstLastNames());
        if (accountId.isPresent() && friendId.isPresent()) {
            accountRepository.addFriend(friendConverter.convertToFriend(accountId.get(), friendId.get()));
            return Response.TRUE;
        }
        return Response.FALSE;
    }

    @Override
    public List<AccountDTO> findAll() {
        return accountRepository.findAll().stream()
                .map(accountConverter::convertToAccountDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Response create(AccountDTO accountDTO) {
        CountryDTO countryDTO = accountDTO.getCountry();
        if (nonNull(accountDTO.getCountry()) && nonNull(countryDTO.getName())) {
            Map<CityName, Pair<Integer, Integer>> cityNamePairMap = mapCountryAndCity.get(countryDTO.getName());
            CityDTO cityDTO = countryDTO.getCityDTO();
            if (nonNull(cityDTO) && nonNull(cityDTO.getName())) {
                Pair<Integer, Integer> integerIntegerPair = cityNamePairMap.get(cityDTO.getName());
                accountRepository.insert(accountConverter
                        .convertToAccount(accountDTO, integerIntegerPair.getFirst(), integerIntegerPair.getSecond()));
                return Response.TRUE;
            }
        }
        return Response.FALSE;
    }

    private Optional<Integer> convertFirstAndLastNameToId(String names) {
        if (nonNull(names)) {
            String[] splitNames = names.trim().split("\\s+");
            if (splitNames.length == 2) {
                return accountRepository.findAccountIdByNameAndLastName(splitNames[0], splitNames[1]);
            }
        }
        return Optional.empty();
    }

    private Pair<Integer, Integer> convertToIds(Pair<CityName, Pair<Integer, City>> entry) {
        return new Pair<>(entry.getSecond().getFirst(), entry.getSecond().getSecond().getId());
    }
}

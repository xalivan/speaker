package com.speaker.service;

import com.speaker.convertors.AccountConverter;
import com.speaker.dto.AccountDTO;
import com.speaker.entities.*;
import com.speaker.repository.AccountRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountConverter accountConverter;

    @Override
    public List<AccountDTO> findAll() {
        return accountRepository.findAll().stream()
                .map(accountConverter::convertToAccountDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Account> findAllFriendsByAccountId(int accountId) {
        return accountRepository.findAllFriendsByAccountId(accountId);
    }

    @Override
    public boolean create(AccountDTO accountDTO) {
        Map<CityName, Pair<Integer, Integer>> cityNamePairMap = getAllCountryAndCity().get(accountDTO.getCountry().getName());
        Pair<Integer, Integer> integerIntegerPair = cityNamePairMap.get(accountDTO.getCountry().getCityDTO().getName());
        return accountRepository.insert(accountConverter
                .convertToAccount(accountDTO, integerIntegerPair.getFirst(), integerIntegerPair.getSecond())) > 0;
    }

    @PostConstruct
    private Map<CountryName, Map<CityName, Pair<Integer, Integer>>> getAllCountryAndCity() {
        List<Country> allCountryAndCity = accountRepository.findAllCountryAndCity();
        return allCountryAndCity.stream()
                .map(country -> new Pair<CountryName, Pair<CityName, Pair<Integer, City>>>(country.getName(),
                        new Pair<CityName, Pair<Integer, City>>(country.getCity().getName(),
                                new Pair<Integer, City>(country.getId(), country.getCity()))
                )).collect(Collectors.groupingBy(Pair::getFirst, Collectors.mapping(Pair::getSecond, Collectors.toMap(Pair::getFirst, this::convertToIds, (city1, city2) -> city1))));
    }

    private Pair<Integer, Integer> convertToIds(Pair<CityName, Pair<Integer, City>> entry) {
        return new Pair<>(entry.getSecond().getFirst(), entry.getSecond().getSecond().getId());
    }

    @Data
    class Pair<A, B> {
        private A first;
        private B second;

        public Pair(A first, B second) {
            this.first = first;
            this.second = second;
        }
    }
}

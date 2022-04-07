package com.speaker.service;

import com.speaker.convertors.AccountConverter;
import com.speaker.dto.AccountDTO;
import com.speaker.entities.*;
import com.speaker.repository.AccountRepository;
import liquibase.pro.packaged.A;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
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
        List<Country> allCountryAndCity = accountRepository.findAllCountryAndCity();
        Map<CountryName, List<Pair>> map = allCountryAndCity.stream()
                .map(country -> new Pair<CountryName, Integer, Pair>(country.getName(), country.getId(), new Pair<CityName, Integer, City>(country.getCity().getName(), country.getId(), country.getCity())))
                .collect(Collectors.groupingBy(Pair::getName, Collectors.mapping(Pair::getObject, Collectors.toList())));
        int countryId = getCountryId(accountDTO, map);
        List<Pair> pairs = map.get(accountDTO.getCountry().getName());
        int cityId = getCityId(pairs, accountDTO);
        return accountRepository.insert(accountConverter.convertToAccount(accountDTO, countryId, cityId)) > 0;
    }

    @Data
    class Pair<A, B, C> {
        private A name;
        private B id;
        private C object;

        public Pair(A name, B id, C object) {
            this.name = name;
            this.id = id;
            this.object = object;
        }
    }

    private int getCountryId(AccountDTO accountDTO, Map<CountryName, List<Pair>> map) {
        return map.get(accountDTO.getCountry().getName()).stream()
                .filter(country -> country.getName().equals(accountDTO.getCountry().getCityDTO().getName()))
                .mapToInt(country -> (Integer) country.getId()).findFirst().getAsInt();
    }

    private int getCityId(List<Pair> pairs, AccountDTO accountDTO) {
        List<City> cityList = pairs.stream()
                .filter(pair -> pair.getName().equals(accountDTO.getCountry().getCityDTO().getName()))
                .map(pair -> (City) pair.getObject()).collect(Collectors.toList());
        return cityList.stream().mapToInt(City::getId).findFirst().getAsInt();
    }
}

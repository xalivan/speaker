package com.speaker.service;

import com.speaker.convertors.AccountConverter;
import com.speaker.dto.AccountDTO;
import com.speaker.entities.Account;
import com.speaker.entities.City;
import com.speaker.entities.CityName;
import com.speaker.entities.CountryName;
import com.speaker.repository.AccountRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Data
public class AccountServiceImpl implements AccountService {
    private Map<CountryName, Map<CityName, Pair<Integer, Integer>>> mapCountryAndCity;
    private final AccountRepository accountRepository;
    private final AccountConverter accountConverter;

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
//        Map<CityName, Pair<Integer, Integer>> cityNamePairMap1 = Optional.ofNullable(accountDTO.getCountry())
//                .map(countryDTO -> Optional.ofNullable(countryDTO.getName()).map(mapCountryAndCity::get).get())
//                .get();
        Map<CityName, Pair<Integer, Integer>> cityNamePairMap = mapCountryAndCity.get(accountDTO.getCountry().getName());
        Pair<Integer, Integer> integerIntegerPair = cityNamePairMap.get(accountDTO.getCountry().getCityDTO().getName());
        return accountRepository.insert(accountConverter
                .convertToAccount(accountDTO, integerIntegerPair.getFirst(), integerIntegerPair.getSecond())) > 0;
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

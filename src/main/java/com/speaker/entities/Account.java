package com.speaker.entities;

import java.util.Objects;

public class Account {
    final int id;
    final String name;
    final String lastName;
    final int age;
    final int countryId;
    final int cityId;

    public Account(int id, String name, String lastName, int age, int countryId, int cityId) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.countryId = countryId;
        this.cityId = cityId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public int getCountryId() {
        return countryId;
    }

    public int getCityId() {
        return cityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id && age == account.age && countryId == account.countryId && cityId == account.cityId && Objects.equals(name, account.name) && Objects.equals(lastName, account.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastName, age, countryId, cityId);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", countryId=" + countryId +
                ", cityId=" + cityId +
                '}';
    }
}

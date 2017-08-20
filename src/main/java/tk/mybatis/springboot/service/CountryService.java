package tk.mybatis.springboot.service;

import tk.mybatis.springboot.model.Country;

import java.util.List;

public interface CountryService {
    List<Country> selectAll();
    Country selectById(Long id);
    void insertCountry(Country country);
}

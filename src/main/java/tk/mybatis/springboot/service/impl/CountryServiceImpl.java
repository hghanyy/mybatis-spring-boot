package tk.mybatis.springboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.springboot.mapper.CountryMapper;
import tk.mybatis.springboot.model.Country;
import tk.mybatis.springboot.service.CountryService;

import java.util.List;

@Service
@Transactional
public class CountryServiceImpl implements CountryService {

    @Autowired
    private transient CountryMapper countryMapper;
    @Override
    public List<Country> selectAll() {
        return countryMapper.selectAll();
    }

    @Override
    public Country selectById(Long id) {
        return countryMapper.selectById(id);
    }

    @Override
    public void insertCountry(Country country) {
        countryMapper.insertCountry(country);
    }
}

package tk.mybatis.springboot.mapper;

import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.springboot.model.Country;

import java.util.List;
public interface CountryMapper {
    List<Country>selectAll();

    Country selectById(Long id);

    void insertCountry(Country country);

}

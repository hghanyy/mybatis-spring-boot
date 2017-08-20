package tk.mybatis.springboot.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.springboot.model.Country;
import tk.mybatis.springboot.service.CountryService;

import java.util.List;

@RestController
public class CountryController {

    @Autowired
    private CountryService countryService;
    @RequestMapping(value="/api/country/selectAll",method= RequestMethod.GET)
    public List<Country>selectAll()
    {
        return countryService.selectAll();
    }

    @RequestMapping(value="/api/country/selectById/{id}",method = RequestMethod.GET)
    public Country selectById(@PathVariable Long id)
    {
        Country country=new Country();
        country.setCountryName("英国");
        country.setCountryCode("04");
        countryService.insertCountry(country);
        return countryService.selectById(id);
    }
    @RequestMapping(value="/api/country/insertCountry",method = RequestMethod.GET)
    public String insertCountry(Country country)
    {
      countryService.insertCountry(country);
      return "插入成功";
    }

}

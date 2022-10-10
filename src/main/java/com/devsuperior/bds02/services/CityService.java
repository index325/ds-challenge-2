package com.devsuperior.bds02.services;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.services.exceptions.DatabaseException;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CityService {
    @Autowired
    CityRepository cityRepository;

    public List<CityDTO> findAll() {
        List<City> cities = cityRepository.findAll(Sort.by("name"));

        return cities.stream().map((city) -> new CityDTO(city)).collect(Collectors.toList());
    }

    public CityDTO insert(CityDTO cityDTO) {
        City city = new City( );

        BeanUtils.copyProperties(cityDTO, city);

        return new CityDTO(cityRepository.save(city));
    }

    public void delete(Long id) {
        try {
            cityRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("This city that you`re trying to delete was not found");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation check your request and try again");
        }
    }
}

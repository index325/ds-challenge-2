package com.devsuperior.bds02.services;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    CityRepository cityRepository;

    public EventDTO update(Long id, EventDTO eventDTO) {

        try{
            Event event = eventRepository.findById(id).get();
            City city =  cityRepository.findById(eventDTO.getCityId()).get();

            event.setName(eventDTO.getName());
            event.setDate(eventDTO.getDate());
            event.setUrl(eventDTO.getUrl());
            event.setCity(city);

            return new EventDTO(eventRepository.save(event));
        }
        catch (NoSuchElementException e) {
            throw new ResourceNotFoundException("This event was not found to be updated");
        }


    }
}

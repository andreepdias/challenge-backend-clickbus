package io.github.andreepdias.clickbus.resource.controller;

import io.github.andreepdias.clickbus.exception.ObjectNotFoundException;
import io.github.andreepdias.clickbus.resource.dto.PlaceDTO;
import io.github.andreepdias.clickbus.service.PlaceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/places")
public class PlaceController {

    private final PlaceService service;

    public PlaceController(PlaceService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public PlaceDTO getById(@PathVariable Long id){
        try{
            return service.getById(id);
        }catch (ObjectNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @GetMapping
    public List<PlaceDTO> getAll(){
        return service.getAll();
    }

    @GetMapping(params = "name")
    public List<PlaceDTO> getByName(@RequestParam String name){
        return service.getByName(name);
    }

    @PostMapping
    public PlaceDTO create(@RequestBody PlaceDTO dto){
        return service.create(dto);
    }

    @PostMapping("{id}")
    public PlaceDTO update(@RequestBody PlaceDTO dto, @PathVariable Long id){
        try{
            return service.update(dto, id);
        }catch (ObjectNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }
}

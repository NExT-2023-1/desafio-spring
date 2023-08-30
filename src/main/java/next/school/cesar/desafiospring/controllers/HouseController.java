package next.school.cesar.desafiospring.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import next.school.cesar.desafiospring.dto.HouseDTO;
import next.school.cesar.desafiospring.dto.HouseUpdateDTO;
import next.school.cesar.desafiospring.entities.House;
import next.school.cesar.desafiospring.services.HouseService;

@RestController
@RequestMapping("/houses")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class HouseController {
    
    private final HouseService houseService;

    @GetMapping
    public ResponseEntity<List<House>> listAll(@RequestParam Optional<String> zipcode) {
        List<House> listHouses = this.houseService.listAll(zipcode);
        return new ResponseEntity<>(listHouses, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<House> create(@RequestBody HouseDTO houseDTO) {
        House house = houseService.create(houseDTO);
        return new ResponseEntity<House>(house, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<House> update(@PathVariable long id, @RequestBody @Valid HouseUpdateDTO houseUpdateDTO) {
        House house = houseService.update(id, houseUpdateDTO);
        if (house != null){
            return new ResponseEntity<>(house, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        if (houseService.delete(id)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

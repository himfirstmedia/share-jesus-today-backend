package com.himfirst.vidshare.person;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/person")
public class PersonController  {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<PersonModel> authenticateUser(@Valid @RequestBody PersonModel person){
        return new ResponseEntity<>(personService.signUp(person), HttpStatus.OK);
    }

}

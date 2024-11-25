package com.himfirst.vidshare.person;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/person")
public class PersonController  {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<PersonModel> signUp(@Valid @RequestBody PersonModel person){
        return new ResponseEntity<>(personService.signUp(person), HttpStatus.CREATED);
    }

    @PutMapping("/verify-otp")
    public ResponseEntity<PersonModel> verifyOtp(@RequestParam String email, @RequestParam int otp){
        return new ResponseEntity<>(personService.verifyOTP(email, otp), HttpStatus.OK);
    }

    @PutMapping("/set-password")
    public ResponseEntity<PersonModel> setPassword(@RequestParam String email, @RequestParam String password){
        return new ResponseEntity<>(personService.setPassword(email, password), HttpStatus.OK);
    }

    @GetMapping("/find-by-email/{email}")
    public ResponseEntity<PersonModel> findByEmail(@Valid @PathVariable String email){
        return new ResponseEntity<>(personService.findByEmail(email), HttpStatus.OK);
    }

}

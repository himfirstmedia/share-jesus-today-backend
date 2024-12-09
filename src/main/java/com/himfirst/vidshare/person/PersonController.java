package com.himfirst.vidshare.person;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/person")
@CrossOrigin
public class PersonController  {

    private final PersonService personService;
    private final EmailService emailService;

    public PersonController(PersonService personService, EmailService emailService) {
        this.personService = personService;
        this.emailService = emailService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<PersonModel> signUp( @Valid @RequestBody PersonModel person){
        return new ResponseEntity<>(personService.signUp(person), HttpStatus.CREATED);
    }

    @PutMapping("/sign-up/verify-otp")
    public ResponseEntity<PersonModel> verifyOtp(@RequestParam String email, @RequestParam int otp){
        return new ResponseEntity<>(personService.verifyOTP(email, otp), HttpStatus.OK);
    }

    @PutMapping("/sign-up/set-password")
    public ResponseEntity<PersonModel> setPassword(@RequestParam String email, @RequestParam String password){
        return new ResponseEntity<>(personService.setPassword(email, password), HttpStatus.OK);
    }

    @GetMapping("/sign-up/find-by-email/{email}")
    public ResponseEntity<PersonModel> findByEmail(@Valid @PathVariable String email){
        return new ResponseEntity<>(personService.findByEmail(email), HttpStatus.OK);
    }

    @PostMapping("/sign-up/email-test/{email}")
    public ResponseEntity<String> signUp(@PathVariable String email){
        //String to, String subject, String text
        emailService.sendSimpleMessage(email, "SHARE JESUS TODAY TEST", "00000");
        return new ResponseEntity<>("Sent", HttpStatus.OK);
    }

}

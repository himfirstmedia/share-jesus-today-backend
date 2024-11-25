package com.himfirst.vidshare.person;

import com.himfirst.vidshare.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonModel extends BaseModel {
    @NotNull(message = "First Name must be provided")
    @Size(min = 3, message = "First Name must be equal or greater than 3 characters")
    private String firstName;
    @NotNull(message = "Last Name must be provided")
    @Size(min = 3, message = "Last Name must be equal or greater than 3 characters")
    private String lastName;
    @NotNull(message = "Email must be provided")
    //validate email
    private String email;
    private String city;
    private String phone;
    private String address;
    @NotNull(message = "Password must be provided")
    @Size(min = 4, message = "Last Name must be equal or greater than 4 characters")
    private String password;
}

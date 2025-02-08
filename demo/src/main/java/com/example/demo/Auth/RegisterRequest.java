package com.example.demo.Auth;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // nos permite crear setter y getter sin nececsidad de @getter @setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotEmpty(message = "Username is requiered")
    String username;

    @NotEmpty(message = "Password is requiered")
    @Size(min = 6, max = 18, message = "Your password should it be between 6 and 18")
    String password;

    @NotEmpty(message = "FirstName is requiered")
    String firstName;

    @NotEmpty(message = "LastName is requiered")
    String lastName;

    @NotEmpty(message = "Country is requiered")
    String country;

}

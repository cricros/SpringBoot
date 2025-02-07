package com.example.demo.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // nos permite crear setter y getter sin nececsidad de @getter @setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    String username, password;

}

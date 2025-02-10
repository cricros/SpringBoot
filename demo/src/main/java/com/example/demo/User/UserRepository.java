package com.example.demo.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


// el repository nos permite utilizar metodos del jpa para hacer consultas
public interface UserRepository extends JpaRepository<User, Integer> {
    // nos puede permitir regresar un null o no
    // se implementa un queryMethods personalizado
    Optional<User> findByUsername(String username);

}

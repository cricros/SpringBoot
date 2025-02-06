package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.security.config.Jwttoken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user/users")
public class UserController {

    // para iniciar las logs
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    // para iniciar el service
    private final UserService userService;
    // para iniciar el jwtoken
    private final Jwttoken jwttoken;


    // para inyectar dependencias
    @Autowired
    public UserController(UserService userService, Jwttoken jwttoken){
        logger.info("Iniciando el controller");
        this.userService = userService;
        this.jwttoken = jwttoken;
    }

    // metodo get
    @GetMapping("/allUsers") // funciona
    public List<User> getUsers(){
        logger.info("Iniciando el metodo getUsers() en controller: ");
        // se declara el metodo para el service, falta implementarlo
        logger.info("Terminando el metodo getUsers() en controller: ");
        return userService.getAllUsers();
    }

    @GetMapping("/test") // funciona
    public String getTest(){
        logger.info("Iniciando el metodo getTest() en controller: ");
        // se declara el metodo para el service, falta implementarlo
        return "endpoint correcto";
    }

    // Obtener user por id
    @GetMapping("/user_{id}") // funciona
    public ResponseEntity<User>getUserById(@PathVariable Long id){
        logger.info("Iniciando el metodo getUserById() en controller id: {}", id);
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()){
            logger.info("User found: {}",  user.get().getName());
            logger.info("Terminando el metodo getUserById() en controller: ");
            return ResponseEntity.ok(user.get()); // 200 ok
        } else {
            logger.error("User NOT found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404
        }
    }

    // crear un nuevo usuario
    @PostMapping("/newUser") // funciona
    public ResponseEntity<User> createUser(@RequestBody User user){
        logger.info("Iniciando el metodo createUser() en controller");
        // TODO: implementar el metodo createUser en el service
        logger.info("Terminando el metodo createUser() en controller");
        // retorna un 201
        return ResponseEntity.status(HttpStatus.CREATED).body(new User());
    }

    // actualizar usuario por id
    @PutMapping("/{id}") // funciona
    public ResponseEntity<User> updateUserById(@PathVariable Long id, @RequestBody User userDetails){
        logger.info("Iniciando el metodo updateUserById() en controller {} y {}", id, userDetails);
        // TODO: implementar el metodo updateUserById en el service
        userService.updateUserById(id, userDetails);
        logger.info("Terminando el metodo updateUserById() en controller");
        // regresa un 204 con no content solo la confirmacion
        return ResponseEntity.noContent().build();
    }

    // eliminar por Id
    @DeleteMapping("/{id}") // funciona
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        logger.info("Iniciando el metodo deleteById() en controller {}", id);
        Optional<User> user = userService.getUserById(id);
        // TODO: implementar el metodo deleteById en el service
        if(user.isPresent()) {
            userService.deleteUser(id);
            logger.info("Terminando el metodo deleteById() en controller");
            return ResponseEntity.ok("Eliminado exitosamente");
        } else {
            logger.error("Terminando el metodo deleteById() en controller");
            return ResponseEntity.notFound().build();
        }
    }
}


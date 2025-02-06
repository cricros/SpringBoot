package com.example.demo.service;

import com.example.demo.controller.UserController;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    // para iniciar las logs
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    // para declarar el repository
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        logger.info("Obteniendo TODOS los usuarios con el metodo getAllUsers() en service: ");
        // metodos directos con el JPA
        logger.info("Terminando el metodo getAllUsers() en service: ");
        return userRepository.findAll();
    }

    // Optional ? dice que nos permite manejar si existe o no
    public Optional<User>getUserById(Long id){
        logger.info("Obteniendo UN usuario con el metodo getUserById() en service: ");
        logger.info("Terminando el metodo getUserById() en service: ");
        return userRepository.findById(id);

    }

    public User createUser(User user){
        logger.info("Creand el usuario con el metodo createUser() en service: ");
        logger.info("Terminando la creacion del usuario con el metodo createUser() en service: ");
        return userRepository.save(user);
    }

    public User updateUserById(Long id, User userDetails){
        logger.info("Actualizando un usuario con el metodo updateUserById() en service: ");
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(userDetails.getName());
                    logger.info("Terminando la actualizacion un usuario con el metodo updateUserById() en service: ");
                    return userRepository.save(user);
                }).orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    public void deleteUser(Long id){
        logger.info("Eliminando un usuario con el metodo deleteUser() en service: ");
        // validar si existe, si pasa eso eliminar; si no, excepcion
        if(userRepository.existsById(id)){
            logger.info("Se elimino un usuario con el metodo deleteUser() en service: ");
            userRepository.deleteById(id);
        } else {
            logger.error("ERROR");
            throw new RuntimeException("User not found");
        }
    }
}

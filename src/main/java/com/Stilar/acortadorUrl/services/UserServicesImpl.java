package com.Stilar.acortadorUrl.services;

import com.Stilar.acortadorUrl.exceptions.DuplicatedKeyException;
import com.Stilar.acortadorUrl.exceptions.NotFoundException;
import com.Stilar.acortadorUrl.exceptions.ServiceException;
import com.Stilar.acortadorUrl.interfaces.IServiceUser;
import com.Stilar.acortadorUrl.models.MessageModel;
import com.Stilar.acortadorUrl.models.UserModel;
import com.Stilar.acortadorUrl.repositorys.IUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServicesImpl implements IServiceUser {
    private static final Logger log = LoggerFactory.getLogger(UserServicesImpl.class);

    @Autowired
    private IUser iUser;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override

    //fucnion para crear y actualizar
    public MessageModel save(UserModel user) {
        try {
            if (user.getEmail() != null) {
                user.setEmail(user.getEmail().toLowerCase());
                if (iUser.findByEmail(user.getEmail()).isPresent()) {
                    throw new DuplicatedKeyException(false, "El usuario con ese Email ya existe.");
                }
            }
            if (user.getUsername() != null) {
                if (iUser.findByUsername(user.getUsername()).isPresent()) {
                    throw new DuplicatedKeyException(false, "El usuario con ese Usuario ya existe.");
                }
            }

            if (user.getId() != null) {
                return iUser.findById(user.getId()).map(existingUser -> {
                    if(user.getPassword()!=null){
                        existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
                    }
                    if(user.getEmail()!=null){
                        existingUser.setEmail(user.getEmail());
                    }
                    if(user.getUsername()!=null){
                        existingUser.setUsername(user.getUsername());
                    }
                    iUser.save(existingUser);
                    return new MessageModel(true, "El usuario se actualizó correctamente");
                }).orElseThrow(() -> new NotFoundException(false, "El usuario con el ID: " + user.getId() + " no existe."));
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            iUser.save(user);
            return new MessageModel(true, "El usuario se guardó correctamente");

        } catch (DuplicatedKeyException | NotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new ServiceException(false, "Ocurrió un error en el servidor");
        }
    }


    @Override
    // Función para traer todos los usuarios de la base de datos
    public List<UserModel> findAll() {
        try {
            return iUser.findAll();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new ServiceException(false, "Ocurrió un error en el servidor");
        }
    }

    @Override
    // Función para buscar un usuario por su ID
    public MessageModel findById(String id) {
        try {
            Optional<UserModel> user = iUser.findById(id);
            return user.map(value -> new MessageModel(true, "Se encontró el usuario", value))
                    .orElseThrow(() -> new NotFoundException(false, "No se encontró el usuario"));
        } catch (NotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new ServiceException(false, "Ocurrió un error en el servidor");
        }
    }

    @Override
    // Función para borrar un usuario por su ID
    public MessageModel deleteById(String id) {
        try {
            return iUser.findById(id).map(user -> {
                iUser.deleteById(id);
                return new MessageModel(true, "El usuario se eliminó correctamente");
            }).orElseThrow(() -> new NotFoundException(false, "El usuario no se encuentra en la base de datos."));
        } catch (NotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new ServiceException(false, "Ocurrió un error en el servidor");
        }
    }

    @Override
    public MessageModel findByUsername(String username) {
        try {
            Optional<UserModel> user = iUser.findByUsername(username);
            return user.map(userFound -> new MessageModel(true, "Se encontro el Usuario con su Username", userFound))
                    .orElseGet(() -> iUser.findByEmail(username).map(userFound -> new MessageModel(true, "Se encontro el usuario con su email", userFound))
                            .orElseThrow(() -> new NotFoundException(false, "No se encontro ningun Usuario")));

        } catch (NotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new ServiceException(false, "Ocurrió un error en el servidor");

        }

    }
}

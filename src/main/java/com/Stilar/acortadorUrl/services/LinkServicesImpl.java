package com.Stilar.acortadorUrl.services;

import com.Stilar.acortadorUrl.exceptions.NotFoundException;
import com.Stilar.acortadorUrl.exceptions.RequestException;
import com.Stilar.acortadorUrl.exceptions.ServiceException;
import com.Stilar.acortadorUrl.interfaces.IServiceLink;
import com.Stilar.acortadorUrl.models.LinkModel;
import com.Stilar.acortadorUrl.models.MessageModel;
import com.Stilar.acortadorUrl.repositorys.ILink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class LinkServicesImpl implements IServiceLink {
    private static final Logger log = LoggerFactory.getLogger(LinkServicesImpl.class);
    @Autowired
    private ILink ilink;

    @Override
    //Funcion para guardar y acualizar links en la Base de Datos
    public MessageModel save(LinkModel linkModel, String userId) {
        try {


            if (linkModel.getId() != null) {
                return ilink.findById(linkModel.getId()).map(user1 -> {
                    ilink.save(linkModel);
                    return new MessageModel(true, "El Link se actualizó correctamente");
                }).orElseThrow(()->new RequestException(false, "EL Link con el ID: " + linkModel.getId() + " No existe."));
            }

            linkModel.setShortUrl(generateShortUrl());
            linkModel.setIdUser(userId);
            linkModel.setVisitcount(0);
            ilink.save(linkModel);
            return new MessageModel(true, "El Link se guardó correctamente");

        }catch (RequestException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new ServiceException(false, "Ocurrió un error en el servidor");
        }
    }

    //Funcion para aumentar el valor de visitas de un link
    public void updateCounter(LinkModel linkModel) {
        try {
            linkModel.setVisitcount(linkModel.getVisitcount() + 1);
             ilink.save(linkModel);
        } catch (Exception ex) {
            throw new ServiceException(false, "Ocurrio un error en el servidor");
        }
    }


    @Override
    //Funcion para traer todos los links de la base de datos
    public List<LinkModel> findAll() {
        try {
            return ilink.findAll();
        } catch (Exception ex) {
            throw new ServiceException(false, "Ocurrio un error en el servidor");
        }
    }

    @Override
    //Funcion para buscar un link por su ID
    public MessageModel findById(String id) {
        try {
            Optional<LinkModel> link = ilink.findById(id);
            return link.map(value -> new MessageModel(true, "Se encontro el Link", value)).orElseGet(() -> new MessageModel(false, "No se encontro el Link"));
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new ServiceException(false, "Ocurrió un error en el servidor");
        }
    }

    @Override
    //Funcion para buscar un link por su IDUser
    public MessageModel findByIdUser(String id) {
        try {
            List<LinkModel> linkModels = ilink.findByIdUser(id);
            if (linkModels.isEmpty()) {
                throw  new NotFoundException(false, "No se encontraron Links");
            }
            return new MessageModel(true, "Se encontraron Links", linkModels);
        } catch (NotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new ServiceException(false, "Ocurrió un error en el servidor");
        }
    }

    @Override
    //Funcion para Borrar un link por su ID
    public MessageModel deleteById(String id) {
        try {
            return ilink.findById(id).map(user -> {
                ilink.deleteById(id);
                return new MessageModel(true, "El Link se elimino Correctamente");
            }).orElseThrow(()-> new NotFoundException(false, "El Link no se encuentra en la base de datos."));
        } catch (NotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new ServiceException(false, "Ocurrió un error en el servidor");
        }
    }

    //Funcion para encontrar un link por su shortUrl
    public MessageModel findByShortUrl(String shortUrl) {
        try {
            Optional<LinkModel> link = ilink.findByShortUrl(shortUrl);
            return link.map(value -> new MessageModel(true, "Se encontro el Link", value)).orElseThrow(() -> new NotFoundException(false, "No se encontro el Link"));
        } catch (NotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new ServiceException(false, "Ocurrió un error en el servidor");
        }
    }
    //Funcion para generar los shortUrl
    private String generateShortUrl() {
        byte[] array = new byte[6];
        ThreadLocalRandom.current().nextBytes(array);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(array);
    }
}

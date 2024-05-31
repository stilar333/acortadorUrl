package com.Stilar.acortadorUrl.repositorys;

import com.Stilar.acortadorUrl.models.LinkModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ILink extends MongoRepository <LinkModel, String>{
    public abstract List<LinkModel> findByIdUser(String idUser);
    public abstract Optional<LinkModel> findByShortUrl(String shortUrl);

}

package com.Stilar.acortadorUrl.interfaces;

import com.Stilar.acortadorUrl.models.LinkModel;
import com.Stilar.acortadorUrl.models.MessageModel;

import java.util.List;

public interface IServiceLink {
    public MessageModel save(LinkModel linkModel, String userID);
    public List<LinkModel> findAll();
    public MessageModel findById(String id);
    public MessageModel findByIdUser(String id);
    public MessageModel deleteById(String id);

}

package com.Stilar.acortadorUrl.interfaces;

import com.Stilar.acortadorUrl.models.MessageModel;
import com.Stilar.acortadorUrl.models.UserModel;


import java.util.List;

public interface IServiceUser {
    public MessageModel save(UserModel user);
    public List<UserModel> findAll();
    public MessageModel findById(String id);
    public MessageModel deleteById(String id);
    public MessageModel findByUsername(String username);
}

package com.vsk.task.repo;

import com.vsk.task.model.User;

import java.util.HashMap;

public class UserRepo {
    private static HashMap<Long, User> users=new HashMap<Long, User>();

    private UserRepo(){

    }
    public static HashMap<Long, User> getUsers() {
        return users;
    }
}

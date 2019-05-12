package com.vsk.task.dao;

import com.vsk.task.model.User;
import com.vsk.task.repo.UserRepo;

import java.util.Map;

public class UserDao {
    Map<Long, User> userRepo=UserRepo.getUsers();
    private long currentId=123;

    public User createUser(User user){
        long nextId=currentId++;
        user.setId(nextId);
        userRepo.put(nextId,user);
        return userRepo.get(nextId);
    }

    public User getUser(long id){
        return userRepo.get(id);
    }

    public void deleteUser(long id){
        userRepo.remove(id);
    }
}

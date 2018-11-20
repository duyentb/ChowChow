package com.chowchow.os.chowchow.realm;

import com.chowchow.os.chowchow.model.User;

import io.realm.Realm;
import io.realm.RealmResults;

public class UserDAO {
    private Realm realm;

    public UserDAO() {
        realm = Realm.getDefaultInstance();
    }

    //
    public RealmResults<User> getAll() {
        realm.beginTransaction();
        RealmResults<User> rs = realm.where(User.class).findAll();
        realm.commitTransaction();
        return rs;
    }

    public User getUser(String userName, String password) {
        realm.beginTransaction();
        User user = realm.where(User.class).equalTo("userName", userName).equalTo("password", password).findFirst();
        realm.commitTransaction();
        return user;
    }

    public User getUserLogin() {
        realm.beginTransaction();
        User user = realm.where(User.class).equalTo("isLogin", true).findFirst();
        realm.commitTransaction();
        return user;
    }

    public boolean checkExistUser(String userName) {
        boolean isExist;
        realm.beginTransaction();
        User usr = realm.where(User.class).equalTo("userName", userName).findFirst();
        realm.commitTransaction();

        if (usr != null) {
            isExist = true;
        } else {
            isExist = false;
        }
        return isExist;
    }

    // insert
    public void insertUser(User user) {
        realm.beginTransaction();
        realm.insertOrUpdate(user);
        realm.commitTransaction();
    }

    // update
    public void updateUser(User user) {
        realm.beginTransaction();
        realm.insertOrUpdate(user);
        realm.commitTransaction();
    }

    public void updateLogin(String userName, boolean isLogin) {
        User user = realm.where(User.class).equalTo("userName", userName).findFirst();
        realm.beginTransaction();
        if (user != null) {
            user.setLogin(isLogin);
        }
        realm.commitTransaction();
    }

    // delete
    public void deleteUser(User user) {
        realm.beginTransaction();
        user.deleteFromRealm();
        realm.commitTransaction();
    }
}

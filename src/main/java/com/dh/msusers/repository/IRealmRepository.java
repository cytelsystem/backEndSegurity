package com.dh.msusers.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRealmRepository {

    public String createRealm(String realm);


}

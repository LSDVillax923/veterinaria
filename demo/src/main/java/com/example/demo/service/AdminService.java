package com.example.demo.service;

import java.util.Collection;

import com.example.demo.entities.Admin;

public interface AdminService {

    Admin searchById(Long id);

    Collection<Admin> searchAll();

    void save(Admin admin);

    void update(Long id, Admin datos);

    void delete(Long id);

    Admin login(String correo, String contrasenia);
}


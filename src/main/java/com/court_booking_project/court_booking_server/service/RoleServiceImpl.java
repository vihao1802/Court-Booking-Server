package com.court_booking_project.court_booking_server.service;

import com.court_booking_project.court_booking_server.entity.Role;
import com.court_booking_project.court_booking_server.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role get(String id) {
        return roleRepository.findById(id).get();
    }

    @Override
    public void add(Role role) {
        roleRepository.save(role);
    }
}

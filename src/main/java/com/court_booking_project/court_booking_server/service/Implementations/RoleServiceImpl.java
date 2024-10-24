package com.court_booking_project.court_booking_server.service.Implementations;

import com.court_booking_project.court_booking_server.entity.Role;
import com.court_booking_project.court_booking_server.repository.IRoleRepository;
import com.court_booking_project.court_booking_server.service.Interfaces.IRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {

    private final IRoleRepository roleRepository;
    @Override
    public Role getByName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    public RoleServiceImpl(IRoleRepository roleRepository) {
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

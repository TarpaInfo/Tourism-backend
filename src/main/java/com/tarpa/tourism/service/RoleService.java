package com.tarpa.tourism.service;

import com.tarpa.tourism.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    Role saveRole(Role role);

    List<Role> getAllRoles();

    Optional<Role> getRoleById(Long id);

    Optional<Role> getRoleByRoleName(String roleName);

}
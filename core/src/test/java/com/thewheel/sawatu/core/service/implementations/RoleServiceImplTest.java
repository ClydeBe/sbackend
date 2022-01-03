package com.thewheel.sawatu.core.service.implementations;

import com.thewheel.sawatu.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleServiceImplTest {

    private RoleServiceImpl roleService = new RoleServiceImpl();

    @Test
    void findByName_shouldReturnCorrectRole() {
        assertEquals(Role.USER, roleService.findByName("USER"));
        assertEquals(Role.ADMIN, roleService.findByName("ADMIN"));
        assertEquals(Role.STAFF, roleService.findByName("STAFF"));
        assertEquals(Role.VENDOR, roleService.findByName("VENDOR"));
    }
}
package com.thewheel.sawatu.api.controller;

import com.thewheel.sawatu.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleControllerTest {

    private RoleController controller = new RoleController();

    @Test
    void getAll_shouldReturn() {
        // Given

        // When
        Role[] roles = Role.values();

        // Then
        assertArrayEquals(roles, controller.getAll());
    }
}
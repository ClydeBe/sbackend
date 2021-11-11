package com.thewheel.sawatu.core.service.interfaces;

import com.thewheel.sawatu.database.model.Role;

public interface RoleService {
    Role findByName(String name);
}

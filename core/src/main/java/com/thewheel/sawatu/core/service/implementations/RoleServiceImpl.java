package com.thewheel.sawatu.core.service.implementations;

import com.thewheel.sawatu.core.service.interfaces.RoleService;
import com.thewheel.sawatu.database.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.thewheel.sawatu.database.model.Role.*;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    @Override
    public Role findByName(String name) {
        if(name.equals("ADMIN")) return ADMIN;
        if(name.equals("STAFF")) return STAFF;
        if(name.equals("VENDOR")) return VENDOR;
        return USER;
    }
}

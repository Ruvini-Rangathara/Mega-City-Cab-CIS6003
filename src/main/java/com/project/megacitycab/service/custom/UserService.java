package com.project.megacitycab.service.custom;

import com.project.megacitycab.dto.UserDTO;
import com.project.megacitycab.service.CrudService;
import com.project.megacitycab.util.exception.MegaCityCabException;


public interface UserService extends CrudService<UserDTO> {
    UserDTO searchByEmail(Object... args) throws MegaCityCabException;
}

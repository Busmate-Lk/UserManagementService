package com.busmatelk.backend.service;

import com.busmatelk.backend.dto.TimekeeperDTO;

import java.util.UUID;

public interface TimekeeperService {

    void createtimekeeper(TimekeeperDTO signupDTO);

    TimekeeperDTO getTimekeeperById(UUID userId);
}

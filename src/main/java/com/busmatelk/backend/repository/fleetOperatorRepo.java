package com.busmatelk.backend.repository;

import com.busmatelk.backend.model.fleetOperatorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface fleetOperatorRepo extends JpaRepository<fleetOperatorModel,Integer>{


}

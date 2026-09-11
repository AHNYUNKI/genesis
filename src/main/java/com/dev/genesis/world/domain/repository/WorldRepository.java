package com.dev.genesis.world.domain.repository;

import com.dev.genesis.world.domain.World;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorldRepository extends JpaRepository<World, Long> {

    boolean existsByName(String name);

}

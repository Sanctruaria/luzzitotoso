package org.example.machadao.relic;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RelicRepository extends JpaRepository<Relic, Long> {

    List<Relic> findAllByOrderByIdAsc();

    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, Long id);
}

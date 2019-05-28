package com.kata.match.domain.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.kata.match.domain.model.MatchEntity;
import com.kata.match.domain.model.MatchStatusEnumEntity;

public interface MatchDao extends CrudRepository<MatchEntity, Long> {

    @Query("select case when count(m) > 0 then 'true' else 'false' end from MatchEntity m where m.id = ?1")
    Boolean existsByMatchId(Long matchId);

    @Query("select m.status from MatchEntity m where m.id = ?1")
    MatchStatusEnumEntity getMatchStatus(Long matchId);
}

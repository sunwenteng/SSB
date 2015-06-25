package com.bingo.persistence.login.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bingo.persistence.login.domain.PlayerInfo;



@Repository
public interface PlayerInfoMapper {
	
	List<PlayerInfo> getByNameAndServerId(Map<String, Object> paramMap);
	
}

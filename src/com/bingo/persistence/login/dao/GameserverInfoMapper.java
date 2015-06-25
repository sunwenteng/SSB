package com.bingo.persistence.login.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bingo.persistence.login.domain.GameserverInfo;



@Repository
public interface GameserverInfoMapper {
	
	List<GameserverInfo> getAll();
	
	void update( GameserverInfo instance );
	
}

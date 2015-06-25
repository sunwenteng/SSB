package com.bingo.persistence.tools.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bingo.persistence.tools.domain.GM;

@Repository
public interface GMMapper {
	void createTable();
	List<GM> getAll();
}

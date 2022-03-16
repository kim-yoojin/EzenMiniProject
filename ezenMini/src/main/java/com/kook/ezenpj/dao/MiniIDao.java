package com.kook.ezenpj.dao;

import com.kook.ezenpj.dto.JoinDto;

public interface MiniIDao {
	//======= join =======
	public String join(JoinDto dto);
	//======= login ======
	public JoinDto login(String bId);
}

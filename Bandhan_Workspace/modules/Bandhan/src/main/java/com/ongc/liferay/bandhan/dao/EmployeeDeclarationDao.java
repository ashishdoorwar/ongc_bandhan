package com.ongc.liferay.bandhan.dao;

import com.ongc.liferay.bandhan.util.EmployeeDeclarationModel;

import java.util.List;

public interface EmployeeDeclarationDao {

	public List<EmployeeDeclarationModel> getAll() ;
	public boolean insertEmployeeDecalarationModel(EmployeeDeclarationModel ed);
	public boolean isExist(String yr,String cpfno);
}

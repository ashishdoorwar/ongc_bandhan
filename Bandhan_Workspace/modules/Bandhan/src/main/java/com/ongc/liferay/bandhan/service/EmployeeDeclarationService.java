package com.ongc.liferay.bandhan.service;

import com.ongc.liferay.bandhan.util.EmployeeDeclarationModel;

import java.util.List;

public interface EmployeeDeclarationService {
	public List<EmployeeDeclarationModel> getAll() ;
	public boolean insertEmployeeDecalarationModel(EmployeeDeclarationModel ed);
	public boolean isExist(String yr,String cpfno);

}

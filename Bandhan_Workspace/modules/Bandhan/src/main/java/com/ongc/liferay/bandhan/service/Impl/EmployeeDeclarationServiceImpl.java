package com.ongc.liferay.bandhan.service.Impl;

import com.ongc.liferay.bandhan.dao.EmployeeDeclarationDao;
import com.ongc.liferay.bandhan.dao.Impl.EmployeeDeclarationDaoImpl;
import com.ongc.liferay.bandhan.util.EmployeeDeclarationModel;
import com.ongc.liferay.bandhan.service.EmployeeDeclarationService;

import java.util.List;

public class EmployeeDeclarationServiceImpl implements EmployeeDeclarationService{

	private EmployeeDeclarationDao employeeDeclarationDao=new EmployeeDeclarationDaoImpl();  
	@Override
	public List<EmployeeDeclarationModel> getAll() {
		// TODO Auto-generated method stub
		return employeeDeclarationDao.getAll();
	}

	@Override
	public boolean insertEmployeeDecalarationModel(EmployeeDeclarationModel ed) {
		// TODO Auto-generated method stub
		return employeeDeclarationDao.insertEmployeeDecalarationModel(ed);
	}

	@Override
	public boolean isExist(String yr, String cpfno) {
		// TODO Auto-generated method stub
		return employeeDeclarationDao.isExist(yr, cpfno);
	}

}

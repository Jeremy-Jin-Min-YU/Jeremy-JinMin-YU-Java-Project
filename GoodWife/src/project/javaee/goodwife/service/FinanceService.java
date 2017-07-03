package project.javaee.goodwife.service;

import java.util.List;

import project.javaee.goodwife.dao.FinanceDao;
import project.javaee.goodwife.domain.Finance;
/**
 * service layer: receive controller layer data, get Operation result pass to dao layer.
 * call dao layer`s class create dao class`s object.
 */




public class FinanceService {
	
	private FinanceDao dao=new FinanceDao();
public void  editFinance(Finance finance) {
		dao.editFinance(finance);
		
	}

	public void addFinance(Finance finance) {
		dao.addFinance(finance);
	} 
	
	/*
	 * 
	 */
	public List<Finance> select(String startDate,String endDate) {
		return dao.select(startDate, endDate);
		
		
	}
	
	
	/*
	 * 
	 */

	public List<Finance> selectAll(){
	
		return dao.selectAll();
	}

	public void deletFinance(int fid) {
		
		dao.deletFinance(fid);
	}
}

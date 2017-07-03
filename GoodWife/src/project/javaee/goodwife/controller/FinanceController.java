package project.javaee.goodwife.controller;
/*
 * 
 * 
 */


import java.util.List;

import project.javaee.goodwife.domain.Finance;
import project.javaee.goodwife.service.FinanceService;

public class FinanceController {
	private FinanceService service = new FinanceService();

	public void deletFinance(int fid) {
		service.deletFinance(fid);
	}

	public void editFinance(Finance finance) {

		service.editFinance(finance);
	}

	public void addFinance(Finance finance) {
		service.addFinance(finance);
	}

	/*
	 * 
	 */
	public List<Finance> select(String startDate, String endDate) {
		return service.select(startDate, endDate);
	}

	/*
	 * call from view layer
	 */
	public List<Finance> selectAll() {

		return service.selectAll();
	}

}

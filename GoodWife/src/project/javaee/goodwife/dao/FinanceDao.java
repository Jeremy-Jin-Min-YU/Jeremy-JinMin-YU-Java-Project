package project.javaee.goodwife.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
/**
 * use for goodwife table Add data, delete data, change data 
 * dbutils tools use queryRunner
 * 
 *
 */
import org.apache.commons.dbutils.handlers.BeanListHandler;

import project.javaee.goodwife.domain.Finance;
import project.javaee.goodwife.tools.JDBCUtils;

public class FinanceDao {

	private QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());

	public void deletFinance(int fid) {

		String sql="delete from goodwife_finance where fid=?";
		try {
			qr.update(sql,fid);
			System.out.println("data delet !!!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("delet data fail");
		}
		
	}

	public void editFinance(Finance finance) {

		String sql = "update goodwife_finance set fname=?,money=?,financeAcc=?,createtime=?,description=? where fid=?";
		Object[] params = { finance.getFname(), finance.getMoney(), finance.getFinanceAcc(), finance.getCreatetime(),
				finance.getDescription(), finance.getFid() };
		try {
			qr.update(sql, params);
			System.out.println("data edited");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("edit the data fail");
		}

	}

	public void addFinance(Finance finance) {
		String sql = "insert into goodwife_finance(fname,money,financeAcc,createtime,description)"
				+ "values(?,?,?,?,?)";
		Object[] params = { finance.getFname(), finance.getMoney(), finance.getFinanceAcc(), finance.getCreatetime(),
				finance.getDescription() };
		try {
			qr.update(sql, params);
			System.out.println("data saving");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
			throw new RuntimeException("add the data fail");
		}

	}

	/*
	 * 
	 */
	public List<Finance> select(String startDate, String endDate) {
		// sql
		String sql = "SELECT * FROM goodwife_finance WHERE createtime BETWEEN ? AND ?";
		Object[] params = { startDate, endDate };
		try {
			List<Finance> list = qr.query(sql, new BeanListHandler<>(Finance.class), params);
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("select query fail");
		}

	}

	/*
	 * 
	 */
	public List<Finance> selectAll() {
		// query sql
		String sql = "SELECT * FROM goodwife_finance";
		// CALL QR object method beanlisthandler
		List<Finance> list;
		try {
			list = qr.query(sql, new BeanListHandler<>(Finance.class));
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("query all finance fail");

		}

	}

}

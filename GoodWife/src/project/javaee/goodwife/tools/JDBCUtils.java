package project.javaee.goodwife.tools;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * 
 * 
 * @author Jeremy
 *
 *	get database tools use dbcp pool 
 */
public class JDBCUtils {
	
	private static BasicDataSource dataSource=new BasicDataSource();
	
	static{
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/goodwife");
		dataSource.setUsername("root");
		dataSource.setPassword("admin");
		dataSource.setMaxActive(10);
		dataSource.setMaxIdle(5);
		dataSource.setMinIdle(2);
		dataSource.setInitialSize(10);
	}
	
	public static DataSource getDataSource() {
		return dataSource;
		
	}
	
	
	
	
	
	
	
}

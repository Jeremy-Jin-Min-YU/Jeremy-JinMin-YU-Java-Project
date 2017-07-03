package project.javaee.goodwife.domain;

public class Finance {
	 private int fid;  
	 private String fname;
	 private double money; 
	 private String financeAcc ;
	 private String createtime; 
	 private String description;
	public Finance(int fid, String fname, double money, String financeAcc, String createtime, String description) {
		super();
		this.fid = fid;
		this.fname = fname;
		this.money = money;
		this.financeAcc = financeAcc;
		this.createtime = createtime;
		this.description = description;
	}
	public Finance() {
		
		
	}
	@Override
	public String toString() {
		return "Finance [fid=" + fid + ", fname=" + fname + ", money=" + money + ", financeAcc=" + financeAcc
				+ ", createtime=" + createtime + ", description=" + description + "]";
	}
	public int getFid() {
		return fid;
	}
	public void setFid(int fid) {
		this.fid = fid;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public String getFinanceAcc() {
		return financeAcc;
	}
	public void setFinanceAcc(String financeAcc) {
		this.financeAcc = financeAcc;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	 

}

package project.javaee.goodwife.view;

import java.util.List;
import java.util.Scanner;

import project.javaee.goodwife.controller.FinanceController;
import project.javaee.goodwife.domain.Finance;

public class MainView {

	private FinanceController controller = new FinanceController();

	/*
	 * 
	 * 	
	 */
	public void run() {

		// create scanner object for repeat use
		Scanner scanner = new Scanner(System.in);
		boolean flag = true;
		while (flag) {
			System.out.println("---------------GoodWife finance---------------");
			System.out.println("1.Add finance　2.edit finance　3.delet finance　4.query finance　5.exit");
			System.out.println("Please enter number[1-5]:");
			int choose = scanner.nextInt();
			switch (choose) {
			case 1:
				addFinance();
				break;
			case 2:
				editFinance();
				break;
			case 3:
				deletFinance();
				break;
			case 4:
				selectFinance();
				break;
			case 5:
				System.out.println("Thanks for use goodwife finance software-----goodbye");
				flag = false;
				break;
			default:
				System.out.println("enter wrong");

			}
		}

	}

	private void deletFinance() {
		selectAll();

		System.out.println("please enter number of row you want delet");
		int fid = new Scanner(System.in).nextInt();
		controller.deletFinance(fid);

	}

	public void editFinance() {
		selectAll();
		System.out.println("you choose edit data,Please fill in the following ");
		Scanner scanner = new Scanner(System.in);
		System.out.println("please enter number of row you want edit");
		int fid = scanner.nextInt();
		System.out.println("please enter categoty");
		String fname = scanner.next();
		System.out.println("please enter Amount of money");
		double money = scanner.nextDouble();
		System.out.println("please enter finance type");
		String financeAcc = scanner.next();
		System.out.println("please enter the date: xxxx-xx-xx");
		String createtime = scanner.next();
		System.out.println("please enter description");
		String descrption = scanner.next();
		Finance finance = new Finance(fid, fname, money, financeAcc, createtime, descrption);
		controller.editFinance(finance);

	}

	public void addFinance() {
		System.out.println("Please fill in the following ");
		Scanner scanner = new Scanner(System.in);
		System.out.println("please enter categoty");
		String fname = scanner.next();
		System.out.println("please enter Amount of money");
		double money = scanner.nextDouble();
		System.out.println("please enter finance type");
		String financeAcc = scanner.next();
		System.out.println("please enter the date: xxxx-xx-xx");
		String createtime = scanner.next();
		System.out.println("please enter description");
		String descrption = scanner.next();
		Finance finance = new Finance(0, fname, money, financeAcc, createtime, descrption);
		controller.addFinance(finance);
	}

	public void selectFinance() {
		System.out.println("1. query all  2.select query");
		Scanner scanner = new Scanner(System.in);
		int selectChoose = scanner.nextInt();
		switch (selectChoose) {
		case 1:
			selectAll();
			break;

		case 2:
			select();
			break;
		default:
			System.out.println("enter wrong");
		}

	}

	public void selectAll() {
		// call controller layer method to query all data.
		List<Finance> list = controller.selectAll();
		if (list.size() != 0) {
			display(list);
		} else {
			System.out.println("No thing found");
		}
	}

	/*
	 * use date query finance enter start date and end date pass date to
	 * controller layer
	 */
	public void select() {

		System.out.println("select query from date, enter format:yyyy-mm-dd");
		Scanner scanner = new Scanner(System.in);
		System.out.println("enter start date:");
		String startDate = scanner.nextLine();
		System.out.println("enter end date:");
		String endDate = scanner.nextLine();
		List<Finance> list = controller.select(startDate, endDate);
		if (list.size() != 0) {
			display(list);
		} else {
			System.out.println("Nothing found");
		}

	}

	private void display(List<Finance> list) {
		System.out.println("ID\t\tCategory\t\t\tFinanceAcc\t\t\tMoney\t\t\tDate\t\t\tDescription");
		for (Finance f : list) {
			System.out.println(f.getFid() + "\t\t" + f.getFname() + "\t\t\t" + f.getFinanceAcc() + "\t\t\t"
					+ f.getMoney() + "\t\t\t" + f.getCreatetime() + "\t\t\t" + f.getDescription());
		}
	}
}

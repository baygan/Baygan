package tests.simple;

import erros.exeptions.ComponentChangeException;
import baseBaygan.managementSystems.BManagementSystem;

public class Main {
	public static void main(String[] args) {
		try {
			BManagementSystem bms = new BManagementSystem("asdsa.xml");
			bms.setAccessLayer(accessLayer)
			Thread t=new Thread(bms);
			t.start();
		} catch (ComponentChangeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

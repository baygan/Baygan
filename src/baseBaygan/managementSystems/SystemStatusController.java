package baseBaygan.managementSystems;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SystemStatusController {
	/**
	 * It keeps an instance of ManagementSystem in order to control its status
	 * and check its conditions any time it needs too.
	 */
	private BManagementSystem mSystem;

	/**
	 * Prints System status containing ManagementSystem status and all
	 * components.
	 * 
	 * @return
	 */
	public String status() {
		String status = "Baygan System Status		@("
				+ new SimpleDateFormat().format(Calendar.getInstance()
						.getTime()) + ") ############### \n\r";
		// ------------
		status += checkManagementSystemStatus() + " \n\r";
		// ------------
		status += checkComponentsStatus() + " \n\r";
		// ------------
		status += "End of Baygan Status ###############";
		return status;
	}

	private String checkManagementSystemStatus() {
		return mSystem.getStatusDescription();
	}

	public String checkComponentsStatus() {

		String all = "Comoponents' Status: \n\r";
		all += "\t" + mSystem.getBackupManager().getStatusDescription()
				+ " \n\r";
		all += "\t" + mSystem.getBufferManager().getStatusDescription()
				+ " \n\r";
		all += "\t" + mSystem.getAccessLayer().getStatusDescription() + " \n\r";
		all += "\t" + mSystem.getTableManager().getStatusDescription()
				+ " \n\r";
		all += "\t" + mSystem.getExecutor().getStatusDescription() + " \n\r";
		all += "\t" + mSystem.getLockManager().getStatusDescription() + " \n\r";
		all += "\t" + mSystem.getOptimizator().getStatusDescription() + " \n\r";
		all += "\t" + mSystem.getParser().getStatusDescription() + " \n\r";
		all += "\t" + mSystem.getSecurityManager().getStatusDescription()
				+ " \n\r";
		all += "\t" + mSystem.getSotrageManager().getStatusDescription()
				+ " \n\r";

		return all;
	}

}

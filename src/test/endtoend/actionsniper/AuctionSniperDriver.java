package test.endtoend.actionsniper;

import actionsniper.Main;

import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JLabelDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;

import static org.hamcrest.CoreMatchers.equalTo;

public class AuctionSniperDriver extends JFrameDriver {

	  public AuctionSniperDriver(int timeoutMillis) { 
		    super(new GesturePerformer(), 
		          JFrameDriver.topLevelFrame( 
		            named(Main.MAIN_WINDOW_NAME), 
		            showingOnScreen()), 
		            new AWTEventQueueProber(timeoutMillis, 100)); 
		  }

	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	  public void showsSniperStatus(String statusText) {
	    new JLabelDriver(this, 
	    		named(Main.SNIPER_STATUS_NAME)).hasText(equalTo(statusText));
	  }

}

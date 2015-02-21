package test.endtoend.actionsniper;

import actionsniper.Main;
import actionsniper.Main.MainWindow;

public class ApplicationRunner {

	public static final String SNIPER_ID = "sniper"; 
  	public static final String SNIPER_PASSWORD = "sniper";
	protected static final String XMPP_HOSTNAME = "localhost";
	private static final String STATUS_JOINING = "Joining";
	private static final String STATUS_LOST = "Lost";
	
	public static final String SNIPER_XMPP_ID = SNIPER_ID + "@" + "daumyangui-macbook-pro.local" + "/Auction";
	
  	private AuctionSniperDriver driver; 

	public void startBiddingIn(final FakeAuctionServer auction) {
		Thread thread = new Thread("Test Application") {
			@Override 
			public void run(){
				try{
					Main.main(XMPP_HOSTNAME, SNIPER_ID, SNIPER_PASSWORD, auction.getItemId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		thread.setDaemon(true);
		thread.start();
		driver = new AuctionSniperDriver(1000);
		
		driver.showsSniperStatus(STATUS_JOINING);
	}
	
	public void hasShownSniperIsBidding() {
		driver.showsSniperStatus(MainWindow.STATUS_LOST);
		
	}

	public void showsSniperHasLostAuction() {
		driver.showsSniperStatus(MainWindow.STATUS_LOST);
		
	}

	public void stop() {
		if (driver != null) { 
	      driver.dispose();  
	    } 
	}

}

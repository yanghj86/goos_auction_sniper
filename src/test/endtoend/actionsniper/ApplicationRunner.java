package test.endtoend.actionsniper;

import auctionsniper.Main;
import auctionsniper.Main.MainWindow;

public class ApplicationRunner {

	public static final String SNIPER_ID = "sniper"; 
  	public static final String SNIPER_PASSWORD = "sniper";
	protected static final String XMPP_HOSTNAME = "daumyangui-macbook-pro.local";
	private static final String STATUS_JOINING = "Joining";
	public static final String SNIPER_XMPP_ID = SNIPER_ID + "@" +XMPP_HOSTNAME + "/Auction";


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
		driver.showsSniperStatus(MainWindow.STATUS_BIDDING);
		
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

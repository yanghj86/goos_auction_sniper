package auctionsniper;

import auctionsniper.SniperListener;

public class AuctionSniper implements AuctionEventListener {
	private final SniperListener sniperListener;
	
	public AuctionSniper(SniperListener sniperListener) {
	    this.sniperListener = sniperListener;
	  }

	@Override
	public void auctionClosed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void currentPrice(int price, int increment) {
		// TODO Auto-generated method stub
		
	}
	

}

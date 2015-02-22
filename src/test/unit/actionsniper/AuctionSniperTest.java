package test.unit.actionsniper;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.Expectations;
import org.junit.Test;
import org.junit.runner.RunWith;

import auctionsniper.Auction;
import auctionsniper.AuctionSniper;
import auctionsniper.SniperListener;

@RunWith(JMock.class)
public class AuctionSniperTest {
	private final Mockery context = new Mockery(); 
	private final SniperListener sniperListener = context.mock(SniperListener.class);
	private final Auction auction = context.mock(Auction.class);
	private final AuctionSniper sniper = new AuctionSniper(auction, sniperListener); 
	
	@Test public void 
	  bidsHigherAndReportsBiddingWhenNewPriceArrives() { 
	    final int price = 1001; 
	    final int increment = 25; 
	    final int bid = price + increment;
	    context.checking(new Expectations() {{ 
	    one(auction).bid(bid);   
	    atLeast(1).of(sniperListener).sniperBidding(); 
	    }}); 
	    
	    sniper.currentPrice(price, increment, null); 
	  } 
	
	@Test public void 
	  reportsLostWhenAuctionClosesImmediately() { 
	    context.checking(new Expectations() {{ 
	      one(sniperListener).sniperLost();  
	    }}); 
	    
	    sniper.auctionClosed(); 
	}

}

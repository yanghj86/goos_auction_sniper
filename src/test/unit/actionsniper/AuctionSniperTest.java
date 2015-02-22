package test.unit.actionsniper;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.Expectations;
import org.junit.Test;
import org.junit.runner.RunWith;

import auctionsniper.AuctionSniper;
import auctionsniper.SniperListener;

@RunWith(JMock.class)
public class AuctionSniperTest {
	private final Mockery context = new Mockery(); 
	private final SniperListener sniperListener = context.mock(SniperListener.class);
	private final AuctionSniper sniper = new AuctionSniper(sniperListener); 
	
	@Test public void 
	  reportsLostWhenAuctionClosesImmediately() { 
	    context.checking(new Expectations() {{ 
	      one(sniperListener).sniperLost();  
	    }}); 
	    
	    sniper.auctionClosed(); 
	}

}

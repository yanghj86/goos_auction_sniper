package auctionsniper;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class Main {

	private static final int ARG_HOSTNAME = 0;
	private static final int ARG_USERNAME = 1;
	private static final int ARG_PASSWORD = 2;

	private static final int ARG_ITEM_ID = 3;

	public static final String AUCTION_RESOURCE = "Auction";
	public static final String ITEM_ID_AS_LOGIN = "auction-%s";

	public static final String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%s/"
			+ AUCTION_RESOURCE;

	public static final String MAIN_WINDOW_NAME = "Auction Sniper Main";
	public static final String SNIPER_STATUS_NAME = "sniper status";


	private MainWindow ui;

	@SuppressWarnings("unused")
	private Chat notToBeGCd;

	public Main() throws Exception {
		startUserInterface();
	}

	public static void main(String... args) throws Exception {
		Main main = new Main();

		main.joinAuction(
				connection(args[ARG_HOSTNAME], args[ARG_USERNAME],
						args[ARG_PASSWORD]), args[ARG_ITEM_ID]);
	}

	private void joinAuction(XMPPConnection connection, String itemId)
			throws XMPPException {
		
		disconnectWhenUICloses(connection);
		final Chat chat = connection.getChatManager().createChat(
				auctionId(itemId, connection), 
				null);
		
		this.notToBeGCd = chat;
		Auction auction = new XMPPAuction(chat);
		chat.addMessageListener(new AuctionMessageTranslator(
				connection.getUser(),
				new AuctionSniper(auction, new SniperStateDisplayer())));
		auction.join();
		
	}
	
	public class SniperStateDisplayer implements SniperListener{
		
		@Override
		public void sniperLost() {
			showStatus(MainWindow.STATUS_LOST);
		}
		
		@Override
		public void sniperBidding() {
			showStatus(MainWindow.STATUS_BIDDING);
		}
		
		@Override
		public void sniperWinning() {
			showStatus(MainWindow.STATUS_WINNING);
		}
		
		@Override
		public void sniperWon() {
			showStatus(MainWindow.STATUS_WON);
		}
		
		private void showStatus(final String status){
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					ui.showStatus(status);
				}
			});
		}

	}
	
	private void disconnectWhenUICloses(final XMPPConnection connection) { 
	    ui.addWindowListener(new WindowAdapter() { 
	      @Override public void windowClosed(WindowEvent e) { 
	    	  connection.disconnect(); 
	      } 
	    }); 
	  } 

	private static String auctionId(String itemId, XMPPConnection connection) {
		return String.format(AUCTION_ID_FORMAT, itemId,
				connection.getServiceName());
	}

	private static XMPPConnection connection(String hostname, String username,
			String password) throws XMPPException {
		XMPPConnection connection = new XMPPConnection(hostname);
		connection.connect();
		connection.login(username, password, AUCTION_RESOURCE);

		return connection;
	}

	private void startUserInterface() throws Exception {
		SwingUtilities.invokeAndWait(new Runnable() {
			public void run() {
				ui = new MainWindow();
			}
		});
	}

	public class MainWindow extends JFrame {

		private static final long serialVersionUID = -4274409492013695538L;

		public static final String SNIPER_STATUS_NAME = "sniper status";

		private static final String STATUS_JOINING = "Joining";
		public static final String STATUS_BIDDING = "Bidding";
		public static final String STATUS_LOST = "Lost";
		public static final String STATUS_WINNING = "winning";
		public static final String STATUS_WON = "Won";
		
		private final JLabel sniperStatus = createLabel(STATUS_JOINING);

		public MainWindow() {
			super("Auction Sniper");
			setName(MAIN_WINDOW_NAME);
			add(sniperStatus);
			pack();
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setVisible(true);
		}

		public void showStatus(String status) {
			sniperStatus.setText(status);
		}

		private JLabel createLabel(String initialText) {
			JLabel result = new JLabel(initialText);
			result.setName(SNIPER_STATUS_NAME);
			result.setBorder(new LineBorder(Color.BLACK));
			return result;
		}

	}

}

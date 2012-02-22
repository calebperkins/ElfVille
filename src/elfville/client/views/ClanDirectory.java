package elfville.client.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ClanDirectory extends JPanel {
	
	private class ClickHandler implements ActionListener {
		private String name;
		
		public ClickHandler(String name) {
			this.name = name;
		}

		/**
		 * Click on a clan and get directed to its clan page.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public ClanDirectory() {
		super();
		add(new JLabel("Clan Directory"));
	}

}

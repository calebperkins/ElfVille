package elfville.client.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import elfville.client.ClientWindow;
import elfville.client.SocketController;
import elfville.client.views.subcomponents.LoginPanel;
import elfville.client.views.subcomponents.RegistrationPanel;
import elfville.protocol.LoadClassRequest;
import elfville.protocol.Response;

public class LoadClassScreen extends Board {
	private static final long serialVersionUID = 1L;

	private JTextField uploadFilePathField = new JTextField();
	private JButton uploadButton;
	/**
	 * Creates a panel with login and registration options.
	 */
	public LoadClassScreen(ClientWindow clientWindow,
			SocketController socketController) {
		super(clientWindow, socketController);
		handleRequestSuccess(null);
	}

	private class ButtonHandler implements ActionListener {
		LoadClassScreen lscreen;
		public ButtonHandler(LoadClassScreen lscreen) {
			this.lscreen = lscreen;
		}
		@Override
		public void actionPerformed(ActionEvent e) {

			LoadClassRequest loadReq = new LoadClassRequest();
			// loadReq.filepath = "bin/userLoadClasses/MyClass.class";
			loadReq.filepath = uploadFilePathField.getText();
			System.out.println(loadReq.filepath);
			String[] paths = loadReq.filepath.split("/");
			loadReq.fileName = "userLoadClasses." + paths[paths.length-1].replace(".class", "");

			getSocketController().sendRequest(loadReq, lscreen,
					"Error retrieving central board.", lscreen);

		}
	}
	
	@Override
	public void handleRequestSuccess(Response resp) {

		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

		uploadButton = new JButton("Click to upload Java Class");
		uploadButton.addActionListener(new ButtonHandler(this));

		add(uploadButton);
		add(uploadFilePathField);
		

		clientWindow.switchScreen(this);
	}

	@Override
	public void refresh() {
		new LoadClassScreen(clientWindow, socketController);
	}

}

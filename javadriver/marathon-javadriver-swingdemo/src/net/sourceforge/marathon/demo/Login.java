package net.sourceforge.marathon.demo;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.jgoodies.forms.builder.ButtonBarBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class Login extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField nameField = new JTextField();
	private JPasswordField passwordField = new JPasswordField();
	private JButton cancelButton = new JButton("Cancel");
	private JButton loginButton = new JButton("Login");
	private JCheckBox rememberMe = new JCheckBox("Remember me");

	public Login() {
		super("Login");
		initComponents();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void initComponents() {
		nameField.setName("userName");
		nameField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				updateLoginState();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				updateLoginState();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				updateLoginState();
			}
		});
		passwordField.setName("password");
		passwordField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				updateLoginState();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				updateLoginState();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				updateLoginState();
			}
		});
		PanelBuilder builder = new PanelBuilder(
				new FormLayout("left:pref, 3dlu, pref:g", "pref, 3dlu, pref, 3dlu, pref, 3dlu, pref, 3dlu, pref"));
		builder.border(Borders.DIALOG);
		CellConstraints labelConstraints = new CellConstraints();
		CellConstraints compConstraints = new CellConstraints();
		builder.addLabel("&Name: ", labelConstraints.xy(1, 1), nameField, compConstraints.xywh(3, 1, 1, 1));
		builder.addLabel("Password: ", labelConstraints.xy(1, 3), passwordField, compConstraints.xy(3, 3));
		rememberMe.setName("rememberMe");
		builder.add(rememberMe, compConstraints.xy(3, 5));
		JPanel buttonBar = ButtonBarBuilder.create().addGlue().addButton(cancelButton, loginButton).getPanel();
		builder.add(buttonBar, compConstraints.xyw(1, 7, 3));
		JPanel panel = builder.getPanel();
		Container contentPane = getContentPane();
		contentPane.add(panel);
		loginButton.setName("login");
		loginButton.setEnabled(false);
		cancelButton.setName("cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(Login.this,
						"You logged in as: " + nameField.getText() + " with password: " + passwordField.getText(),
						"Login Success", JOptionPane.INFORMATION_MESSAGE);
				dispose();
			}
		});
		pack();
	}

	private void updateLoginState() {
		loginButton.setEnabled(nameField.getText().length() != 0 && passwordField.getPassword().length != 0);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Login login = new Login();
				login.setVisible(true);
			}
		});
	}
}

package com.example;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField textField;
	static private JTextPane label;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */

	public Main() {
		setAlwaysOnTop(true);
		ImageIcon img = new ImageIcon(getClass().getResource("/Contacts1.png"));
		setIconImage(img.getImage());

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();
		setBounds(width - 700, height - 200, 700, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// if (textField.getText().length() == 0) {
				// textField.setText(LABLE_TEXT);
				// }
				if (textField.getText().length() > 2) {

					new Thread(new Runnable() {
						@Override
						public void run() {
							String data = textField.getText();
							if (data.contains(" ")) {
								data = data.replaceAll(" ", "%20");
							}
							String responceText = excutePost("http://172.26.1.39:8080/ContactBook/search?term="
									+ data);
							JSONArray arr;
							try {
								// String header[] = new String[] { "EmpID",
								// "Name",
								// "Mobile No", "Spedd Dial No", "Email ",
								// "Dept" };
								String dataToFetchForJson[] = { "empId",
										"name", "mobileNo", "speedDialNo",
										"email", "dept" };
								arr = new JSONArray(responceText);
								String datashow = "<html><body><table> <tr><td>EmpID</td><td>Name</td><td>Mobile No</td><td>Star No</td><td>Email</td><td>Dept</td></tr>";
								String dataToDisplay = "";
								if (arr.length() == 0) {
									label.setText("<html><div style='color:RED;'>NO SUCH RECORD EXIST<div></html>");
								} else {
									for (int i = 0; i < arr.length(); i++) {
										JSONObject jsonObject = arr
												.getJSONObject(i);
										datashow += "<tr>";
										for (int j = 0; j < dataToFetchForJson.length; j++) {
											try {
												dataToDisplay = jsonObject
														.getString(dataToFetchForJson[j]);
												datashow += "<td>"
														+ dataToDisplay
														+ "</td>";
											} catch (JSONException ee) {
												datashow += "<td>" + ""
														+ "</td>";
											}
										}
										datashow += "</tr>";
									}
									datashow += "</table></body></html>";
									label.setText(datashow);
								}
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					}).start();

					// TODO give option for insert IP
					// String responceText =
					// excutePost("http://172.26.1.23:8080/ContactBook/search?term="
					// + textField.getText());

				}
			}

			@Override
			public void keyPressed(KeyEvent e) {

			}
		});
		contentPane.add(textField, BorderLayout.NORTH);
		textField.setColumns(10);
		label = new JTextPane();
		label.setContentType("text/html");
		contentPane.add(label, BorderLayout.CENTER);
	}

	public static String excutePost(String targetURL) {
		URL url;
		HttpURLConnection connection = null;
		try {
			// Create connection
			url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Language", "en-US");

			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(
					connection.getOutputStream());
			wr.flush();
			wr.close();

			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			return response.toString();
		} catch (ConnectException e) {
			label.setText("<html><div style='color:RED;'>SERVER NOT FOUND<div></html>");
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			label.setText("<html><div style='color:RED;'>ERROR OCCURED<div></html>");
			e.printStackTrace();
			return null;
		} finally {

			if (connection != null) {
				connection.disconnect();
			}
		}
	}
}

package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import soft.MAC_samples;

public class InsertHypoAPDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField MacAddtf;

	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		try {
			InsertAPDialog dialog = new InsertAPDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the dialog.
	 */
	int x;
	int y;
	
	private JTextField YTextF;
	private JTextField XTextF;
	public InsertHypoAPDialog(final PaintPane p) {
		setBounds(100, 100, 379, 218);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblApId = new JLabel("AP ID :");
			lblApId.setBounds(10, 11, 105, 35);
			lblApId.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblApId);
		}
		
		{
			MacAddtf = new JTextField();
			MacAddtf.setEditable(false);
			MacAddtf.setBounds(125, 18, 157, 21);
			MacAddtf.setText(Integer.toString(PaintPane.Mac.size() + 1));
			contentPanel.add(MacAddtf);
			MacAddtf.setColumns(10);
		}
		
			final JLabel lblx = new JLabel("X :");
			lblx.setBounds(125, 57, 18, 35);
			lblx.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblx);
		
		{
			JButton btnSetPosition = new JButton("Set Position");
			btnSetPosition.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					setVisible(false);
					MainWindow.panelCanvas.apPosition();
				}
			});
			{
				JLabel lblY = new JLabel("Y :");
				lblY.setHorizontalAlignment(SwingConstants.CENTER);
				lblY.setBounds(187, 57, 18, 35);
				contentPanel.add(lblY);
			}
			btnSetPosition.setBounds(256, 64, 26, 21);
			contentPanel.add(btnSetPosition);
		}
		
		{
			JButton btnAdd = new JButton("Add");
			btnAdd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					addAP(true);
					p.repaint();
				}
			});
			btnAdd.setBounds(193, 109, 89, 23);
			contentPanel.add(btnAdd);
		}
		{
			YTextF = new JTextField("0");
			YTextF.setBounds(204, 64, 37, 20);
			contentPanel.add(YTextF);
			YTextF.setColumns(10);
		}
		{
			XTextF = new JTextField("0");
			XTextF.setColumns(10);
			XTextF.setBounds(140, 64, 37, 20);
			contentPanel.add(XTextF);
		}
		{
			JLabel lblLocation = new JLabel("Location (pixel)");
			lblLocation.setHorizontalAlignment(SwingConstants.CENTER);
			lblLocation.setBounds(10, 57, 105, 35);
			contentPanel.add(lblLocation);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Done");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						p.smoothing_hypothetical();
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
	
	public void addAP(boolean isAuth)
	{
		String hex = Integer.toHexString(Integer.parseInt(MacAddtf.getText()));
		StringBuilder well = new StringBuilder("00-00-00-00-00-00");
		for (int i = hex.length()-1, j = 16 ; i >= 0; i--, j--)
		{
			if (well.charAt(j)=='-')
				j--;
			well.setCharAt(j, hex.charAt(i));
		}
		//System.out.println("HEX: " + hex);
		String macAdd = well.toString();
		PaintPane.Mac.add(new MAC_samples(macAdd, Integer.parseInt(XTextF.getText()), Integer.parseInt(YTextF.getText())));
		MainWindow.updateAPList();
		reset();
	}
	
	public void setXY(int newx, int newy)
	{
		x = newx ;
		y = newy ;
		
		XTextF.setText(Integer.toString(x));
		YTextF.setText(Integer.toString(y));
	}
	
	public void reset()
	{
		MacAddtf.setText(Integer.toString(PaintPane.Mac.size()+1));
		XTextF.setText("0");
		YTextF.setText("0");
	}
}

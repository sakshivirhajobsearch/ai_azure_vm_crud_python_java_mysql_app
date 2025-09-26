package com.ai.azure.vm;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.ai.azure.vm.model.VMModel;
import com.ai.azure.vm.repository.VMRepository;

public class UnifiedGUI {
	
	public static void main(String[] args) throws Exception {
		
		JFrame frame = new JFrame("Azure VM Dashboard with AI");
		frame.setSize(900, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		List<VMModel> vms = VMRepository.getAllVMs();
		String[] columns = { "ID", "Name", "Location", "VM Size", "Resource Group", "Classification" };
		String[][] data = new String[vms.size()][6];

		for (int i = 0; i < vms.size(); i++) {
			VMModel vm = vms.get(i);
			data[i][0] = String.valueOf(vm.getId());
			data[i][1] = vm.getName();
			data[i][2] = vm.getLocation();
			data[i][3] = vm.getVmSize();
			data[i][4] = vm.getResourceGroup();
			data[i][5] = vm.getClassification();
		}

		JTable table = new JTable(data, columns);
		JScrollPane scrollPane = new JScrollPane(table);

		JButton aiButton = new JButton("Run AI Analysis");
		aiButton.addActionListener(e -> {
			try {
				ProcessBuilder pb = new ProcessBuilder("python", "backend-python/insert_vm_data.py");
				pb.inheritIO();
				pb.start();
				JOptionPane.showMessageDialog(frame, "AI Analysis triggered. Please restart app to see changes.");
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(frame, "Error running AI analysis.");
			}
		});

		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.add(aiButton);

		frame.add(scrollPane, BorderLayout.CENTER);
		frame.add(panel, BorderLayout.SOUTH);
		frame.setVisible(true);
	}
}

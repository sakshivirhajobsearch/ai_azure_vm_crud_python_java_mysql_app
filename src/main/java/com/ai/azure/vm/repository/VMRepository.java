package com.ai.azure.vm.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ai.azure.vm.connection.DBConnection;
import com.ai.azure.vm.model.VMModel;

public class VMRepository {

	public static List<VMModel> getAllVMs() throws Exception {
		List<VMModel> list = new ArrayList<>();
		Connection con = DBConnection.getConnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM azure_vms");

		while (rs.next()) {
			VMModel vm = new VMModel();
			vm.setId(rs.getInt("id"));
			vm.setName(rs.getString("name"));
			vm.setLocation(rs.getString("location"));
			vm.setVmSize(rs.getString("vm_size"));
			vm.setResourceGroup(rs.getString("resource_group"));
			vm.setClassification(rs.getString("classification"));
			list.add(vm);
		}

		rs.close();
		stmt.close();
		con.close();

		return list;
	}

	public static void addVM(VMModel vm) throws Exception {
		Connection con = DBConnection.getConnection();
		String sql = "INSERT INTO azure_vms (name, location, vm_size, resource_group, classification) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, vm.getName());
		ps.setString(2, vm.getLocation());
		ps.setString(3, vm.getVmSize());
		ps.setString(4, vm.getResourceGroup());
		ps.setString(5, vm.getClassification());
		ps.executeUpdate();
		ps.close();
		con.close();
	}

	public static void updateVM(VMModel vm) throws Exception {
		Connection con = DBConnection.getConnection();
		String sql = "UPDATE azure_vms SET name=?, location=?, vm_size=?, resource_group=?, classification=? WHERE id=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, vm.getName());
		ps.setString(2, vm.getLocation());
		ps.setString(3, vm.getVmSize());
		ps.setString(4, vm.getResourceGroup());
		ps.setString(5, vm.getClassification());
		ps.setInt(6, vm.getId());
		ps.executeUpdate();
		ps.close();
		con.close();
	}

	public static void deleteVM(int id) throws Exception {
		Connection con = DBConnection.getConnection();
		String sql = "DELETE FROM azure_vms WHERE id=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, id);
		ps.executeUpdate();
		ps.close();
		con.close();
	}
}

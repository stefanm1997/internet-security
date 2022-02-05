package org.unibl.etf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.unibl.etf.util.ConnectionPool;

public class DAOUser {

	public static final String GET_ROLE_FROM_USER = "SELECT role FROM korisnik WHERE username=?";

	public static String getRoleFromUser(String username) {
		String role = "";
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = c.prepareStatement(GET_ROLE_FROM_USER);
			ps.setString(1, username);
			rs = ps.executeQuery();
			// ps=c.createStatement();
			// rs=ps.executeQuery(GET_ROLE_FROM_USER);
			if (rs.next())
				role = rs.getString(1);
			else
				System.out.println("Nema role");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			ConnectionPool.getInstance().checkIn(c);
		}

		return role;

	}
}

package org.unibl.etf.dao;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.dto.DTOUser;
import org.unibl.etf.util.ConnectionPool;

public class DAOUser {

	public static final String GET_ALL_USERS = "SELECT * FROM korisnik";
	public static final String INSERT_USER = "INSERT INTO korisnik (username, password, role)" + " VALUES (?, ?, ?)";
	public static final String DELETE_USER = "DELETE FROM korisnik WHERE username=?";
	public static final String GET_ROLE_FROM_USER = "SELECT role FROM korisnik WHERE username=?";

	public static List<DTOUser> listOfUser() {

		List<DTOUser> list = new ArrayList<DTOUser>();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = c.prepareStatement(GET_ALL_USERS);
			rs = ps.executeQuery();
			while (rs.next()) {
				String username = rs.getString(2);
				int id = rs.getInt(1);
				String password = rs.getString(3);
				String role = rs.getString(4);

				list.add(new DTOUser(id, username, password, role));
			}
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
		return list;
	}

	public static void insertUser(DTOUser user) {
		Connection c = null;
		PreparedStatement ps = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = c.prepareStatement(INSERT_USER);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getRole());
			ps.execute();
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
		}
		ConnectionPool.getInstance().checkIn(c);
	}

	public static void deleteUser(String username) {

		Connection c = null;
		PreparedStatement ps = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = c.prepareStatement(DELETE_USER);
			ps.setString(1, username);
			ps.execute();
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
		}
		ConnectionPool.getInstance().checkIn(c);

	}

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

	public static String sha256(String base) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(base.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer();

			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}

			return hexString.toString();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}

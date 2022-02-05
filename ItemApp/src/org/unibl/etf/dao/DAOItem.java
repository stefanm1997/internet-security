package org.unibl.etf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.dto.DTOItem;
import org.unibl.etf.util.ConnectionPool;

public class DAOItem {

	private static final String GET_ALL_ITEMS = "SELECT * FROM artikl";
	public static final String INSERT_ITEM = "INSERT INTO artikl (code, name, producer, price)"
			+ " VALUES (?, ?, ?, ?)";
	public static final String DELETE_ITEM = "DELETE FROM artikl WHERE code=?";

	public static List<DTOItem> listOfArticle() {

		List<DTOItem> list = new ArrayList<DTOItem>();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = c.prepareStatement(GET_ALL_ITEMS);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new DTOItem(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5)));
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

	public static void insertItem(DTOItem item) {
		Connection c = null;
		PreparedStatement ps = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = c.prepareStatement(INSERT_ITEM);
			ps.setString(1, item.getCode());
			ps.setString(2, item.getName());
			ps.setString(3, item.getProducer());
			ps.setDouble(4, item.getPrice());
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

	public static void deleteItem(String code) {

		Connection c = null;
		PreparedStatement ps = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = c.prepareStatement(DELETE_ITEM);
			ps.setString(1, code);
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
}

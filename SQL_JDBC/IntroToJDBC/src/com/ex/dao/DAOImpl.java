package com.ex.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import com.ex.pojos.Example;
import com.ex.util.SingleConnection;

// NOTE: SQL is 1-indexed rather than 0-indexed

public class DAOImpl {

	/*
	 * Callable Statement
	 */
	public int getID(String name) {
		Connection connect = null;
		CallableStatement cs = null;
		int id = 0;

		try {
			connect = SingleConnection.getInstance().getConnection();

			// Callable statements need curly braces
			String sql = "{? = call findPerson(?)}";
			cs = connect.prepareCall(sql);
			cs.registerOutParameter(1, Types.NUMERIC);
			cs.setString(2, name);
			cs.execute();
			id = cs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cs.close();
				connect.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return id;
	}

	/*
	 * Prepared Statement
	 */
	public int addExample(String fn, String ln) {

		try(Connection conn = SingleConnection.getInstance().getConnection()) {
			conn.setAutoCommit(false);

			// No semi-colon inside the quotes
			String sql = "INSERT INTO example(firstname, lastname) values(?, ?)";
			String[] key = new String[1];
			key[0] = "ex_id";

			PreparedStatement prep = conn.prepareStatement(sql, key);
			prep.setString(1, fn);
			prep.setString(2, ln);

			// executeUpdate() returns the number of rows updated
			prep.executeUpdate();
			int id = 0;
			ResultSet pk = prep.getGeneratedKeys();

			// Use a while loop even though I only expect one result
			while(pk.next()) {
				id = pk.getInt(1);
			}

			conn.commit();
			return id;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Something went wrong
		return -1;

	}

	public ArrayList<Example> getAll() {
		ArrayList<Example> whateverFloatsYourBoat = new ArrayList<Example>();

		try(Connection conn = SingleConnection.getInstance().getConnection()) {

			String sql = "SELECT * from example";
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);

			while(rs.next()) {
				int id = rs.getInt(1);
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);
				int song_id = rs.getInt(4);

				whateverFloatsYourBoat.add(new Example(id, firstName, lastName, song_id));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return whateverFloatsYourBoat;
	}

	public ArrayList<Example> getAllWithTitle() {
		ArrayList<Example> whateverFloatsYourBoat = new ArrayList<Example>();

		try(Connection conn = SingleConnection.getInstance().getConnection()) {

			String sql = "SELECT ex.ex_id, ex.firstname, ex.lastname, tr.name" + 
					" FROM example ex" + 
					" LEFT JOIN track tr" + 
					" ON ex.favorite_song_id = tr.trackid";
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);

			while(rs.next()) {
				Example exp = new Example();
				exp.setId(rs.getInt(1));
				exp.setFirstName(rs.getString(2));
				exp.setLastName(rs.getString(3));
				exp.setSongName(rs.getString(4));

				whateverFloatsYourBoat.add(exp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return whateverFloatsYourBoat;
	}


}
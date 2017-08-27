package com.rev.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import com.rev.utils.ConnectionFactory;

public class DBDAO {
	Connection conn;
	
	public DBDAO () {
		conn = ConnectionFactory.getInstance().getConnection();
	}
	
	
	public int getID(String name) {
		try (CallableStatement cs = conn.prepareCall("{? = call findEmployee(?)}")) {
			cs.registerOutParameter(1, Types.NUMERIC);
			cs.setString(2, name);
			cs.execute();
			return cs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public String getFirstFromId(int id) {
		try (CallableStatement cs = conn.prepareCall("{? = call getFirstFromId(?)}")) {
			cs.registerOutParameter(1, Types.VARCHAR);
			cs.setInt(2, id);
			cs.execute();
			return cs.getString(1);
		} catch(SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public String getLastFromId(int id) {
		try (CallableStatement cs = conn.prepareCall("{? = call getLastFromId(?)}")) {
			cs.registerOutParameter(1, Types.VARCHAR);
			cs.setInt(2, id);
			cs.execute();
			return cs.getString(1);
		} catch(SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	
	public void disconnect() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
}

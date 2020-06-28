package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.CoppiaObjects;

public class ArtsmiaDAO {

	public void listObjects(Map<Integer, ArtObject> idMap) {
		String sql = "SELECT * from objects";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
				idMap.put(artObj.getId(), artObj);
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<CoppiaObjects> listCoppieObjects(Map<Integer, ArtObject> idMap) {
		String sql = "SELECT o1.object_id AS ob1, o2.object_id AS ob2, COUNT(DISTINCT eo1.exhibition_id) AS c " + 
				"FROM objects AS o1, objects AS o2, exhibition_objects AS eo1, exhibition_objects AS eo2 " + 
				"WHERE eo1.exhibition_id = eo2.exhibition_id AND eo1.object_id > eo2.object_id " + 
				"AND o1.object_id=eo1.object_id AND o2.object_id=eo2.object_id " +
				"GROUP BY o1.object_id, o2.object_id";
		List<CoppiaObjects> list = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next())
				list.add(new CoppiaObjects(idMap.get(res.getInt("ob1")), idMap.get(res.getInt("ob2")), res.getInt("c")));
			conn.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}

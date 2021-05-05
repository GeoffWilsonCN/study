package com.f.Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.management.RuntimeErrorException;

import com.f.bean.Commodity;
import com.f.util.DBHelper;
import com.f.util.PageBean;

public class CommodityDao {
	//获取所有信息
	public Map<String, Object> getAll(PageBean pb) throws Exception{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Commodity> list = new ArrayList<Commodity>();
		
		int totalCount = this.getTotalCount();
		pb.setTotalCount(totalCount);
		if(pb.getCurrentPage() <= 0) {
			pb.setCurrentPage(1);
		} else if(pb.getCurrentPage() > pb.getTotalPage()) {
			pb.setCurrentPage(pb.getTotalPage());
		}
		
		int currentPage = pb.getCurrentPage();
		int index = (currentPage - 1) * pb.getPageCount();
		int count = pb.getPageCount();
		
		try {
			String sql = "select * from commodity limit ?,?;";
			stmt = DBHelper.getConnection().prepareStatement(sql);
			stmt.setInt(1, index);
			stmt.setInt(2, count);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Commodity comm = new Commodity();
				comm.setId(rs.getInt("id"));
				comm.setClass_id(rs.getInt("class_id"));
				comm.setTitle(rs.getString("title"));
				comm.setPrice(rs.getFloat("price"));
				comm.setPlace(rs.getString("place"));
				comm.setAlcohol(rs.getString("alcohol"));
				comm.setContent(rs.getString("content"));
				list.add(comm);
			}
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("PageDate", pb);
			map.put("list", list);
			return map;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		} finally {
			if(rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
			if(stmt != null) {
				try {
					stmt.close();
					stmt = null;
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
		}
	}
	//获取总数
	public int getTotalCount() throws Exception{
		PreparedStatement pStatement = null;
		ResultSet rs = null;
		String sql = "select count(*) from commodity";
		int count = 0;
		try {
			pStatement = DBHelper.getConnection().prepareStatement(sql);
			rs = pStatement.executeQuery();
			rs.next();
			count = rs.getInt(1);
			System.out.println("数据库获得总条数：>>>>>"+count);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if(rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			if(pStatement != null) {
				try {
					pStatement.close();
					pStatement = null;
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		return count;
	}
	//获取总数
		public int getTotalCount(String isql) throws Exception{
			PreparedStatement pStatement = null;
			ResultSet rs = null;
			String sql = "select count(*) from commodity";
			int count = 0;
			try {
				String isql1 = isql.substring(0,isql.indexOf(":"));
				String isql2 = isql.substring(isql1.length()+1,isql.length());
				pStatement = DBHelper.getConnection().prepareStatement(isql2);
				rs = pStatement.executeQuery();
				rs.next();
				count = rs.getInt(1);
				System.out.println("数据库获得总条数：>>>>>"+count);
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				if(rs != null) {
					try {
						rs.close();
						rs = null;
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}
				if(pStatement != null) {
					try {
						pStatement.close();
						pStatement = null;
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}
			}
			return count;
		}
	//添加
	public boolean add(Commodity commodity) {
		String sql = "insert into commodity(id,class_id,title,price,place,alcohol,content)"
				+ "values(?,?,?,?,?,?,?);";
		PreparedStatement pSta = null;
		if(this.Compare(commodity)==false) {
			try {
				pSta = DBHelper.getConnection().prepareStatement(sql);
				pSta.setInt(1, commodity.getId());
				pSta.setInt(2, commodity.getClass_id());
				pSta.setString(3, commodity.getTitle());
				pSta.setFloat(4, commodity.getPrice());
				pSta.setString(5, commodity.getPlace());
				pSta.setString(6, commodity.getAlcohol());
				pSta.setString(7, commodity.getContent());
				pSta.execute();
			} catch (Exception e) {
				// TODO: handle exception
				throw new RuntimeException(e);
			} finally {
				try {
					pSta.close();
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
			return true;
		} else {
			return false;
		}
	}
	//比对
	public boolean Compare(Commodity commodity) {
		int id = -1;
		String sql = "select * from Commodity where id=?";
		ResultSet rs = null;
		PreparedStatement pSta = null;
		try {
			pSta = DBHelper.getConnection().prepareStatement(sql);
			pSta.setInt(1, commodity.getId());
			rs = pSta.executeQuery();
			while(rs.next()) {
				id = rs.getInt("id");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				pSta.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		if(id == -1) {
			return false;
		} else {
			return true;
		}
	}
	//搜索
	public Map<String ,Object> search(PageBean pb, Commodity commodity) throws Exception {
		PreparedStatement stmt = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		ArrayList<Commodity> list = new ArrayList<Commodity>();
		ArrayList cList = new ArrayList();
//		Map<String ,Object> lmap = new LinkedHashMap<String, Object>();
		Map<String ,Object> map = new LinkedHashMap<String ,Object>();
		int tag= 0;
		int t = 0;
		int flag = 0;
		String args[] = {
			"id",
			"class_id",
			"title",
			"price",
			"alcohol",
			"content"
		};
		
		
		int totalCount = this.getTotalCount();
		pb.setTotalCount(totalCount);
		
		if(pb.getCurrentPage() <= 0) {
			pb.setCurrentPage(1);
		} else if(pb.getCurrentPage() > pb.getTotalPage()) {
			pb.setCurrentPage(pb.getTotalPage());
		}
		
		int currentPage = pb.getCurrentPage();
		int index = (currentPage - 1) * pb.getPageCount();
		int count = pb.getPageCount();
//		int index = 1;
//		int count = 2;
		try {
			StringBuilder isql = new StringBuilder("select count(*) from commodity where 1=1");
			StringBuilder sql = new StringBuilder("select * from commodity where 1=1");
			//判定bean中的属性是否需要查询
			int id = commodity.getId();
			if(id != 0) {
				sql.append(" and id= ?");
				isql.append(" and id= ?");
//				if(flag != 0) {
//					cList.add(flag += 1,id);
//				} else 
				map.put("id", id);
				tag += 1;
			}
//			Thread.sleep(10);
			int class_id = commodity.getClass_id();
			if(class_id != 0) {
				sql.append(" and class_id= ?");
				isql.append(" and class_id= ?");
//				if(flag != 0) {
//					cList.add(flag += 1,class_id);
//				} else 
//					cList.add(flag,class_id);
				map.put("class_id", class_id);
				tag += 1;
			}
			String title = commodity.getTitle();
			if(title != null && !title.trim().isEmpty()) {
				sql.append(" and title like ?");
				isql.append(" and title like ?");
//				if(flag != 0) {
//					cList.add(flag += 1,title);
//				} else 
//					cList.add(flag,title);
				map.put("title", title);
				tag += 1;
			}
			float price = commodity.getPrice();
			if(price != 0) {
				sql.append(" and price= ?");
				isql.append(" and price= ?");
//				if(flag != 0) {
//					cList.add(flag += 1,price);
//				} else 
//					cList.add(flag,price);
				map.put("price", price);
				tag += 1;
			}
			String place = commodity.getPlace();
			if(place != null && !place.trim().isEmpty()) {
				sql.append(" and place like ?");
				isql.append(" and place like ?");
//				if(flag != 0) {
//					cList.add(flag += 1,place);
//				} else 
//					cList.add(flag,place);
				map.put("place", place);
				tag += 1;
			}
			String alcohol = commodity.getAlcohol();
			if(alcohol != null && !alcohol.trim().isEmpty()) {
				sql.append(" and alcohol = ?");
				isql.append(" and alcohol = ?");
//				if(flag != 0) {
//					cList.add(flag += 1,alcohol);
//				} else 
//					cList.add(flag,alcohol);
				map.put("alcohol", alcohol);
				tag += 1;
			}
			//范围查询检测
			String als = commodity.getAls();
			if(als == null || als.trim().isEmpty()) {
				als = "0";
				sql.append(" and alcohol >= ?");
				isql.append(" and alcohol >= ?");
			} else {
				sql.append(" and alcohol >= ?");
				isql.append(" and alcohol >= ?");
			}
			map.put("als", als);
			tag += 1;
			String ale = commodity.getAle();
			if(ale == null || ale.trim().isEmpty()) {
				
			} else 
			if(ale != null || !ale.trim().isEmpty()){
				sql.append(" and alcohol <= ?");
				isql.append(" and alcohol <= ?");
				map.put("ale", ale);
				tag += 1;
			}
			if((als == null || als.trim().isEmpty()) && (ale == null || ale.trim().isEmpty())){
				
			}
			
			float prs = commodity.getPrs();
			if(prs == 0) {
				prs = 0;
				sql.append(" and price >= ?");
				isql.append(" and price >= ?");
			} else {
				sql.append(" and price >= ?");
				isql.append(" and price >= ?");
			}
			map.put("prs", prs);
			tag += 1;
			float pre = commodity.getPre();
			if(pre == 0) {
				
			} else 
			if(pre != 0){
				sql.append(" and price <= ?");
				isql.append(" and price <= ?");
				map.put("pre", pre);
				tag += 1;
			}
			if((als == null || als.trim().isEmpty()) && (ale == null || ale.trim().isEmpty())){
				
			}
			
			System.out.println("tag:....."+tag);
			sql.append(" limit ?,?;");
//			isql.append(" limit ?,?;");
			//判定需要添加的查询关键字
			
			String finalSql = sql.toString();
			String finalisql = isql.toString();
			
			System.out.println("sql=...."+finalSql.toString());
			System.out.println("finalIsql=...."+finalisql.toString());
//			Collections.reverse(cList);
			stmt = DBHelper.getConnection().prepareStatement(finalSql);
			st = DBHelper.getConnection().prepareStatement(finalisql);
			
			Set<Entry<String ,Object>> set = map.entrySet();
			Iterator<Entry<String,Object>> ite = set.iterator();
			int i=1;
			System.out.println("执行之前的id："+id);
			while(ite.hasNext()) {
				Entry<String ,Object> entry = ite.next();
				String key = (String) entry.getKey();
				
				if(key.equals("id")) {
					stmt.setInt((tag-(tag-i)), id);
					st.setInt((tag-(tag-i)), id);
					System.out.println("执行到了判定stmt----id");
					i++;
					continue;
				} else
				if (key.equals("class_id")) {
					stmt.setInt((tag-(tag-i)), class_id);
					st.setInt((tag-(tag-i)), class_id);
					System.out.println("执行到了判定stmt----class_id");
					i++;
					continue;
				} else
				if (key.equals("title")) {
					stmt.setString((tag-(tag-i)), title);
					st.setString((tag-(tag-i)), title);
					System.out.println("执行到了判定stmt----title");
					i++;
					continue;
				} else
				if (key.equals("price")) {
					stmt.setFloat((tag-(tag-i)), price);
					st.setFloat((tag-(tag-i)), price);
					System.out.println("执行到了判定stmt----price");
					i++;
					continue;
				} else
				if (key.equals("place")) {
					stmt.setString((tag-(tag-i)), place);
					st.setString((tag-(tag-i)), place);
					System.out.println("执行到了判定stmt----place");
					i++;
					continue;
				} else
				if (key.equals("alcohol")) {
					stmt.setString((tag-(tag-i)), alcohol);
					st.setString((tag-(tag-i)), alcohol);
					System.out.println("执行到了判定stmt----alcohol");
					i++;
					continue;
				} else
					//范围stmt set值
				if (key.equals("als")) {
					stmt.setFloat((tag-(tag-i)), Float.parseFloat(als));
					st.setFloat((tag-(tag-i)), Float.parseFloat(als));
					System.out.println("执行到了判定stmt----als");
					i++;
					continue;
				}
				if (key.equals("ale")) {
					stmt.setFloat((tag-(tag-i)), Float.parseFloat(ale));
					st.setFloat((tag-(tag-i)), Float.parseFloat(ale));
					System.out.println("执行到了判定stmt----ale");
					i++;
					continue;
				}
				if (key.equals("prs")) {
					stmt.setFloat((tag-(tag-i)), prs);
					st.setFloat((tag-(tag-i)), prs);
					System.out.println("执行到了判定stmt----prs");
					i++;
					continue;
				}
				if (key.equals("pre")) {
					stmt.setFloat((tag-(tag-i)), pre);
					st.setFloat((tag-(tag-i)), pre);
					System.out.println("执行到了判定stmt----pre");
					i++;
					continue;
				}
			}
			System.out.println("st:----"+st.toString());
			t = this.getTotalCount(st.toString());
			
			if(t == 0) {
				t=1;
			} else {
				
			}
			int totalCount1 = t;
			System.out.println("t:----"+t);
			pb.setTotalCount(totalCount1);
			
			if(pb.getCurrentPage() <= 0) {
				pb.setCurrentPage(1);
			} else if(pb.getCurrentPage() > pb.getTotalPage()) {
				pb.setCurrentPage(pb.getTotalPage());
			}
			
			int currentPage1 = pb.getCurrentPage();
			int index1 = (currentPage1 - 1) * pb.getPageCount();
			int count1 = pb.getPageCount();
			
			System.out.println("tag=>>>>>>"+tag);
			System.out.println("index=>>>>>>"+index);
			System.out.println("count=>>>>>>"+count);
			stmt.setInt((tag+1), index1);
			stmt.setInt((tag+2), count1);
			System.out.println("最终sql："+stmt.toString());	
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				Commodity bean = new Commodity();
				bean.setId(rs.getInt("id"));
				bean.setClass_id(rs.getInt("class_id"));
				bean.setTitle(rs.getString("title"));
				bean.setPrice(rs.getFloat("price"));
				bean.setPlace(rs.getString("place"));
				bean.setAlcohol(rs.getString("alcohol"));
				bean.setContent(rs.getString("content"));
				list.add(bean);
			}
			Map<String,Object> dataMap = new HashMap<String ,Object>();
			dataMap.put("PageDate", pb);
			dataMap.put("list", list);
			return dataMap;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
			if(stmt != null) {
				try {
					stmt.close();
					stmt = null;
				} catch (Exception e3) {
					// TODO: handle exception
					e3.printStackTrace();
				}
			}
		}
		return null;
	}
}

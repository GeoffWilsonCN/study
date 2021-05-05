package com.f.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.f.Dao.CommodityDao;
import com.f.bean.Commodity;
import com.f.util.PageBean;
import com.mysql.cj.xdevapi.JsonArray;

/**
 * Servlet implementation class CommServlet
 */
@WebServlet("/CommServlet")
public class CommServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String uri;

	/**
	 * Default constructor.
	 */
	public CommServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String method = request.getParameter("method");
		System.out.println(method);
		if ("add".equals(method)) {
			add(request, response);
			System.out.println("执行到了add");
		} else if ("search".equals(method)) {
			search(request, response);
			System.out.println("执行到了search");
		}
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			PrintWriter out = response.getWriter();
			int id = Integer.valueOf(request.getParameter("id"));
			int class_id = Integer.valueOf(request.getParameter("class_id"));
			String title = request.getParameter("title");
			float price = Float.valueOf(request.getParameter("price"));
			String place = request.getParameter("place");
			String alcohol = request.getParameter("alcohol");
			String content = request.getParameter("content");

			Commodity comm = new Commodity();
			comm.setId(id);
			comm.setClass_id(class_id);
			comm.setTitle(title);
			comm.setPrice(price);
			comm.setPlace(place);
			comm.setAlcohol(alcohol);
			comm.setContent(content);

			CommodityDao dao = new CommodityDao();
			if (dao.add(comm) == true) {
				out.println("<script language='javascript'>");
				out.println("alert('添加成功')");
				out.println("window.top.location.href='" + request.getContextPath() + "/Add.jsp';");
				out.println("</script>");
				out.flush();
				out.close();
			} else {
				out.println("<script language='javascript'>");
				out.println("alert('id重复')");
				out.println("window.top.location.href='" + request.getContextPath() + "/Add.jsp';");
				out.println("</script>");
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Commodity comm = new Commodity();
			String tagName;
			String currPage = request.getParameter("currentPage");
			boolean flag = true;
			Map<String, Object> map = new HashMap<String, Object>();
			Enumeration enu = request.getParameterNames();
			while (enu.hasMoreElements()) {
				String name = (String) enu.nextElement();
				if (request.getParameter(name) == null || request.getParameter(name).equals("")) {

				} else if (name.equals("method") || name.equals("list") || name.equals("save") || name.equals("currentPage")) {

				} else if(flag){
					flag = false;
					String value = (String) request.getParameter(name);
					map.put(name, value);
					if (name.equals("id")) {
						comm.setId(Integer.parseInt(value));
						flag = true;
					}
					if (name.equals("class_id")) {
						comm.setClass_id(Integer.parseInt(value));
						flag = true;
					}
					if (name.equals("title")) {
						comm.setTitle(value);
						flag = true;
					}
					if (name.equals("price")) {
						comm.setPrice(Float.parseFloat(value));
						flag = true;
					}
					if (name.equals("place")) {
						comm.setPlace(value);
						flag = true;
					}
					if (name.equals("alcohol")) {
						comm.setAlcohol(value);
						flag = true;
					}
					if (name.equals("als")) {
						if (value == null || value.equals("")) {
							value = "0";
							comm.setAls(value);
							flag = true;
						} else {
							comm.setAls(value);
							flag = true;
						}
							
					}
					if (name.equals("ale")) {
						comm.setAle(value);
						flag = true;
					}
					if (name.equals("prs")) {
						if (value == null || value.equals("")) {
							value = "0";
							comm.setAls(value);
							flag = true;
						} else {
							comm.setPrs(Float.parseFloat(value));
							flag = true;
						}
							
					}
					if (name.equals("pre")) {
						comm.setPre(Float.parseFloat(value));
						flag = true;
					}
				}
				if(flag = true) {
					
				} else {
					flag = false;
				}
				
			}
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
			}

			if (currPage == null || "".equals(currPage.trim())) {
				currPage = "1";
			}
			int currentPage = Integer.parseInt(currPage);
			System.out.println("search获得的页数" + currentPage);
			PageBean pageBean = new PageBean();
			pageBean.setCurrentPage(currentPage);

			if ("clear".equals(request.getParameter("save"))) {
				clearInfoForFile();
			}
			if(!map.isEmpty()) {
				CommServlet.save(map);
			}
			
			if (flag == true) {
				map = this.read();
				if(map == null) {
					
				} else {
					for (Map.Entry<String, Object> entry : map.entrySet()) {
						String name = (String) entry.getKey();

						String value = (String) entry.getValue();
						if (name.equals("id")) {
							comm.setId(Integer.parseInt(value));
						}
						if (name.equals("class_id")) {
							comm.setClass_id(Integer.parseInt(value));
						}
						if (name.equals("title")) {
							comm.setTitle(value);
						}
						if (name.equals("price")) {
							comm.setPrice(Float.parseFloat(value));
						}
						if (name.equals("place")) {
							comm.setPlace(value);
						}
						if (name.equals("alcohol")) {
							comm.setAlcohol(value);
						}
						if (name.equals("als")) {
							if (value == null || value.equals("")) {
								value = "0";
								comm.setAls(value);
							} else
								comm.setAls(value);
						}
						if (name.equals("ale")) {
							comm.setAle(value);
						}
						if (name.equals("prs")) {
							if (value == null || value.equals("")) {
								value = "0";
								comm.setAls(value);
							} else
								comm.setPrs(Float.parseFloat(value));
						}
						if (name.equals("pre")) {
							comm.setPre(Float.parseFloat(value));
						}

					}
				}
				
			}
			CommodityDao dao = new CommodityDao();
			Map<String, Object> result = dao.search(pageBean, comm);

			request.setAttribute("result", result);
			uri = "/info.jsp";

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		request.getRequestDispatcher(uri).forward(request, response);
	}

	public static void save(Map<String, Object> map) throws Exception {
		String path = "C:/json/";
		JSONObject json = new JSONObject(map);
		String mapJson = json.toJSONString(map);
		System.out.println("mapjson" + mapJson);
		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path + "temp.json"),
				StandardCharsets.UTF_8);
		osw.write(mapJson);
		osw.flush();
		osw.close();
	}

	public void clearInfoForFile() {
		File file = new File("C:/json/temp.json");

		try {
			if (!file.exists()) {
				file.createNewFile();

			}

			FileWriter fileWriter = new FileWriter(file);

			fileWriter.write("");

			fileWriter.flush();

			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	public Map<String, Object> read() throws Exception{
		String path = "C:/json/temp.json";
		String json = readJson(path);
		System.out.println("读取到的json" + json);
		Map<String, Object> map = new HashMap<String, Object>();
		map = (Map) JSON.parse(json);
		if(map == null || map.isEmpty()) {
			
		} else {
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				System.out.println("json转后的map：key = " + entry.getKey() + ", value = " + entry.getValue());
			}
		}
		
		return map;
	}

	public String readJson(String filename) {
		String jsonStr;
		try {
			File file = new File(filename);
			FileReader fileReader = new FileReader(file);
			Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
			int ch;
			StringBuilder sb = new StringBuilder();
			while ((ch = reader.read()) != -1) {
				sb.append((char) ch);
			}
			fileReader.close();
			reader.close();
			jsonStr = sb.toString();
			return jsonStr;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
}

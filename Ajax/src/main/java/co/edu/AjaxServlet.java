package co.edu;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet({ "/AjaxServlet", "/ajax.do" }) // AjaxServlet이나 /ajax.do를 치면
public class AjaxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AjaxServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8"); // 페이지안에 한글이 있으면 출력됨

		String job = request.getParameter("job");
		PrintWriter out = response.getWriter(); // 출력스트림

		if (job.equals("html")) { // http://localhost/Ajax/ajax.do?job=html로 들어왔을때 나오는 페이지
			out.print("<h3>아작스페이지입니다</h3>");
			out.print("<a href='index.html'>첫페이지로</a>");
//		response.getWriter().append("Served at: ").append(request.getContextPath()); // AjaxServlet이나 /ajax.do를 치면 이 결과를 가져오겠다 뭐 이런뜻인거같음

		} else if (job.equals("json")) { // http://localhost/Ajax/AjaxServlet?job=json 이면 이 화면이 뜸
//			out.print("<h3>JSON페이지입니다</h3>");
//			out.print("<a href='index.html'>첫페이지로</a>");

			// [{"fname":?, "lname":?, {}, {}]
//			String json = "[";
			EmpDAO dao = new EmpDAO();
			List<Employee> list = dao.empList();
//			for(int i = 0; i < list.size(); i++) {
//				json += "{\"fname\":" + list.get(i).getFirstName() + "}";
//				if(i != list.size() - 1) {
//					json += ",";
//				}
//			}
//			json += "]";

			// JSON이 아닌 새로운 Gson이라는걸 사용
			Gson gson = new GsonBuilder().create();
			out.print(gson.toJson(list));
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		String cmd = request.getParameter("cmd");

		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String email = request.getParameter("email");
		String job = request.getParameter("job");
		String hdate = request.getParameter("hdate");

		Employee emp = new Employee();
		emp.setFirstName(fname);
		emp.setLastName(lname);
		emp.setEmail(email);
		emp.setJobId(job);
		emp.setHireDate(hdate);

		if (cmd.equals("insert")) {

			EmpDAO dao = new EmpDAO();
			dao.insertEmp(emp);

			System.out.println(emp);

//		doGet(request, response);
		} else if (cmd.equals("update")) {

			EmpDAO dao = new EmpDAO();

			if (dao.updateEmp(emp) == null) {
				// {"retCode":"error"}
				System.out.println("error");
			} else {
				// {"retCode":"success"}
				System.out.println("success");
			}
		}

		Gson gson = new GsonBuilder().create();
		response.getWriter().print(gson.toJson(emp));
	}

}

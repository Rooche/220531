package co.edu;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@WebServlet("/SchedulesServlet")
public class SchedulesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SchedulesServlet() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/json;charset=utf8");
		
		EmpDAO dao = new EmpDAO();
		List<Schedule> list = dao.scheduleList();
		Gson gson = new GsonBuilder().create();
		response.getWriter().print(gson.toJson(list));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		String job = request.getParameter("job");
		String title = request.getParameter("title");
		String sd = request.getParameter("start");
		String ed = request.getParameter("end");
		Schedule sch = new Schedule();
		sch.setTitle(title);
		sch.setStart(sd);
		sch.setEnd(ed);
		
		EmpDAO dao = new EmpDAO();
		if(job.equals("add")) {
			dao.insertSchedule(sch);
			//{"retCode:"Success"}
			response.getWriter().print("{\"retCod\":\"Success\"}");
			
		} else if(job.equals("del")) {
			dao.deleteSchedule(sch);
			response.getWriter().print("{\"retCod\":\"Success\"}");
			
		} else {
			response.getWriter().print("{\"retCod\":\"No Request\"}");
		}
	}

}

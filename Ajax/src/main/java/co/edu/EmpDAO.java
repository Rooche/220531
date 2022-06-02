package co.edu;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmpDAO extends DAO {
	
	// 스케줄 리스트
	public List<Schedule> scheduleList(){
		connect();
		List<Schedule> list = new ArrayList<Schedule>(); // 변수선언
		String sql = "SELECT * FROM SCHEDULES";
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while (rs.next()) {
				Schedule emp = new Schedule();
				emp.setTitle(rs.getString("title"));
				emp.setStart(rs.getString("start_date"));
				emp.setEnd(rs.getString("end_date"));
				list.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}

	
	// 스케줄 등록
	public void insertSchedule(Schedule sched) {
		connect();
		String sql = "INSERT INTO SCHEDULES(TITLE, START_DATE, END_DATE)VALUES(?, ?, ?)";
		try {
			psmt = conn.prepareStatement(sql);// 첫번째쿼리 실행
				psmt.setString(1, sched.getTitle());
				psmt.setString(2, sched.getStart());
				psmt.setString(3, sched.getEnd());
				psmt.executeUpdate();
				
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	
	
	// 스케줄 삭제
	public void deleteSchedule(Schedule sched) {
		connect();
		String sql = "DELETE FROM SCHEDULES WHERE TITLE = ?";
		try {
			psmt = conn.prepareStatement(sql);// 첫번째쿼리 실행
			psmt.setString(1, sched.getTitle());
			psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	
	
	// 부서별 인원(차트) : 반환(리턴)되는 타입이 부서명 = 인원. Map<String, Integer>
	public Map<String, Integer> getMemberByDept(){
		Map<String, Integer> map = new HashMap<String, Integer>();
		connect(); 
		// \r\n 지우고싶으면 스페이스바 눌러서 지우기
		String sql = "select d.department_name, count(1) as cnt\r\n"
				+ "from employees e, departments d\r\n"
				+ "where e.department_id = d.department_id\r\n"
				+ "group by d.department_name";
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while(rs.next()) { // key = value, key:부서명 = value:인원
				map.put(rs.getString("department_name"), rs.getInt("cnt"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	
	// 리스트
	public List<Employee> empList() {
		connect();
		List<Employee> list = new ArrayList<Employee>(); // 변수선언
		try {
			psmt = conn.prepareStatement("SELECT * FROM EMP_TEMP ORDER BY 1");
			rs = psmt.executeQuery();
			while (rs.next()) {
				Employee emp = new Employee();
				emp.setEmployeeId(rs.getInt("employee_id"));
				emp.setFirstName(rs.getString("first_name"));
				emp.setLastName(rs.getString("last_name"));
				emp.setEmail(rs.getString("email"));
				emp.setJobId(rs.getString("job_id"));
				emp.setHireDate(rs.getString("hire_date"));

				list.add(emp);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}

	// 입력 소문자를 대문자로 컨트롤 + 쉬프트 + x
	public Employee insertEmp(Employee emp) {
		String sql = "INSERT INTO EMP_TEMP (EMPLOYEE_ID, FIRST_NAME, LAST_NAME, EMAIL, HIRE_DATE, JOB_ID)"
				+ " VALUES(?,?,?,?,?,?)";
		String seqSql = "SELECT EMPLOYEES_SEQ.NEXTVAL FROM DUAL";

		connect();
		int nextSeq = -1;
		try {
			psmt = conn.prepareStatement(seqSql);// 첫번째쿼리 실행
			rs = psmt.executeQuery();
			if (rs.next()) {
				nextSeq = rs.getInt(1); // 첫번째 컬럼을 가져오겠다 EMPLOYEES_SEQ 이거뿐이라 1
			}

			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, nextSeq);
			psmt.setString(2, emp.getFirstName());
			psmt.setString(3, emp.getLastName());
			psmt.setString(4, emp.getEmail());
			psmt.setString(5, emp.getHireDate());
			psmt.setString(6, emp.getJobId());
			// 각각 파라메터마다 값을 넣어주겠다
			int r = psmt.executeUpdate(); // 이게 있어야 실행
			System.out.println(r + "건 입력됨");

			emp.setEmployeeId(nextSeq);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return emp;
	}

	// 수정
	public Employee updateEmp(Employee emp) {
		connect();
		String sql = "UPDATE EMP_TEMP SET FIRST_NAME=?, LAST_NAME=?, EMAIL=?, HIRE_DATE=?, JOB_ID=? WHERE EMPLOYEE_ID=?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, emp.getFirstName());
			psmt.setString(2, emp.getLastName());
			psmt.setString(3, emp.getEmail());
			psmt.setString(4, emp.getHireDate());
			psmt.setString(5, emp.getJobId());
			psmt.setInt(6, emp.getEmployeeId());
			int r = psmt.executeUpdate();
			System.out.println(r + "건 수정");
			if (r > 0) {
				return emp;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

		return null;

	}

	// 삭제
	public Employee deleteEmp(Employee emp) {
		connect();
		String sql = "DELETE FROM EMP_TEMP WHERE EMPLOYEE_ID = ?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, emp.getEmployeeId());
			psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return emp;
	}
	// 한건조회
}

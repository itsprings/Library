
/*ReturnBookDao.java*/

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReturnBookDao {

	public static int delete(String book_id, int studentid) {
		int status = 0;
		try {
			Connection con = DB.getConnection();

			status = updatebook(book_id);// updating quantity and issue

			if (status > 0) {
				PreparedStatement ps = con.prepareStatement("delete from issuebooks where book_id=? and student_id=?");
				ps.setString(1, book_id);
				ps.setInt(2, studentid);
				status = ps.executeUpdate();
			}

			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return status;
	}

	public static int updatebook(String bookid) {
		int status = 0;
		int quantity = 0, issued = 0;
		try {
			Connection con = DB.getConnection();

			PreparedStatement ps = con.prepareStatement("select quantity,issued from books where bookid=?");
			ps.setString(1, bookid);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				quantity = rs.getInt("quantity");
				issued = rs.getInt("issued");
			}

			if (issued > 0) {
				PreparedStatement ps2 = con.prepareStatement("update books set quantity=?,issued=? where bookid=?");
				ps2.setInt(1, quantity + 1);
				ps2.setInt(2, issued - 1);
				ps2.setString(3, bookid);

				status = ps2.executeUpdate();
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return status;
	}
}

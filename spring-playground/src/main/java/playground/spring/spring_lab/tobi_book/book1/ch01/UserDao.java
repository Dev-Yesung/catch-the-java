package playground.spring.spring_lab.tobi_book.book1.ch01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

	private final ConnectionMaker connectionMaker;

	public UserDao(ConnectionMaker connectionMaker) {
		this.connectionMaker = connectionMaker;
	}

	public void add(User user) throws SQLException, ClassNotFoundException {
		// 첫 번째 관심사
		Connection c = connectionMaker.makeNewConnection();

		// 두 번째 관심사
		PreparedStatement ps = c.prepareStatement("INSERT INTO USERS(ID, NAME, PASSWORD) VALUES(?, ? ,?)");
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());

		ps.executeUpdate();

		// 세 번째 관심사
		ps.close();
		c.close();
	}

	public User get(String id) throws ClassNotFoundException, SQLException {
		// 첫 번째 관심사
		Connection c = connectionMaker.makeNewConnection();

		// 두 번째 관심사
		PreparedStatement ps = c.prepareStatement("SELECT * FROM USERS WHERE ID = ?");
		ps.setString(1, id);

		ResultSet rs = ps.executeQuery();
		rs.next();
		User user = new User();
		user.setId(rs.getString("ID"));
		user.setName(rs.getString("NAME"));
		user.setPassword(rs.getString("PASSWORD"));

		// 세 번째 관심사
		rs.close();
		ps.close();
		c.close();

		return user;
	}

}

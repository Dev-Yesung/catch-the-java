package playground.spring.spring_lab.tobi_book.book1.ch01;

public class DaoFactory {

	public UserDao userDao() {
		ConnectionMaker connectionMaker = new DConnectionMaker();

		return new UserDao(connectionMaker);
	}

}

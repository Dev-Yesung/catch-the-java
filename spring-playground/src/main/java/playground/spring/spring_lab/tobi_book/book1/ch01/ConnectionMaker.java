package playground.spring.spring_lab.tobi_book.book1.ch01;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionMaker {

	Connection makeNewConnection() throws ClassNotFoundException, SQLException;

}

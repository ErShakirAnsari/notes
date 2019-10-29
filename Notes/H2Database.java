
package h2database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.h2.jdbcx.JdbcConnectionPool;

/**
 *
 * @author admin
 */
public class H2Database
{
	public static void main(String[] args) throws SQLException, ClassNotFoundException
	{
		long start = System.currentTimeMillis();
		JdbcConnectionPool jdbcConnectionPool = JdbcConnectionPool.create("jdbc:h2:./test", "sa", "admin");
		System.out.println("jdbcConnectionPool.getMaxConnections() = " + jdbcConnectionPool.getMaxConnections());

		for (int i = 0; i < 10; i++)
		{
			try (Connection connection = jdbcConnectionPool.getConnection();)
			{
			}
		}
		System.out.println("pool time: " + (System.currentTimeMillis() - start));

		jdbcConnectionPool.dispose();

		//-----------------
		start = System.currentTimeMillis();
		for (int i = 0; i < 10; i++)
		{
			Class.forName("org.h2.Driver");
			try (Connection connection = DriverManager.getConnection("jdbc:h2:./test", "sa", "admin");)
			{
			}
		}
		System.out.println("without pool time: " + (System.currentTimeMillis() - start));

	}
}

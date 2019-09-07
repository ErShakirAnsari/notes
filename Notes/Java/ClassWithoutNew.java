
/**
 *
 * @author Shakir
 */
public class ClassWithoutNew
{
	public static void main(String[] args) throws Exception
	{
		Class refClass = Class.forName("com.api.test.Human");
		Human human = (Human) refClass.newInstance();
		human.sayHello();
	}
}

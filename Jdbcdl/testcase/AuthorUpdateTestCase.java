import com.thinking.machines.library.Jdbcdl.*;
class AuthorUpdateTestCase
{
public static void main(String data[])
{
AuthorInterface author=new Author();
author.setCode(Integer.parseInt(data[0]));
author.setName(data[1]);
AuthorDAOInterface authorDAO=new AuthorDAO();
try
{
authorDAO.update(author);
System.out.println("Author updated");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
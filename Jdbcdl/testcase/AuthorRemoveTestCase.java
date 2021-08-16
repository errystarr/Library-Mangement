import com.thinking.machines.library.Jdbcdl.*;
class AuthorRemoveTestCase
{
public static void main(String data[])
{
AuthorInterface author=new Author();
author.setCode(Integer.parseInt(data[0]));
AuthorDAOInterface authorDAO=new AuthorDAO();
try
{
authorDAO.remove(author.getCode());
System.out.println("Author deleted");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
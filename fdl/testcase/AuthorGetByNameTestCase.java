import com.thinking.machines.library.dl.*;
class AuthorGetByNameTestCase
{
public static void main(String data[])
{
try
{
AuthorInterface author=new Author();
author.setName(data[0]);
AuthorDAOInterface authorDAO=new AuthorDAO();
authorDAO.getByName(author.getName());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
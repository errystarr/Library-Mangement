import com.thinking.machines.library.dl.*;
class AuthorGetByCodeTestCase
{
public static void main(String data[])
{
try
{
AuthorInterface author=new Author();
author.setCode(Integer.parseInt(data[0]));
AuthorDAOInterface authorDAO=new AuthorDAO();
authorDAO.getByCode(author.getCode());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
import com.thinking.machines.library.dl.*;
class AuthorAddTestCase
{
public static void main(String data[])
{
AuthorInterface author=new Author();
author.setName(data[0]);
AuthorDAOInterface authorDAO=new AuthorDAO();
try
{
authorDAO.add(author);
System.out.println("Author added with code as:"+author.getCode());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
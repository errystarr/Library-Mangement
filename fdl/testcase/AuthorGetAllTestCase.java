import com.thinking.machines.library.dl.*;
import java.util.*;
class AuthorGetAllTestCase
{
public static void main(String gg[])
{
try
{
LinkedList<AuthorInterface>authors;
AuthorDAOInterface authorDAO= new AuthorDAO();
authors =authorDAO.getAll();
AuthorInterface author;
for(int i=0;i<authors.size();i++)
{
author = authors.get(i);
System.out.println(author.getCode()+","+author.getName());
}
}catch(DAOException daoException)
{
System.out.println(daoException);
}
}
}
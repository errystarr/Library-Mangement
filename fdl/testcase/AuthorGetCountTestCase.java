import com.thinking.machines.library.dl.*;
class AuthorGetCountTestCase
{
public static void main(String gg[])
{
try
{
AuthorDAOInterface authorDAO=new AuthorDAO();
long count;
count=authorDAO.getCount();
throw new DAOException("TOTAL NUMBER OF RECORDS "+count);
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
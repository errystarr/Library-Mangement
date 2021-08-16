import com.thinking.machines.library.bl.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import javax.swing.table.*;
import java.awt.event.*;
class AuthorModelTestCase extends JFrame
{
private Container c;
private JTable table;
private JScrollPane jsp;
private AuthorModel m;
private JLabel l1,l2;
private JTextField t1,t2;
private JButton b1,b2,b3,b4;
private JCheckBox c1,c2;
AuthorModelTestCase()
{
m=new AuthorModel();
table=new JTable(m);
Font f=new Font("Verdana",Font.PLAIN,16);
table.setFont(f);
table.setRowHeight(30);
jsp=new
JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
t1=new JTextField(20);
t2=new JTextField(20);
b1=new JButton("Add");
b2=new JButton("Remove");
b3=new JButton("Update");
b4=new JButton("Search");
l1=new JLabel();
l2=new JLabel("Authors");
l3=new JLabel();
c1=new JCheckBox("LeftCompare");
c2=new JCheckBox("CaseSensitive");
JPanel p1 =new JPanel();
p1.setLayout(new GridLayout(3,3));
p1.add(t1);
p1.add(t2);
p1.add(b1);
p1.add(b2);
p1.add(b3);
p1.add(b4);
p1.add(l1);
p1.add(c1);
p1.add(c2);
b1.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent e)
{
String name=t1.getText();
AuthorInterface author=new Author();
author.setName(name);
try
{
m.add(author);
JOptionPane.showMessageDialog(c,"Author added with code"+author.getCode());
}catch(BLException blException)
{
ArrayList<String>properties=new ArrayList<String>();
properties=blException.getProperties();
for(String str:properties)
{
System.out.println(blException.getException(str));
}
}
}
});
b2.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
try
{
m.remove(Integer.parseInt(t2.getText()));
l1.setText("Author removed");
}catch(BLException e)
{
l1.setText(e.getMessage());
}

}
});
b3.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
AuthorInterface author;
author=new Author();
author.setName(t1.getText());
author.setCode(Integer.parseInt(t2.getText()));
try
{
m.update(author);
l1.setText("Author Updated");
}catch(BLException e)
{
l1.setText(e.getMessage());
}
}
});
b4.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
try
{
l2.setText(""+m.getAuthorIndexByName(t1.getText(),true,true));
}catch(BLException e)
{
l2.setText(e.getMessage());
}
}
});
c=getContentPane();
c.setLayout(new BorderLayout());
c.add(jsp,BorderLayout.CENTER);
c.add(l2,BorderLayout.NORTH);
c.add(p1,BorderLayout.SOUTH);
setDefaultCloseOperation(EXIT_ON_CLOSE);
setLocation(10,10);
setSize(500,400);
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
setLocation(d.width/2-250,d.height/2-200);
setVisible(true);
}
public static void main(String g[])
{
AuthorModelTestCase amtc= new AuthorModelTestCase();
}
}



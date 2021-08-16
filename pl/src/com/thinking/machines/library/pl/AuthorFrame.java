package com.thinking.machines.library.pl;
import javax.swing.*;
import java.awt.*;
public class AuthorFrame extends JFrame
{
private ImageIcon icon;
private Container container;
private AuthorPanel authorPanel;
public AuthorFrame()
{
initComponents();
setAppearance();
addListeners();
}
private void initComponents()
{
setTitle(Application.TITLE);
icon=new ImageIcon(this.getClass().getResource("logo.png"));
setIconImage(icon.getImage());
container=getContentPane();
authorPanel=new AuthorPanel();
//authorPanel.setBorder(BorderFactory.createLineBorder(Color.red));
authorPanel.setBounds(5,5,475,600);
}
private void setAppearance()
{
// font & other settings

// add to container
container.setLayout(null);
container.add(authorPanel);
setSize(500,650);
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
setLocation(d.width/2-getWidth()/2,d.height/2-getHeight()/2);
}
private void addListeners()
{
setDefaultCloseOperation(DISPOSE_ON_CLOSE);
}
}
package com.thinking.machines.library.pl;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.*;
import javax.swing.filechooser.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import com.thinking.machines.library.bl.*;
public class AuthorPanel extends JPanel implements DocumentListener,ListSelectionListener
{
private ImageIcon clearIcon;
private static final int VIEW_MODE=1;
private static final int ADD_MODE=2;
private static final int EDIT_MODE=3;
private static final int DELETE_MODE=4;
private static final int EXPORT_TO_PDF_MODE=5;
private int mode;
private JLabel titleLabel;
private JLabel searchLabel;
private JTextField searchTextField;
private JButton clearSearchTextFieldButton;
private JLabel searchErrorLabel;
private JTable authorsTable;
private JScrollPane authorsTableScrollPane;
private AuthorModel authorModel;
private AuthorCRUDPanel authorCRUDPanel;
public AuthorPanel()
{
initComponents();
setAppearance();
addListeners();
this.setViewMode();
authorCRUDPanel.setViewMode();
}
private void initComponents()
{
clearIcon=new ImageIcon(this.getClass().getResource("clearIcon.png"));
authorModel=new AuthorModel();
titleLabel=new JLabel("Authors");
searchLabel=new JLabel("Search");
searchTextField=new JTextField();
clearSearchTextFieldButton=new JButton(clearIcon);
searchErrorLabel=new JLabel("");
authorsTable=new JTable(authorModel);
authorsTable.getColumnModel().getColumn(0).setPreferredWidth(50);
authorsTable.getColumnModel().getColumn(1).setPreferredWidth(450);
authorsTable.setRowHeight(30);
java.awt.Font tableHeaderFont=new java.awt.Font("Verdana",java.awt.Font.BOLD,14);
authorsTable.getTableHeader().setFont(tableHeaderFont);
authorsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
authorsTableScrollPane=new JScrollPane(authorsTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
authorCRUDPanel=new AuthorCRUDPanel();
setLayout(null);
int lm=0;
int tm=0;
titleLabel.setBounds(lm+5,tm+5,100,40);
searchErrorLabel.setBounds(lm+5+50+5+350-60,tm+5+40-15,60,20);
searchLabel.setBounds(lm+5,tm+5+40+5,50,30);
searchTextField.setBounds(lm+5+50+5,tm+5+40+5,350,30);
clearSearchTextFieldButton.setBounds(lm+5+50+5+350+2,tm+5+40+5,30,30);
authorsTableScrollPane.setBounds(lm+5,tm+5+40+5+30+5,465,300);authorsTableScrollPane.setBounds(lm+5,tm+5+40+5+30+5,465,300);
authorCRUDPanel.setBounds(lm+5,tm+5+40+5+30+5+305,465,200);
//authorCRUDPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
}
private void setAppearance()
{
java.awt.Font titleFont=new java.awt.Font("Verdana",java.awt.Font.BOLD,18);
java.awt.Font font=new java.awt.Font("Verdana",java.awt.Font.PLAIN,14);
java.awt.Font errorFont=new java.awt.Font("Verdana",java.awt.Font.BOLD,10);
titleLabel.setFont(titleFont);
searchErrorLabel.setFont(errorFont);
searchErrorLabel.setForeground(Color.red);
searchLabel.setFont(font);
searchTextField.setFont(font);
authorsTable.setFont(font);
add(titleLabel);
add(searchErrorLabel);
add(searchLabel);
add(searchTextField);
add(clearSearchTextFieldButton);
add(authorsTableScrollPane);
add(authorCRUDPanel);
}
private void addListeners()
{
authorsTable.getSelectionModel().addListSelectionListener(this);
searchTextField.getDocument().addDocumentListener(this);
clearSearchTextFieldButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
searchTextField.setText("");
searchTextField.requestFocus();
}
});
}
private void search()
{
searchErrorLabel.setText("");
String leftPartOfNameToSearch=searchTextField.getText().trim();
if(leftPartOfNameToSearch.length()==0) return;
try
{
int index=authorModel.getAuthorIndexByName(leftPartOfNameToSearch,true,true);
authorsTable.setRowSelectionInterval(index,index);
authorsTable.scrollRectToVisible(new java.awt.Rectangle(authorsTable.getCellRect(index,0,true)));
}catch(BLException e)
{
searchErrorLabel.setText("Not found");
}
}
public void changedUpdate(DocumentEvent ev)
{
search();
}
public void insertUpdate(DocumentEvent ev)
{
search();
}
public void removeUpdate(DocumentEvent ev)
{
search();
}
public void setViewMode()
{
this.mode=VIEW_MODE;
if(authorModel.getRowCount()==0)
{
searchTextField.setText("");
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
authorsTable.setEnabled(false);
}
else
{
searchTextField.setEnabled(true);
clearSearchTextFieldButton.setEnabled(true);
authorsTable.setEnabled(true);
}
}
public void setAddMode()
{
this.mode=ADD_MODE;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
authorsTable.setEnabled(false);
}
public void setEditMode()
{
this.mode=EDIT_MODE;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
authorsTable.setEnabled(false);
}
public void setDeleteMode()
{
this.mode=DELETE_MODE;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
authorsTable.setEnabled(false);
}
public void setExportToPDFMode()
{
this.mode=EXPORT_TO_PDF_MODE;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
authorsTable.setEnabled(false);
}
public void valueChanged(ListSelectionEvent ev)
{
int selectedRow=authorsTable.getSelectedRow();
if(selectedRow<0 || selectedRow>=authorModel.getRowCount())
{
authorCRUDPanel.clear();
return;
}
try
{
AuthorInterface selectedAuthor=authorModel.getAuthorAt(selectedRow);
authorCRUDPanel.setAuthor(selectedAuthor);
}catch(BLException blException)
{
authorCRUDPanel.clear();
}
}
// inner class
class AuthorCRUDPanel extends JPanel implements ActionListener
{
private ImageIcon addIcon;
private ImageIcon editIcon;
private ImageIcon deleteIcon;
private ImageIcon cancelIcon;
private ImageIcon pdfIcon;
private ImageIcon saveIcon;
private JLabel authorCaptionLabel;
private JLabel authorLabel;
private JTextField authorTextField;
private JButton clearAuthorTextFieldButton;
private JButton addButton;
private JButton editButton;
private JButton cancelButton;
private JButton deleteButton;
private JButton exportToPDFButton;
private JPanel buttonsPanel;
private AuthorInterface author;
public AuthorCRUDPanel()
{
initComponents();
setAppearance();
addListeners();
}
private void initComponents()
{
addIcon=new ImageIcon(AuthorPanel.this.getClass().getResource("addIcon.png"));
editIcon=new ImageIcon(AuthorPanel.this.getClass().getResource("editIcon.png"));
deleteIcon=new ImageIcon(AuthorPanel.this.getClass().getResource("deleteIcon.png"));
saveIcon=new ImageIcon(AuthorPanel.this.getClass().getResource("saveIcon.png"));
cancelIcon=new ImageIcon(AuthorPanel.this.getClass().getResource("cancelIcon.png"));
pdfIcon=new ImageIcon(AuthorPanel.this.getClass().getResource("pdfIcon.png"));
authorCaptionLabel=new JLabel("Author : ");
authorLabel=new JLabel("");
authorTextField=new JTextField();
clearAuthorTextFieldButton=new JButton(clearIcon);
addButton=new JButton(addIcon);
editButton=new JButton(editIcon);
cancelButton=new JButton(cancelIcon);
deleteButton=new JButton(deleteIcon);
exportToPDFButton=new JButton(pdfIcon);
buttonsPanel=new JPanel();
buttonsPanel.setLayout(null);
}
private void setAppearance()
{
java.awt.Font font=new java.awt.Font("Verdana",java.awt.Font.PLAIN,14);
authorCaptionLabel.setFont(font);
authorLabel.setFont(font);
authorTextField.setFont(font);
setLayout(null);
int lm,tm;
lm=0;
tm=5;
authorCaptionLabel.setBounds(lm+5,tm+5,65,30);
authorLabel.setBounds(lm+5+65+5,tm+5,300,30);
authorTextField.setBounds(lm+5+65+5,tm+5,350,30);
clearAuthorTextFieldButton.setBounds(lm+5+65+5+350+2,tm+5,30,30);
buttonsPanel.setBounds(465/2-310/2,tm+5+30+40,310,70);
addButton.setBounds(10,10,50,50);
editButton.setBounds(70,10,50,50);
cancelButton.setBounds(130,10,50,50);
deleteButton.setBounds(190,10,50,50);
exportToPDFButton.setBounds(250,10,50,50);
buttonsPanel.add(addButton);
buttonsPanel.add(editButton);
buttonsPanel.add(cancelButton);
buttonsPanel.add(deleteButton);
buttonsPanel.add(exportToPDFButton);
buttonsPanel.setBorder(BorderFactory.createLineBorder(new Color(122,138,153)));
add(authorCaptionLabel);
add(authorLabel);
add(authorTextField);
add(clearAuthorTextFieldButton);
add(buttonsPanel);
}
private void addListeners()
{
addButton.addActionListener(this);
editButton.addActionListener(this);
deleteButton.addActionListener(this);
cancelButton.addActionListener(this);
exportToPDFButton.addActionListener(this);
}
public void setViewMode()
{
authorTextField.setVisible(false);
clearAuthorTextFieldButton.setVisible(false);
authorLabel.setVisible(true);
addButton.setEnabled(true);
cancelButton.setEnabled(false);
addButton.setIcon(addIcon);
editButton.setIcon(editIcon);
if(authorModel.getRowCount()==0)
{
editButton.setEnabled(false);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
}
else
{
editButton.setEnabled(true);
deleteButton.setEnabled(true);
exportToPDFButton.setEnabled(true);
}
}
public void setAddMode()
{
authorTextField.setText("");
authorLabel.setVisible(false);
authorTextField.setVisible(true);
clearAuthorTextFieldButton.setVisible(true);
addButton.setEnabled(true);
cancelButton.setEnabled(true);
addButton.setIcon(saveIcon);
editButton.setEnabled(false);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
}
public void setEditMode()
{
authorTextField.setText(author.getName());
authorLabel.setVisible(false);
authorTextField.setVisible(true);
clearAuthorTextFieldButton.setVisible(true);
addButton.setEnabled(false);
cancelButton.setEnabled(true);
editButton.setIcon(saveIcon);
editButton.setEnabled(true);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
}
public void setDeleteMode()
{
addButton.setEnabled(false);
cancelButton.setEnabled(false);
editButton.setEnabled(false);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
}
public void setExportToPDFMode()
{
addButton.setEnabled(false);
cancelButton.setEnabled(false);
editButton.setEnabled(false);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
}
public void setAuthor(AuthorInterface author)
{
this.author=author;
this.authorLabel.setText(author.getName());
}
public void clear()
{
this.author=null;
this.authorLabel.setText("");
}
public void actionPerformed(ActionEvent ev)
{
if(ev.getSource()==addButton)
{
if(AuthorPanel.this.mode==VIEW_MODE)
{
// logic to put in add mode
AuthorPanel.this.setAddMode();
this.setAddMode();
}
else
{
// logic to add and go back to view mode if the record gets added
String name=authorTextField.getText().trim();
try
{
AuthorInterface newAuthor=new Author();
newAuthor.setName(name);
authorModel.add(newAuthor);
int index=authorModel.getIndexOf(newAuthor);
authorsTable.setRowSelectionInterval(index,index);
authorsTable.scrollRectToVisible(new java.awt.Rectangle(authorsTable.getCellRect(index,0,true)));
AuthorPanel.this.setViewMode();
this.setViewMode();
}catch(BLException blException)
{
JOptionPane.showMessageDialog(AuthorPanel.this,blException.getMessage());
}
}
}else 
if(ev.getSource()==editButton)
{
if(AuthorPanel.this.mode==VIEW_MODE)
{
int selectedRow=authorsTable.getSelectedRow();
if(selectedRow<0 || selectedRow>=authorModel.getRowCount())
{
JOptionPane.showMessageDialog(AuthorPanel.this,"Select author to edit");
return;
}
AuthorPanel.this.setEditMode();
this.setEditMode();
}
else
{
// logic to update and put back in view mode
String name=authorTextField.getText().trim();
try
{
AuthorInterface newAuthor=new Author();
newAuthor.setCode(author.getCode());
newAuthor.setName(name);
authorModel.update(newAuthor);
int index=authorModel.getIndexOf(newAuthor);
authorsTable.setRowSelectionInterval(index,index);
authorsTable.scrollRectToVisible(new java.awt.Rectangle(authorsTable.getCellRect(index,0,true)));
AuthorPanel.this.setViewMode();
this.setViewMode();
}catch(BLException blException)
{
JOptionPane.showMessageDialog(AuthorPanel.this,blException.getMessage());
}
}
}else
if(ev.getSource()==deleteButton)
{

int selectedRow=authorsTable.getSelectedRow();
if(selectedRow<0 || selectedRow>=authorModel.getRowCount())
{
JOptionPane.showMessageDialog(AuthorPanel.this,"Select author to delete");
return;
}
AuthorPanel.this.setDeleteMode();
this.setDeleteMode();
int selectedOption=JOptionPane.showConfirmDialog(AuthorPanel.this,"Delete : "+this.author.getName(),"Confirmation",JOptionPane.YES_NO_OPTION);
if(selectedOption==JOptionPane.NO_OPTION)
{
AuthorPanel.this.setViewMode();
this.setViewMode();
return;
}
try
{
authorModel.remove(this.author.getCode());
}catch(BLException blException)
{
JOptionPane.showMessageDialog(AuthorPanel.this,blException.getMessage());
}
AuthorPanel.this.setViewMode();
this.setViewMode();
}else
if(ev.getSource()==cancelButton)
{
AuthorPanel.this.setViewMode();
this.setViewMode();
}else 
if(ev.getSource()==exportToPDFButton)
{
AuthorPanel.this.setExportToPDFMode();
this.setExportToPDFMode();
JFileChooser jfc=new JFileChooser();
jfc.setCurrentDirectory(new File("."));
jfc.setAcceptAllFileFilterUsed(false);
jfc.setFileFilter(new FileNameExtensionFilter("PDF Files","pdf"));
int selectedOption=jfc.showSaveDialog(AuthorPanel.this);
if(selectedOption==jfc.APPROVE_OPTION)
{
File selectedFile=jfc.getSelectedFile();
File parentFolder=new File(selectedFile.getParent());
if(parentFolder.exists()==false)
{
JOptionPane.showMessageDialog(AuthorPanel.this,"Invalid path : "+parentFolder.getAbsolutePath());
return;
}
String selectedFileNameWithPath=selectedFile.getAbsolutePath();
if(selectedFileNameWithPath.endsWith(".pdf")==false)
{
if(selectedFileNameWithPath.endsWith("."))
{
selectedFileNameWithPath+="pdf";
}
else
{
selectedFileNameWithPath+=".pdf";
}
}
String fullPath=selectedFileNameWithPath;
// logic to generate pdf starts
try
{
Document document=new Document();
PdfWriter.getInstance(document,new FileOutputStream(new File(fullPath)));
document.open();
com.itextpdf.text.Font font=new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN,24,com.itextpdf.text.Font.BOLD);
Paragraph paragraph;
float widths[]={1f,5f};
PdfPTable table=null;
com.itextpdf.text.Image img;
PdfPCell cell=null;
java.text.SimpleDateFormat sdf;
sdf=new java.text.SimpleDateFormat("dd/MM/yy (hh:mm:ss)");
java.util.Date today=new java.util.Date();
String todayString=sdf.format(today);
int pageSize=6;  // later on change it to 40 or whatever
boolean newPage=true;
int pageNumber=0;
int x;
x=0;
AuthorInterface authorInterface;
while(x<authorModel.getRowCount())
{
if(newPage==true)
{
pageNumber++;
// header part starts
paragraph=new Paragraph("ABC Book Store",font);
paragraph.setAlignment(Element.ALIGN_CENTER);
document.add(paragraph);
paragraph=new Paragraph("Authors");
paragraph.setAlignment(Element.ALIGN_CENTER);
document.add(paragraph);
paragraph=new Paragraph("Page No. "+pageNumber);
paragraph.setAlignment(Element.ALIGN_RIGHT);
document.add(paragraph);
document.add(new Paragraph(" "));
img=com.itextpdf.text.Image.getInstance(AuthorPanel.this.getClass().getResource("logo.png"));
img.setAbsolutePosition(50,760);
document.add(img);
table=new PdfPTable(2);
table.setWidths(widths);
cell=new PdfPCell(new Paragraph("S.No."));
cell.setBackgroundColor(BaseColor.GRAY);
table.addCell(cell);
cell=new PdfPCell(new Paragraph("Authors"));
cell.setBackgroundColor(BaseColor.GRAY);
table.addCell(cell);
// header part ends
}
newPage=false;
authorInterface=authorModel.getAuthorAt(x);
cell=new PdfPCell(new Paragraph(String.valueOf(x+1)));
table.addCell(cell);
cell=new PdfPCell(new Paragraph(authorInterface.getName()));
table.addCell(cell);
x++;
if(x%pageSize==0 || x==authorModel.getRowCount())
{
// footer start
document.add(table);
paragraph=new Paragraph("Generated on : "+todayString);
document.add(paragraph);
paragraph=new Paragraph("Software by : Thinking Machines");
document.add(paragraph);
// footer ends
if(x<authorModel.getRowCount())
{
document.newPage();
newPage=true;
}
}
}
document.close();
JOptionPane.showMessageDialog(AuthorPanel.this,"Pdf file : "+fullPath+" created..");
}catch(Exception exception)
{
System.out.println(exception);
JOptionPane.showMessageDialog(AuthorPanel.this,"Unable to created pdf file : "+fullPath);
}
// logic to generate PDF ends
}
AuthorPanel.this.setViewMode();
this.setViewMode();
}
}
} // inner class ends
}
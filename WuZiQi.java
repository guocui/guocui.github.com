package 五子棋;
import java.applet.*; 
import java.awt.*; 
import java.awt.event.*; 
import java.applet.Applet; 
import java.awt.Color; 
import java.io.*;
import java.net.*;
import javax.swing.*;
public class WuZiQi extends JApplet implements ActionListener
,MouseListener,MouseMotionListener,ItemListener,Runnable{	
int color_Qizi=0;//   黑子0   白子1	
int ChessBody[][]=new int[17][17];//无棋子 0，黑棋 1，白棋 2;	
int GameStart=0;	
JButton start,exit,rest;	
JButton check[]=new JButton[3];	
String chess[]={"黑方","白方","旁观"};
JTextArea txtDisplay;	
JTextField txtInput;
DataInputStream in;
 DataOutputStream out;	
public void init(){
setLayout(null);	 start=new JButton("开始");	 rest=new JButton("重置");	 exit=new JButton("退出");	 
Panel p1=new Panel();
p1.add(start);  p1.add(rest);
p1.add(exit);
add(p1);   p1.setBounds(130,510,300,50);
Panel p2=new Panel();
for(int i=0;i<chess.length;i++)	 {check[i]=new JButton(chess[i]);
p2.add(check[i]); }
add(p2);   p2.setBounds(600,50,200,50);
for(int i=0;i<chess.length;i++)
 {check[i].addActionListener(this); }
start.addActionListener(this);
exit.addActionListener(this);
rest.addActionListener(this);
addMouseListener(this);
txtDisplay = new JTextArea(10,50);
txtDisplay.setEditable(false);
txtInput = new JTextField(10);
    Panel chat=new Panel();
    chat.setLayout(new BorderLayout());
chat.add(BorderLayout.CENTER,new JScrollPane(txtDisplay));
chat.add(BorderLayout.SOUTH,txtInput);
    add(chat);chat.setBounds(900, 300, 350, 300);
txtInput.addActionListener(//对输入文本框地方注册事件监听
new ActionListener() {
public void actionPerformed(ActionEvent event) {
String str = txtInput.getText();
txtInput.setText("");	
try {
out.writeUTF(str);
}catch(Exception e) {}}
});
in = null;  out = null;
初始化网络，并连接到服务端
try {//获得URL
URL url = this.getCodeBase；
InetAddress addr = InetAddress.getByName(url.getHost());
Socket socket;	
System.out.println ("Server: "+addr+" "+url.getHost()+" "+url.getProtocol());	
//5555 为服务器端口	
socket = new Socket(addr,5555);//由本套接口获得输入，输出流
in = new DataInputStream(socket.getInputStream());
out = new DataOutputStream(socket.getOutputStream());
}catch (Exception ex) {ex.printStackTrace(); 
}	
new Thread(this).start();//线程开始
}	
public void actionPerformed(ActionEvent e){
Graphics g=getGraphics();
if(e.getSource()==start){
GameStart();}
if(e.getSource()==rest){	
GameReSt();}
if(e.getSource()==exit){
GameEnd();	
if(e.getSource()==check[0])	   color_Qizi=0;
}	  public void mousePressed(MouseEvent e){}	  public void mouseClicked(MouseEvent e){//获取下棋位点	
  int x,y;	  x=e.getX();y=e.getY();	  if(x<45||y<45||x>495||y>495)	  {return;}	  if(x%30>15)//获取下棋点的位置	  {x+=30;}	  if(y%30>15)	      {y+=30;}	  x=x/30*30; y=y/30*30;	   PlayQizi(x,y);//下棋
  }	  public void mouseEntered(MouseEvent e){} 	  public void mouseExited(MouseEvent e){} 	  public void mouseReleased(MouseEvent e){} 	  public void mouseDragged(MouseEvent e){} 	  public void mouseMoved(MouseEvent e){} 	  public void paint(Graphics g){
QiPan(g);
}	  public void PlayQizi(int x,int y){	 if (GameStart==0) //判断游戏未开始 { return; } 
if (ChessBody[x/30][y/30]!=0) {return;}	   Graphics g=getGraphics(); 	
if (color_Qizi==1)//判断黑子还是白子 	     {g.setColor(Color.black); 	       color_Qizi=0;} 
else    {g.setColor(Color.white); 	          color_Qizi=1;        
} 
g.fillOval(x-12,y-12,24,24); 
ChessBody[x/30][y/30]=color_Qizi+1; 
///判断输赢//////	   g.setColor(Color.magenta);	   g.setFont(new Font("fhahf ",Font.BOLD,30));
if(GameWin横(x/30,y/30))
{g.drawString(GetQiziColor(color_Qizi)+"赢了!",200,200);GameStart=0;	
}	
if(GameWin竖(x/30,y/30)){	
g.drawString(GetQiziColor(color_Qizi)+"赢了!",200,200);GameStart=0;	
}	
if(GameWin撇(x/30,y/30)){	
g.drawString(GetQiziColor(color_Qizi)+"赢了!",200,200); GameStart=0;	
}	
if(GameWin捺(x/30,y/30)){	
g.drawString(GetQiziColor(color_Qizi)+"赢了!",200,200);	
GameStart=0;
  }
 }//////////////获得赢方//////////////////////////  	  public String GetQiziColor(int x) 
   {  if(x==0  )return "黑方";else return "白方"; } 	    //////////////判断输赢////////////////////////////////////	  public boolean GameWin横(int x,int y){	  int x1,y1;int t=1;	  x1=x;y1=y;	  for(int i=1;i<5;i++)	  {if(x1>16)break;	  if(ChessBody[x1+i][y1]==ChessBody[x][y])  t+=1;	   else break;	  
 }	  for(int i=1;i<5;i++)	  
{if(x1<2)break;	  if(ChessBody[x1-i][y1]==ChessBody[x][y])t+=1;	 
else break;	  
  }	  if(t>4)  return true;	   else  return   false;	    
}	  public boolean GameWin竖(int x,int y){	  
int x1,y1;int t=1;x1=x;y1=y; 
  for(int i=1;i<5;i++)	  {if(x1>16)break;	  if(ChessBody[x1][y1+i]==ChessBody[x][y])    t+=1;	      else break;
  }
   for(int i=1;i<5;i++)	  {if(x1<2)break;	  if(ChessBody[x1][y1-i]==ChessBody[x][y]) t+=1;	   else break;	  
  }	  if(t>4)	  return true;	  else  return false;	      
  }	  public boolean GameWin撇(int x,int y){	  
int x1,y1;int t=1;	  x1=x;y1=y;	  for(int i=1;i<5;i++)	  {if(x1>16)break;	  if(ChessBody[x1+i][y1-i]==ChessBody[x][y])t+=1; 	   else break;	  
 }	  for(int i=1;i<5;i++)	  {if(x1<2)break ;	  if(ChessBody[x1-i][y1+i]==ChessBody[x][y])  t+=1;	  else break;
  }	  if(t>4)return true;	     else  return false;
  }	  public boolean GameWin捺(int x,int y){	  
int x1,y1;int t=1;x1=x;y1=y;	   
for(int i=1;i<5;i++)	  {	  if(x1>16)break;	  if(ChessBody[x1-i][y1-i]==ChessBody[x][y]) t+=1; 	      else break;
 }	  for(int i=1;i<5;i++)	  {if(x1<2)break;	  if(ChessBody[x1+i][y1+i]==ChessBody[x][y])   t+=1;	    else break;
 }	  if(t>4) turn true;	    else  eturn false;	  
}	  public void GameStart()//游戏开始	  {GameStart=1;	  Game_btn_enable(false);	  rest.setEnabled(true);	
}	  public void Game_btn_enable(boolean e){	  start.setEnabled(e);rest.setEnabled(e);	  for(int i=0;i<check.length;i++)	  { check[i].setEnabled(e);}
 }	  public void GameStartInit(){//初始化	  GameStart=0;	  Game_btn_enable(true);	  start.setEnabled(true);	  for(int i=0;i<17;i++)	  for(int j=0;j<17;j++)	  {ChessBody[i][j]=0;}	
}	  public void GameReSt(){//重置	  repaint();	  GameStartInit();
}	      public void QiPan(Graphics g){//画棋盘   	
g.setColor(Color.cyan); 	
int m=45;int n=m+15;
G.fillRect(m,m,450,450);	
g.setColor(Color.black);	
for(int i=0;i<15;i++)	
g.drawLine(n,n+i*30,n+420,n+i*30);	
for(int i=0;i<15;i++)  	
g.drawLine(n+i*30,n,n+i*30,n+420);	
g.fillRect(n+3*30-3,n+3*30-3,6,6);    	
g.fillRect(n+3*30-3,n+11*30-3,6,6); 	
g.fillRect(n+11*30-3,n+3*30-3,6,6);  	
g.fillRect(n+11*30-3,n+11*30-3,6,6); 	
g.fillRect(n+7*30-3,n+7*30-3,6,6); 	
    }
     /////////////////////////运行监听方法//////////////////////////
     public void run() {	
try {//监视服务器所发送的信息	
while(true) {   	
String receive = in.readUTF();	  if(receive!=null){	   txtDisplay.append(receive + "\n");	      //将光标移动到最后一个以实现滚动条的自动置底	   txtDisplay.setCaretPosition(txtDisplay.getText().length());
      }
 }	
}catch (Exception ex) {	   txtDisplay.append("Network problem or Server down");	    txtInput.setVisible(false);}	
}	
public void stop() 	
try {	
out.writeUTF("leave");	
}catch (Exception ex) {}
    }	
}
   /*ChatServer类*/
import java.net.*;
import java.io.*;
import java.util.*;
public class ChatServer {
	public static void main(String[] args)throws Exception{
		ServerSocket svSocket =null;
		//Vector threads 为ServerThread集合
		Vector threads = new Vector();
		//开始监听
		System.out.println ("listening...");
		try {
		//创建服务端套接口
		svSocket = new ServerSocket(5555);
		}catch (Exception ex) {
		System.out.println ("Server create ServerSocket failed!");
		return;
		}
		try{
		int nid = 0;
		//监听是否有客户端连接
		while(true){
		Socket socket = svSocket.accept();
		System.out.println ("accept a client");
		//创建一个新的ServerThread
		ServerThread st = new ServerThread(socket,threads);
		//为客户端分配一个ID号
		st.setID(nid++);
		threads.add(st);
		new Thread(st).start();
		//向所有人广播有新客户端连接
		for(int i=0;i < threads.size();i++){
		ServerThread temp = (ServerThread)threads.elementAt(i);
		st.write("<#>Welcome "+ temp.getID()+" to enter the chatroom");
		}
		System.out.println ("Listen again...");
		}
		}catch(Exception ex){
		System.out.println ("server is down");}
		}
		}
		/* 监听线程，监听是否有客户端发消息过来*/
		class ServerThread implements Runnable{
		private Vector threads;
		private Socket socket = null;
		private DataInputStream in = null;
		private DataOutputStream out = null;
		private int nid;
		//构造器
		public ServerThread(Socket socket,Vector threads){
		this.socket = socket;
		this.threads = threads;
		try {
		in = new DataInputStream(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());
		}
		catch (Exception ex) {}
		}
		//实现run方法
		public void run(){
		System.out.println ("Thread is running");
		try{
		//监听客户端是否发消息过来
		while(true){
		String receive = in.readUTF();
		if(receive == null)
		return;
		//当某客户离开，给其它客户端发消息
		if(receive.equals("leave")){
		for(int i=0;i < threads.size();i++){
		ServerThread st = (ServerThread)threads.elementAt(i);
		st.write("***"+getID()+"leaving...***");
		}
		}else{
		//把某客户端发过来的发送到所有客户端
		for(int i=0;i < threads.size();i++){
		ServerThread st = (ServerThread)threads.elementAt(i);
		st.write("<"+getID()+">: "+receive);}}
		}
		}catch(Exception ex){
		//从Vector中删除该线程，表示该线程已经离开聊天室
		threads.removeElement(this);
		//ex.printStackTrace();
		}
		try{
		socket.close();
		}catch(Exception ex){
		ex.printStackTrace();}
		}
		/* 服务端向客户端发送信息*/
		public void write(String msg){
		synchronized(out){
		try{
		out.writeUTF(msg);
		}catch(Exception ex){
		}}
		}
		/* 获得线程ID号*/
		public int getID(){
		return this.nid;
		}
		/** 设置线程ID号*/
		public void setID(int nid){
		this.nid = nid;
		}
}


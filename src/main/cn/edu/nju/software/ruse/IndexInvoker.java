package cn.edu.nju.software.ruse;

public class IndexInvoker {
   private Command cmd;
   public void setCommand(Command cmd){
	   this.cmd=cmd;
   }
   public void doIndex(){
	   cmd.execute();
   }
}

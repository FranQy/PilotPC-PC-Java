import com.example.socketclient.TCP_Data;


public class Pilot {
	
	  static {
	      System.loadLibrary("clicker"); // hello.dll (Windows) or libhello.so (Unixes)
	   }
	   // A native method that receives nothing and returns void
	   private native void click(int i);
	   
	   public Pilot()
	   {
		   
	   }
	   public void click(TCP_Data data)
	   {
		   switch(data.button)
		   {
		   case VOLDOWN:
		   {
			   click(0);
			   break;
		   }
		   case VOLUP:
		   {
			   click(1);
			   break;
		   }
		   case MUTE:
		   {
			   click(2);
			   break;
		   }
		   }
		   data.clean();
	   }

}

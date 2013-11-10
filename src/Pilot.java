import com.example.socketclient.TCP_Data;


public class Pilot {
	
	static {
		 if(System.getenv("PROCESSOR_ARCHITECTURE").contentEquals("AMD64"))
	        System.loadLibrary("libpilotpc-x64");
		 else
		        System.loadLibrary("libpilotpc");
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
			   click(data.button.ordinal());//Korzysta z tego samego enuma co TCP_DATA, bo po co robic dwa osobne
			   break;
		   }
		   case VOLUP:
		   {
			   click(data.button.ordinal());
			   break;
		   }
		   case MUTE:
		   {
			   click(data.button.ordinal());
			   break;
		   }
		   }
		   data.clean();
	   }

}

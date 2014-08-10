class FinallyAfterReturn
{
    public static void main(String args[]) 
    { 
        // call the proveIt method and print the return value
    	System.out.println(FinallyAfterReturn.proveIt()); 
    }

    public static int proveIt()
    {
    	try {  
            	return 1;  
    	}  
    	finally {  
    	    System.out.println("finally block is run before method returns.");
    	}
    }
}

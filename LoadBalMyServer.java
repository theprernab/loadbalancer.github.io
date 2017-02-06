import javax.print.DocFlavor;
import java.io.*;
import java.net.ServerSocket;
import java.net.*;
/*
 * Created by jayant on 1/7/17.
 */
public class LoadBalMyServer 
{
	public static void main(String[] args) throws Exception
	{
		ServerSocket server = new ServerSocket(1265);
		System.out.println("Listening on port 1265 ....");
		int i = 1;
		int serverChoose = 0;
		while (true)
		{

			Socket clientSocket = server.accept();
			try
			{

				BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                // DataOutputStream outToClient = new DataOutputStream(clientSocket.getOutputStream());
				PrintWriter outToClient = new PrintWriter(clientSocket.getOutputStream(), true);
				String line="";
				String line1 = "";
				line1 = reader.readLine();
				System.out.println(line1);

				while(true){

					line = reader.readLine();
					if(line.isEmpty()) break;
					System.out.println(line);
				}
				int end = line1.indexOf("HTTP");
				String fileName = line1.substring(4,end-1);
				System.out.println(fileName + " <-------- ");
                String[] servers= {"127.0.0.1","127.0.0.1"}; // temporarily changing
                // String[] servers= {"10.0.0.2","10.0.0.3"}; // temporarily changing
                if(serverChoose%2 == 0)
                {
                	Socket socket1 = new Socket(servers[serverChoose%2], 1267);
                	 PrintWriter out = new PrintWriter(socket1.getOutputStream(), true);
     					BufferedReader in = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
                	System.out.println("server 1");
                	String serveraddr = "";
                	serveraddr = servers[serverChoose%2];
                	int portNo = 1267;
                	serverChoose++;
                	serveraddr += fileName;
                	out.println(fileName);
                	 String recv = in.readLine();
                	 System.out.println("Text received: " + recv);

                   //out.println(fileName);
                	// outToClient.write("server 1");
                	 outToClient.println(recv);
                	System.out.println(serveraddr); 
                	socket1.close();
                	clientSocket.close();

                }
                else
                {
                	Socket socket2 = new Socket(servers[serverChoose%2], 1268);
                	PrintWriter   out2 = new PrintWriter(socket2.getOutputStream(), true);
                	BufferedReader  in2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
   	                	System.out.println("server 1");

                	String serveraddr = "";
                	serveraddr = servers[serverChoose%2];
                	int portNo = 1268;
                	serverChoose++;
                	serveraddr += fileName;
                	out2.println(fileName);
                	 String recv2 = in2.readLine();
                	 System.out.println("Text received: " + recv2);

                   //out.println(fileName);
                	
                	// outToClient.write("server 2");
                	outToClient.println(recv2);

                	System.out.println(serveraddr); 
                	socket2.close();
                	clientSocket.close();
                }

                // int x1 = fileName.lastIndexOf("/");
                // int x2 = fileName.indexOf("/");
/*                if (x1 == x2)
                {
                    fileName = fileName.substring(1);
                }
                File file = new File(fileName);
                int numOfBytes = (int) file.length();
                FileInputStream inFile = new FileInputStream (fileName);
                byte[] fileInBytes = new byte[numOfBytes];
// */                
                // inFile.read(fileInBytes);
/*                outToClient.writeBytes("HTTP/1.0 200 Document Follows\r\n\r\n");
                outToClient.write(fileInBytes, 0, numOfBytes);
*/                clientSocket.close();

            }
            catch(Exception e)
            {
            	System.out.printf("File not found");
            	e.printStackTrace();
            	clientSocket.close();
            }


        }
    }
}

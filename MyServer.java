import javax.print.DocFlavor;
import java.io.*;
import java.net.ServerSocket;
import java.net.*;
/**
 * Created by jayant on 1/7/17.
 */
public class MyServer {
    public static void main(String[] args) throws Exception
    {
        ServerSocket server = new ServerSocket(1265);
        System.out.println("Listening on port 1265 ....");
        int i = 1;
        while (true)
        {

            Socket clientSocket = server.accept();
            try
            {

                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(clientSocket.getOutputStream());
                String line="";
                while(true){

                    line = reader.readLine();
                    if(line.isEmpty()) break;
                    System.out.println(line);
                }
                int end = line.indexOf("HTTP");
                String fileName = line.substring(4,end-1);
                System.out.println(fileName);
                int x1 = fileName.lastIndexOf("/");
                int x2 = fileName.indexOf("/");
                if (x1 == x2)
                {
                    fileName = fileName.substring(1);
                }
                File file = new File(fileName);
                int numOfBytes = (int) file.length();
                FileInputStream inFile = new FileInputStream (fileName);
                byte[] fileInBytes = new byte[numOfBytes];
                inFile.read(fileInBytes);
                outToClient.writeBytes("HTTP/1.0 200 Document Follows\r\n\r\n");
                outToClient.write(fileInBytes, 0, numOfBytes);
                clientSocket.close();

            }
            catch(Exception e)
            {
                System.out.printf("File not found");
                clientSocket.close();
            }


        }
    }
}

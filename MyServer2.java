import javax.print.DocFlavor;
import java.io.*;
import java.net.ServerSocket;
import java.net.*;

public class MyServer2 
{
    public static void main(String[] args) throws Exception
    {
        ServerSocket server = new ServerSocket(1268);
        System.out.println("Listening on port 1268 ....");
        int i = 1;
        int serverChoose = 0;
        while (true)
        {

            Socket clientSocket = server.accept();
            try
            {

                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(clientSocket.getOutputStream());
                PrintWriter   out = new PrintWriter(clientSocket.getOutputStream(), true);
                 String line="";
                String line1 = "";
                line1 = reader.readLine();
                System.out.println(line1);

                int x1 = line1.indexOf("=");
                int x2 = line1.indexOf("&");
                String s1=line1.substring(x1+1,x2);
                x1=line1.lastIndexOf("=");
                int l=line1.length();
                String s2=line1.substring(x1+1,l);
                //System.out.println(x1+"  "+l+" "+s1+" "+s2);
                int num1=Integer.parseInt(s1);
                int num2=Integer.parseInt(s2);
                System.out.println(num1+" "+num2);
                double n1=Math.log(num1);
                double n2=Math.log(num2);
                String tosend="";
                if(n1>0 && n2>0)
                {
                    double output=n1/n2;
                    tosend=Double.toString(output);
                }   
                else
                {
                     tosend="NaN";

                }
                String line="";
                String line1 = "";
                line1 = reader.readLine();
                System.out.println(line1);
                OutputStream os = clientSocket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);

                bw.write(tosend);
                System.out.println("Message sent to the client is "+tosend);
                bw.flush();
               
               //out.println("haan mil gya i m server 2");
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

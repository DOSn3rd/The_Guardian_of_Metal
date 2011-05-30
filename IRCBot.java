import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class IRCBot {
    public String nick = "The_Guardian";
    public String realname = "trololo";
    public String channel = "#snekabel";
    public String host = "irc.thg.se";
    public int port = 6667;
    public Socket socket = null;
    public BufferedReader fromRemote = null;
    public PrintWriter toRemote = null;

    public static void main(String[] args) {
    	new IRCBot();
    }
    public IRCBot() {
        System.out.println("Init sequence started..");
        System.out.print("Creating socket...");
        socket = null;
        System.out.println("    [done]");
        System.out.print("Creating PrintWriter...");
        toRemote = null;
        System.out.println("    [done]");

        try {
            System.out.print("Trying to connect..");
            socket = new Socket(host, port);
            System.out.println("    [connected]");
            System.out.print("Making communication channels...");
            toRemote = new PrintWriter(socket.getOutputStream(), true);
            fromRemote = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("    [done]");
            System.out.print("Spawning printing thread...");
            printIncoming print = new printIncoming(fromRemote, toRemote);
            print.start();
            System.out.println("    [done]");
            
            print.sendRaw("NICK "+nick);
            print.sendRaw("USER "+realname+" 0 * :"+nick);
            Thread.sleep(3000);
            print.sendRaw("JOIN "+channel);
            System.out.println("Init completed.");
            
        }catch(Exception e){
            System.out.println("Error in init class: "+e);
            System.exit(1);
        }
     }
}

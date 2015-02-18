package Lib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Logger;

import pl.dom133.skyvote.SkyVote;

public class VersionChecker {
	private SkyVote plugin;
	
	public VersionChecker(SkyVote plugin)
	{
		this.plugin = plugin;
		currentVersion = plugin.getDescription().getVersion();
	}
	
	private String currentVersion;
	private String readurl = "https://rawgit.com/dom133/SkyVote/master/version.txt";
	
	public void checker()
	{
		if(Config.getConfig("config").getString("DataSource.versionchecker")==null)
		{
			Config.getConfig("config").set("DataSource.versionchecker", true);
			Config.save("config");
		}
		plugin.checker = Config.getConfig("config").getBoolean("DataSource.versionchecker");
	}
			
	
	public void startUpdateCheck() {
    	checker();
        if (plugin.checker==true) {
            Logger log = plugin.getLogger();
            try {
                log.info(plugin.messages.get("g32").toString());
                URL url = new URL(readurl);
                BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
                String str;
                while ((str = br.readLine()) != null) {
                	String line = str;
                	if(line.equals(currentVersion)==false)
                	{
                		log.info(plugin.updatemsg);
                	}
                }
                br.close();
            } catch (IOException e) {
                log.severe("The UpdateChecker URL is invalid! Please let me know!");
            }
        }
    }
	
	public String rawMessage(String url1) {
            try {
                URL url = new URL(url1);
                BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
                String str;
                while ((str = br.readLine()) != null) {
                	String line = str;
                	return line;
                }
                br.close();
            } catch (IOException e) {
            	return null;
            }
		return null;
    }
}

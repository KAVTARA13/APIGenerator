//import com.octopus.AutomatedBrowser;
//import com.octopus.decoratorbase.AutomatedBrowserBase;
import com.browserup.bup.BrowserUpProxy;
import com.browserup.bup.BrowserUpProxyServer;
import com.browserup.bup.proxy.dns.NativeResolver;
import com.browserup.harreader.model.HarEntry;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import static java.nio.channels.Selector.open;
import static java.nio.file.Paths.get;


public class BrowserMobDecorator {
    public static BrowserMobProxyServer proxy;
    public static void main(String[] args) {
        proxy = new BrowserMobProxyServer();
        proxy.setTrustAllServers(true);
        proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
        proxy.start(44);
        System.out.println(proxy.getPort());
        System.out.println(proxy.getClientBindAddress().getHostName());
        try {
            proxy.newHar();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Har har = proxy.getHar();
            System.out.println(har.getLog().toString());
            File harFile = new File("aaa.har");
            har.writeTo(harFile);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        proxy.stop();
    }
}

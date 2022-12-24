//import com.octopus.AutomatedBrowser;
//import com.octopus.decoratorbase.AutomatedBrowserBase;
import de.sstoehr.harreader.HarReaderException;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.filters.RequestFilter;
import net.lightbody.bmp.filters.ResponseFilter;
import net.lightbody.bmp.proxy.CaptureType;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;
import org.littleshoot.proxy.HttpFilters;
import org.littleshoot.proxy.HttpFiltersSource;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


public class BrowserMob {
    public static BrowserMobProxyServer proxy;
    public static void main(String[] args) {
        final String[] URL = {""};
        proxy = new BrowserMobProxyServer();
        proxy.setTrustAllServers(true);
        proxy.setHarCaptureTypes(CaptureType.getAllContentCaptureTypes());
        proxy.enableHarCaptureTypes(CaptureType.getAllContentCaptureTypes());
        proxy.addRequestFilter(new RequestFilter() {
            @Override
            public HttpResponse filterRequest(HttpRequest httpRequest, net.lightbody.bmp.util.HttpMessageContents httpMessageContents, net.lightbody.bmp.util.HttpMessageInfo httpMessageInfo) {
                if (Arrays.toString(httpRequest.headers().entries().toArray()).contains("User-Agent=Postman")){
                    if (httpRequest.getMethod().toString().equals("CONNECT")){
                        URL[0] = httpRequest.getUri().split(":")[0];
                    }else if(!URL[0].equals("")){
                        System.out.println("URI: "+URL[0]+httpRequest.getUri());
                        System.out.println("headers: "+Arrays.toString(httpRequest.headers().entries().toArray()));
                        System.out.println("result: "+httpRequest.getDecoderResult().toString());
                        System.out.println("method: "+httpRequest.getMethod().toString());
                        System.out.println(httpMessageContents.getTextContents());
                    }
                }
                return null;
            }
        });
        proxy.addResponseFilter(new ResponseFilter() {
            @Override
            public void filterResponse(HttpResponse httpResponse, HttpMessageContents httpMessageContents, HttpMessageInfo httpMessageInfo) {
                if (Arrays.toString(httpResponse.headers().entries().toArray()).contains(URL[0]) && !URL[0].equals("")){
                    System.out.println();
                    System.out.println("headers: "+Arrays.toString(httpResponse.headers().entries().toArray()));
                    System.out.println("getStatus: "+httpResponse.getStatus());
                    System.out.println("result: "+httpResponse.getDecoderResult().toString());
                    System.out.println(httpMessageContents.getTextContents());
                }
            }
        });
        proxy.start(44);
    }
}
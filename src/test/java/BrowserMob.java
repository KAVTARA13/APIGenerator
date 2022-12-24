//import com.octopus.AutomatedBrowser;
//import com.octopus.decoratorbase.AutomatedBrowserBase;
import de.sstoehr.harreader.HarReaderException;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.filters.RequestFilter;
import net.lightbody.bmp.proxy.CaptureType;
import org.littleshoot.proxy.HttpFilters;
import org.littleshoot.proxy.HttpFiltersSource;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;


public class BrowserMob {
    public static BrowserMobProxyServer proxy;
    public static void main(String[] args) {
        proxy = new BrowserMobProxyServer();
        proxy.setTrustAllServers(true);
        proxy.setHarCaptureTypes(CaptureType.getAllContentCaptureTypes());
        proxy.enableHarCaptureTypes(CaptureType.getAllContentCaptureTypes());
        proxy.newHar();
//        proxy.addHttpFilterFactory(new org.littleshoot.proxy.HttpFiltersSource(){
//            @Override
//            public HttpFilters filterRequest(HttpRequest httpRequest, ChannelHandlerContext channelHandlerContext) {
//                System.out.println("gaeshva");
//                return null;
//            }
//
//            @Override
//            public int getMaximumRequestBufferSizeInBytes() {
//                int maxBufferSize = 0;
//                for (HttpFiltersSource source : filterFactories) {
//                    int requestBufferSize = source.getMaximumRequestBufferSizeInBytes();
//                    if (requestBufferSize > maxBufferSize) {
//                        maxBufferSize = requestBufferSize;
//                    }
//                }
//                return maxBufferSize;
//            }
//
//            @Override
//            public int getMaximumResponseBufferSizeInBytes() {
//                return 0;
//            }
//        } );
        proxy.addRequestFilter(new RequestFilter() {
            @Override
            public HttpResponse filterRequest(HttpRequest httpRequest, net.lightbody.bmp.util.HttpMessageContents httpMessageContents, net.lightbody.bmp.util.HttpMessageInfo httpMessageInfo) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy_HH.mm.ss");
                Date date = new Date();
//                System.out.println(formatter.format(date));
                System.out.println("URI: "+httpRequest.getUri());
                System.out.println("result: "+httpRequest.getDecoderResult().toString());
                System.out.println("method: "+httpRequest.getMethod().toString());
                System.out.println("--------------------------------");
                System.out.println(httpMessageContents.getTextContents());
                System.out.println(Arrays.toString(httpMessageContents.getBinaryContents()));

//                Har har = proxy.getHar();
//                File harFile = new File(formatter.format(date)+".har");
//                try {
//                    har.writeTo(harFile);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
                return null;
            }
        });
        proxy.start(44);
//        HarReader harReader = new HarReader();
//        de.sstoehr.harreader.model.Har har = harReader.readFromFile(new File("aafga.har"));
//        System.out.println(har.getLog().getEntries().get(0).getResponse().getContent().getText());
    }
}


import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.filters.RequestFilter;
import net.lightbody.bmp.filters.ResponseFilter;
import net.lightbody.bmp.proxy.CaptureType;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.*;


public class BrowserMob {
    public static BrowserMobProxyServer proxy;
    public static void main(String[] args) {
        final String[] URL = {""};
        final String[] path = {""};
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
                        path[0] =httpRequest.getUri();
                        System.out.println("URI: "+URL[0]+httpRequest.getUri());
                        System.out.println("headers: "+Arrays.toString(httpRequest.headers().entries().toArray()));
                        System.out.println("result: "+httpRequest.getDecoderResult().toString());
                        System.out.println("method: "+httpRequest.getMethod().toString());
                        System.out.println(httpMessageContents.getTextContents());
                        try {
                            createClassByRequest(httpMessageContents.getTextContents(),path[0],"",true);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
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
                    System.out.println("body: "+httpMessageContents.getCharset().toString());
                    System.out.println(httpMessageContents.getTextContents());
                    try {
                        createClassByRequest(httpMessageContents.getTextContents(),path[0],"",false);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    URL[0]="";
                    path[0]="";
                }
            }
        });
        proxy.start(44);
    }

    static void createClassByRequest(String content,String path,String result,boolean request) throws IOException {
        String className=path.split("/")[path.split("/").length - 1].toUpperCase();
        StringBuilder classText= new StringBuilder("import lombok.Getter;\n" +
                "import lombok.Setter;\n" +
                "@Getter\n" +
                "@Setter\n" +
                "public class " +( request?className + "Request {\n":className + "Response {\n"));

        org.json.JSONObject jsonObject = new JSONObject(content);
        JSONArray array = jsonObject.names();
        for (Object i:array){
            System.out.println(i.toString());
            Object aObj = jsonObject.get(i.toString());
            if (aObj instanceof Integer) {
                classText.append("public int ").append(i.toString()).append(";\n");
            }if (aObj instanceof String) {
                classText.append("public String ").append(i.toString()).append(";\n");
            }
        }
        classText.append("}");
        FileWriter myWriter = new FileWriter("src/test/java/"+(request?className+ "Request":className+ "Response")+".java");
        myWriter.write(String.valueOf(classText));
        myWriter.close();
    };
}
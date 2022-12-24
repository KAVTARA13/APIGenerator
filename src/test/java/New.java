////import org.asynchttpclient.proxy.ProxyServer;
//
//import org.asynchttpclient.proxy.ProxyServer;
//
//public class New {
//    public static void main(String[] args) {
//        ProxyServer proxyServer = null;
//        proxyServer = new ProxyServer(9101);
//        proxyServer.start();
//        proxyServer.setCaptureContent(true);
//        proxyServer.setCaptureHeaders(true);
//    }
//
//
//    Proxy proxy = proxyServer.seleniumProxy();
//proxy.setHttpProxy("localhost:9101");
//
////selenium test config code, omitted for brevity
//
//proxyServer.addRequestInterceptor(new HttpRequestInterceptor() {
//        public void process(HttpRequest request, HttpContext context) throws  HttpException,  IOException {
//            Header[] headers = request.getAllHeaders();
//            System.out.println("\nRequest Headers\n\n");
//            for(Header h : headers) {
//                System.out.println("Key: " + h.getName() + " | Value: " + h.getValue());
//            }
//
//        }
//    });
//}

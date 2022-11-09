package ec.edu.ups;

import java.io.*;
import java.nio.ByteBuffer;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


@SuppressWarnings("ALL")
@ServerEndpoint("/proceso")
public class FileServerEndpoint {
	static ByteArrayOutputStream baos = null;

    @OnOpen
    public void open(Session session, EndpointConfig conf) {
    }

//    @OnMessage
//    public void processUpload(ByteBuffer msg, boolean last, Session session) {
//        while(msg.hasRemaining()) {
//            baos.write(msg.get());
//        }
//    }

    @OnMessage
    public void message(Session session, String msg) {
    	System.out.println(msg);
    	
    	PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
    	 
    	 
		DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
		DocPrintJob docPrintJob = printService.createPrintJob();
		Doc doc = new SimpleDoc(msg.getBytes(), flavor, null);
		try {
			docPrintJob.print(doc, null);
		} catch (PrintException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//        
    }

    private void print(ByteArrayOutputStream baos) throws PrintException {
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        if (services.length > 0) {
            DocFlavor psInFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
            Doc myDoc = new SimpleDoc(new ByteArrayInputStream(baos.toByteArray()), psInFormat, null);
            DocPrintJob job = services[0].createPrintJob();
            job.print(myDoc, null);
        }
    }

    @OnClose
    public void close(Session session, CloseReason reason) {
        System.out.println("WebSocket closed: "+ reason.getReasonPhrase());
    }

    @OnError
    public void error(Session session, Throwable t) {
        t.printStackTrace();

    }
}

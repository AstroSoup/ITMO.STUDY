/*
 * Main.java
 *
 * Created on Jul 1, 2007, 7:24:43 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */



import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.javascript.JavaScript;
import com.meterware.httpunit.javascript.JavaScriptEngineFactory;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.SAXException;

/**
 *
 * @author gs145266
 */
public class Main {

    /** Creates a new instance of Main */
    public Main() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ServletRunner sr = null;
        try {
            HttpUnitOptions.setExceptionsThrownOnScriptError(false);
            sr = new ServletRunner();
            sr.registerServlet("myServlet", HelloWorld.class.getName());
            ServletUnitClient sc = sr.newClient();
            int number = 1;
            WebRequest request = new GetMethodWebRequest("http://test.meterware.com/myServlet");
            ArrayList<Integer> a = new ArrayList<>();
            rec();
            while (true) {
                a.add(number++);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger("global").log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger("global").log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger("global").log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger("global").log(Level.SEVERE, null, ex);
        } finally {
            if (sr != null) {
                sr.shutDown();
            }
        }
    }

    public static void rec() {
        rec();
    }
}

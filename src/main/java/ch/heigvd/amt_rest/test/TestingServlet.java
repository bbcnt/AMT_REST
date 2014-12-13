package ch.heigvd.amt_rest.test;

import ch.heigvd.amt_rest.model.Fact;
import ch.heigvd.amt_rest.model.Observation;
import ch.heigvd.amt_rest.model.Organization;
import ch.heigvd.amt_rest.model.Sensor;
import ch.heigvd.amt_rest.model.User;
import ch.heigvd.amt_rest.services.FactManagerLocal;
import ch.heigvd.amt_rest.services.ObservationManagerLocal;
import ch.heigvd.amt_rest.services.OrganizationManagerLocal;
import ch.heigvd.amt_rest.services.SensorManagerLocal;
import ch.heigvd.amt_rest.services.UserManagerLocal;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * This servlet is used to test the application. To be used,
 * simply go to http://localhost:8080/AMT_REST/test
 */
@WebServlet(name = "TestingServlet", urlPatterns = {"/test"})
public class TestingServlet extends HttpServlet {

    @EJB
    UserManagerLocal uManager;
    @EJB
    OrganizationManagerLocal orgManager;
    @EJB
    SensorManagerLocal sManager;
    @EJB
    ObservationManagerLocal obsManager;
    @EJB
    FactManagerLocal fManager;

    ConcurrentLinkedQueue <Sensor> listSensors = new ConcurrentLinkedQueue<>();

    /**
     * His method returns a double value (used to generate observations)
     * @return double a double value
     */
    public static double randDbl() {

    // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

    // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        double randomNum = rand.nextDouble();
        randomNum *= 10;
        return randomNum;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Creating a first organization
        Organization org1 = new Organization();
        org1.setName("Org1");

        //Creating a user and linking him to the organization
        User u1 = new User();
        u1.setName("John");
        u1.setOrganization(org1);
        
        orgManager.createOrganization(org1);
        uManager.createUser(u1);

        //An other used
        User u2 = new User();
        u2.setName("Paul");
        u2.setOrganization(org1);
        uManager.createUser(u2);

        //A new organization
        Organization org2 = new Organization();
        org2.setName("Org2");
        orgManager.createOrganization(org2);
         
        //And another one
        User u3 = new User();
        u3.setName("Marie");
        u3.setOrganization(org2);
        uManager.createUser(u3);
        
        //Here we create 20 sensors
        Sensor s = new Sensor();

        //Sensors org 1
        for (int i = 0; i < 10; i++) {
            s = new Sensor();
            s.setName("sensor_org1_" + i);
            s.setOrganization(org1);
            s.setType("THERMO");
            s.setVisibility("All");
            s.setDescription("None, really");
            sManager.createSensor(s);
            listSensors.add(s);
        }

        //Sensors org2
        for (int i = 0; i < 10; i++) {
            s = new Sensor();
            s.setName("sensor_org2_" + i);
            s.setOrganization(org2);
            s.setType("VECTO");
            s.setVisibility("All");
            s.setDescription("Nope, nothing either");
            sManager.createSensor(s);
            listSensors.add(s);
        }
        
        //This is a test fact (date somewhere in the 1970), to see that different
        //facts are created in function of the date.
        Fact f = new Fact();
        f.setDate(new Date(333333333));
        f.setInformation("A test fact, just to see that facts are separated by date");
        f.setOrganization(org2);
        f.setType(Fact.DATE_COUNTER);
        f.setSensor(s);
        f.setSensorType(s.getType());
        f.setVisibility("ALL");
        
        fManager.createFact(f);
        
        //Here we create 1020 observations (51 per sensor)
        new Thread() {
            int count = 1000;
            Observation obs;

            @Override
            synchronized public void  run() {
                try {
                    while (true) {
                        if(count < 0)
                            break;
                        for (Sensor s : listSensors) {
                            count--;
                            //TimeUnit.SECONDS.sleep(3);
                            obs = new Observation();
                            obs.setSensor(s);
                            obs.setTimeS(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
                            obs.setValueObservation(randDbl());
                            obsManager.createObservation(obs);
                            
                        }
                        
                    }
                } catch (Exception ex) {
                    Logger.getLogger(TestingServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet TestingServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>The test was launched, you can now check your database" + "</h2>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

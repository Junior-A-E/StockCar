package api;

import esiea.api.VoitureAPI;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.json.JSONObject;
import org.junit.Test;

import javax.ws.rs.core.Application;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class VoitureAPITest extends JerseyTest {

    VoitureAPI api = new VoitureAPI();
    @Override
    protected Application configure() {
        return new ResourceConfig(VoitureAPI.class);
    }

    @Test
    public void testGetVoituresJson() {
        String result = api.getVoituresJson("all", "-1", "-1");
        // Vérifiez que le résultat est comme prévu
    }

    @Test
    public void testAjouterVoitureValidJson() {
        String json = "{\"id\":1,\"marque\":\"Renault\",\"modele\":\"VelSatis\",\"finition\":\"Initiale\",\"carburant\":\"E\",\"km\":174826,\"annee\":2008,\"prix\":4600}";
        String response = api.ajouterVoiture(json);
        // Vérifiez que le résultat est comme prévu
        JSONObject jsonResponse = new JSONObject(response);
        assertTrue(jsonResponse.getBoolean("succes"));
    }

    @Test
    public void testAjouterVoitureInvalidJson() {
        String json = "{\"id\":1,\"marque\":\"Renault\",\"modele\":\"VelSatis\",\"finition\":\"Initiale\",\"carburant\":\"diesel\",\"km\":174826,\"annee\":2038,\"prix\":4600}";
        String response = api.ajouterVoiture(json);
        // Vérifiez que le résultat est comme prévu
        JSONObject jsonResponse = new JSONObject(response);
        assertFalse(jsonResponse.getBoolean("succes"));
    }
}

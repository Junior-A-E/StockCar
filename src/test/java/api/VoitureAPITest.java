package api;

import esiea.api.VoitureAPI;
import esiea.dao.VoitureDAO;
import esiea.metier.Voiture;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.Application;

import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class VoitureAPITest{

    VoitureAPI testApi = new VoitureAPI();

    @Mock
    private VoitureDAO vDao;

    @InjectMocks
    private VoitureAPI api;

    @Test
    public void testGetVoituresJsonAll() {
        String allResult = testApi.getVoituresJson("all");
        // Vérifiez que le résultat est comme prévu
        JSONObject jsonResponseAll = new JSONObject(allResult);
        assertEquals(9,jsonResponseAll.getInt("volume"));
    }

    @Test
    public void testGetVoituresJsonValue(){
        String valueResult = testApi.getVoituresJson("2");
        JSONObject jsonResponseValue = new JSONObject(valueResult);
        assertEquals(1,jsonResponseValue.getInt("volume"));
    }

    @Test
    public void testGetVoituresJson(){
        String carResult = testApi.getVoituresJson("opel");
        JSONObject jsonCarResult = new JSONObject(carResult);
        assertEquals(1,jsonCarResult.getInt("volume"));
    }

    @Test
    public void testAjouterVoitureValidJson() throws SQLException {

        String json = "{\"id\":10,\"marque\":\"Renault\",\"modele\":\"VelSatis\",\"finition\":\"Initiale\",\"carburant\":\"E\",\"km\":174826,\"annee\":2008,\"prix\":4600}";

        MockitoAnnotations.initMocks(this);
        doNothing().when(vDao).ajouterVoiture(any(Voiture.class));

        String result = api.ajouterVoiture(json);
        assertEquals("{\"succes\":true}", result);
        verify(vDao, times(1)).ajouterVoiture(any(Voiture.class));
    }

    @Test
    public void testAjouterVoitureInvalidJson() throws SQLException {
        String json = "{\"id\":1,\"marque\":\"Renault\",\"modele\":\"VelSatis\",\"finition\":\"Initiale\",\"carburant\":\"diesel\",\"km\":174826,\"annee\":2038,\"prix\":4600}";
        MockitoAnnotations.initMocks(this);
        doNothing().when(vDao).ajouterVoiture(any(Voiture.class));

        String result = api.ajouterVoiture(json);
        assertEquals("{\"succes\":false}", result);
        verify(vDao, times(0)).ajouterVoiture(any(Voiture.class));
    }

    @Test
    public void testSupprimerVoitureTrue() throws SQLException {
        String id = "2";
        MockitoAnnotations.initMocks(this);
        doNothing().when(vDao).supprimerVoiture(any(String.class));

        String result = api.supprimerVoiture(id);
        assertEquals("{\"succes\":true}", result);
        verify(vDao, times(1)).supprimerVoiture(any(String.class));
    }

    @Test
    public void testVoitureFromJson() {
        // Préparation des données de test
        String json = "{\"id\":10,\"marque\":\"Renault\",\"modele\":\"VelSatis\",\"finition\":\"Initiale\",\"carburant\":\"E\",\"km\":174826,\"annee\":2008,\"prix\":4600}";
        JSONObject jsonObj = new JSONObject(json);
        // Exécution de la méthode
        Voiture voiture = testApi.voitureFromJson(jsonObj);

        // Vérification des résultats
        assertNotNull(voiture);
        assertEquals(10, voiture.getId());
        assertEquals("Renault", voiture.getMarque());
        assertEquals("VelSatis", voiture.getModele());
        assertEquals(Voiture.Carburant.ESSENCE, voiture.getCarburant());
        assertEquals(2008, voiture.getAnnee());
        assertEquals(4600, voiture.getPrix());
    }
}

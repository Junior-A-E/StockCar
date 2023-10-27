package metier;

import esiea.metier.Voiture;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.*;

public class VoitureTest {
    @Test
    public void testCheckValidData() {
        Voiture voiture = new Voiture();
        voiture.setId(1);
        voiture.setMarque("Toyota");
        voiture.setModele("Camry");
        voiture.setFinition("LE");
        voiture.setCarburant(Voiture.Carburant.ESSENCE);
        voiture.setKm(50000);
        voiture.setAnnee(2020);
        voiture.setPrix(20000);

        assertTrue(voiture.check());
    }

    @Test
    public void testCheckInvalidData() {
        Voiture voiture = new Voiture();
        voiture.setId(-1); // Invalid ID
        voiture.setMarque("Toyota");
        voiture.setModele(""); // Invalid empty modele
        voiture.setFinition("LE");
        voiture.setCarburant(Voiture.Carburant.ESSENCE);
        voiture.setKm(-100); // Invalid negative km
        voiture.setAnnee(1899); // Invalid year
        voiture.setPrix(500); // Invalid low price

        assertFalse(voiture.check());
    }

    @Test
    public void testGetTypeDonnee() {
        assertEquals("string", Voiture.getTypeDonnee("marque"));
        assertEquals("entier", Voiture.getTypeDonnee("id"));
        assertEquals("", Voiture.getTypeDonnee("color")); // Invalid attribute
    }
}

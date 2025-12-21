package net.sf.robocode.battle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test de Integración / Persistencia para BattleProperties.
 * Objetivo: Asegurar que los nuevos campos de Daño Aleatorio sobreviven
 * al proceso de guardado y carga en disco.
 */
public class TestBattleProperties {

    @Test
    public void testRandomDamagePersistence() throws IOException {
        // 1. Arrange: Crear propiedades originales con valores específicos
        BattleProperties originalProps = new BattleProperties();
        
        boolean expectedEnabled = true;
        double expectedMin = 5.0;
        double expectedMax = 20.0;

        originalProps.setRandomDamage(expectedEnabled);
        originalProps.setRandomDamageMin(expectedMin);
        originalProps.setRandomDamageMax(expectedMax);

        File tempFile = File.createTempFile("test_battle_persistence", ".properties");
        tempFile.deleteOnExit(); 
        FileOutputStream out = new FileOutputStream(tempFile);
        originalProps.store(out, "Test Random Damage Persistence");
        out.close();

        BattleProperties loadedProps = new BattleProperties();
        FileInputStream in = new FileInputStream(tempFile);
        loadedProps.load(in);
        in.close();


        Assert.assertTrue("Fallo de Persistencia: El flag de RandomDamage se perdió o cambió", 
                loadedProps.getRandomDamage() == expectedEnabled);
        
        Assert.assertEquals("Fallo de Persistencia: El daño mínimo no se recuperó correctamente", 
                expectedMin, loadedProps.getRandomDamageMin(), 0.0001);
        
        Assert.assertEquals("Fallo de Persistencia: El daño máximo no se recuperó correctamente", 
                expectedMax, loadedProps.getRandomDamageMax(), 0.0001);
    }

    @Test
    public void testDefaultValuesLoad() throws IOException {
        
        BattleProperties emptyProps = new BattleProperties();
        File tempFile = File.createTempFile("test_empty_battle", ".properties");
        tempFile.deleteOnExit();
        
        FileOutputStream out = new FileOutputStream(tempFile);
        emptyProps.store(out, "Empty Battle");
        out.close();

        BattleProperties loadedProps = new BattleProperties();
        loadedProps.load(new FileInputStream(tempFile));

        Assert.assertFalse("Por defecto debería estar desactivado", loadedProps.getRandomDamage());
        Assert.assertEquals("Por defecto el min debería ser 0.1", 0.1, loadedProps.getRandomDamageMin(), 0.0001);
    }
}

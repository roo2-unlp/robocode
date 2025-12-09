package net.sf.robocode.battle;

import org.junit.Assert;
import org.junit.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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
        
        // Configuramos valores "no standard" para asegurar que se guardan los nuestros
        boolean expectedEnabled = true;
        double expectedMin = 5.0;
        double expectedMax = 20.0;

        originalProps.setRandomDamage(expectedEnabled);
        originalProps.setRandomDamageMin(expectedMin);
        originalProps.setRandomDamageMax(expectedMax);

        // 2. Act: Guardar en un archivo temporal (Simulamos guardar la batalla)
        File tempFile = File.createTempFile("test_battle_persistence", ".properties");
        tempFile.deleteOnExit(); // Se borra automáticamente al terminar el test
        
        FileOutputStream out = new FileOutputStream(tempFile);
        originalProps.store(out, "Test Random Damage Persistence");
        out.close();

        // 3. Act: Cargar en un NUEVO objeto limpio (Simulamos leer al iniciar Robocode)
        BattleProperties loadedProps = new BattleProperties();
        FileInputStream in = new FileInputStream(tempFile);
        loadedProps.load(in);
        in.close();

        // 4. Assert: Verificar que los datos queden intactos
        Assert.assertTrue("Fallo de Persistencia: El flag de RandomDamage se perdió o cambió", 
                loadedProps.getRandomDamage() == expectedEnabled);
        
        Assert.assertEquals("Fallo de Persistencia: El daño mínimo no se recuperó correctamente", 
                expectedMin, loadedProps.getRandomDamageMin(), 0.0001);
        
        Assert.assertEquals("Fallo de Persistencia: El daño máximo no se recuperó correctamente", 
                expectedMax, loadedProps.getRandomDamageMax(), 0.0001);
    }

    @Test
    public void testDefaultValuesLoad() throws IOException {
        // Test extra: Verificar que si el archivo NO tiene las claves (archivo viejo),
        // el sistema carga los defaults seguros en lugar de crashear o poner ceros.
        
        BattleProperties emptyProps = new BattleProperties();
        File tempFile = File.createTempFile("test_empty_battle", ".properties");
        tempFile.deleteOnExit();
        
        // Guardamos unas propiedades vacías (sin setear random damage)
        FileOutputStream out = new FileOutputStream(tempFile);
        emptyProps.store(out, "Empty Battle");
        out.close();

        // Cargamos
        BattleProperties loadedProps = new BattleProperties();
        loadedProps.load(new FileInputStream(tempFile));

        // Verificamos defaults seguros (0.1 y false)
        Assert.assertFalse("Por defecto debería estar desactivado", loadedProps.getRandomDamage());
        Assert.assertEquals("Por defecto el min debería ser 0.1", 0.1, loadedProps.getRandomDamageMin(), 0.0001);
        // Nota: No testeamos el max por defecto aquí porque depende de Rules.ROBOT_HIT_DAMAGE 
        // y queremos evitar acoplamiento con Rules si cambia.
    }
}

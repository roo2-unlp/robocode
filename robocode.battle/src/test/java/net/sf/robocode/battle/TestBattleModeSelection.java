package net.sf.robocode.battle;

import net.sf.robocode.battle.damage.IDamageModel;
import net.sf.robocode.host.ICpuManager;
import net.sf.robocode.host.IHostManager;
import net.sf.robocode.settings.ISettingsManager;
import net.sf.robocode.security.HiddenAccess;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import robocode.control.RobotSpecification;
import robocode.Rules;

/**
 * Test de Integración: Selección de Modelo de Daño.
 * Objetivo: Validar que la clase Battle instancie la implementación correcta de IDamageModel.
 * Se utiliza verificación de comportamiento (valores de retorno conocidos) para confirmar el tipo.
 */
public class TestBattleModeSelection {

    @Before
    public void setUp() {
        // Inicialización de la infraestructura interna de Robocode.
        // Se requiere para instanciar los helpers de reglas (IHiddenRulesHelper) utilizados en Battle.setup().
        // Sin esta llamada, HiddenAccess.createRules(...) genera NullPointerException.
        HiddenAccess.init();
    }

    @Test
    public void testBattleSelectsRandomDamageModel() {
        // Configuración de propiedades para activar la funcionalidad.
        BattleProperties props = new BattleProperties();
        props.setRandomDamage(true);
        
        // Se configuran valores límite atípicos para identificar la instancia por su comportamiento.
        // Un modelo aleatorio con min=1000 y max=1000 retornará invariablemente 1000.0.
        props.setRandomDamageMin(1000.0);
        props.setRandomDamageMax(1000.0);

        Battle battle = createBattleWithStubs();

        // Ejecución de la inicialización.
        battle.setup(new RobotSpecification[0], props, false);

        // Obtención del modelo seleccionada por el contexto.
        IDamageModel selectedModel = battle.getDamageModel();

        // Validación:
        // Se confirma que el modelo seleccionado respete la configuración inyectada.
        // El valor 1000.0 confirma que se trata de la instancia RandomDamageModel.
        Assert.assertEquals("El modelo seleccionado debe respetar la configuración aleatoria (1000.0)",
                1000.0, 
                selectedModel.getRobotHitDamage(), 
                0.0001);
    }

    @Test
    public void testBattleSelectsStandardDamageModel() {
        // Configuración de propiedades por defecto (funcionalidad desactivada).
        BattleProperties props = new BattleProperties();
        props.setRandomDamage(false);

        Battle battle = createBattleWithStubs();

        // Ejecución de la inicialización.
        battle.setup(new RobotSpecification[0], props, false);

        // Obtención del modelo seleccionada por el contexto.
        IDamageModel selectedModel = battle.getDamageModel();

        // Validación:
        // Se verifica que, ante la configuración desactivada, el comportamiento corresponda al estándar.
        // El retorno debe coincidir con la constante oficial de reglas (0.6).
        Assert.assertEquals("El modelo seleccionado debe ser el estándar (0.6)",
                Rules.ROBOT_HIT_DAMAGE, 
                selectedModel.getRobotHitDamage(), 
                0.0001);
    }

    // --- Método Auxiliar para Stubs ---
    
    private Battle createBattleWithStubs() {
        // Implementación anónima de ICpuManager para satisfacer el contrato de la interfaz.
        ICpuManager cpuManagerStub = new ICpuManager() {
            @Override
            public long getCpuConstant() {
                return 1000;
            }

            @Override
            public void calculateCpuConstant() {
                // Método no requerido para la prueba de selección.
            }
        };

        // Dependencias nulas permitidas ya que no intervienen en el método setup() para este propósito.
        return new Battle(null, null, null, cpuManagerStub, null);
    }
}
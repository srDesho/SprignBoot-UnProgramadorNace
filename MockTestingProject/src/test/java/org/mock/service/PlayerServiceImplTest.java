package org.mock.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mock.DataProvider;
import org.mock.persistence.entity.Player;
import org.mock.persistence.repository.PlayerRepositoryImpl;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Agregamos @ExtendWidth para habilitar las anotaciones para mockito pueda agregarlas en nuestra dependencia simulada
@ExtendWith(MockitoExtension.class)
public class PlayerServiceImplTest {

    @Mock // Para la dependencia inventada
    PlayerRepositoryImpl playerRepository;

    @InjectMocks // Para la clase testeada
    PlayerServiceImpl playerService;

    // Debemos saber que cuando hacemos un test con JUnit y hacemos las pruebas con datos reales de por ejemplo una
    // base de datos, esta db siempre está en constante cambio, y entonces cuando no exista un registro que le damos
    // a nuestro test por ejemplo: "Leonel Mesi"

    // En este caso tenemos los datos del objeto(Player) en la posición 0, pero como siempre va a cambiar, pues en
    // cualquier momento no pasará la prueba el test y eso es malo.

    /*@Test
    public void testFindAll() {
        // Given
        PlayerRepositoryImpl playerRepository = new PlayerRepositoryImpl();
        PlayerServiceImpl playerService = new PlayerServiceImpl(playerRepository);

        // When
        List<Player> result = playerService.findAll();

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Lionel Messi", result.get(0).getName());
        assertEquals("Inter Miami", result.get(0).getTeam());
        assertEquals("Delantero", result.get(0).getPosition());
    }*/

    // Es en estos casos que se trabaja con Kockito, para realizar pruebas con datos inventados.
    // NOTA: Mockito solamente trabaja con las dependencias y no con la clase testeada, en este caso
    // MOCKITO va a trabajar con la dependencia: PlayerRepositoryImpl playerRepository = new PlayerRepositoryImpl();
    // y JUNIT con la clase testeada: PlayerServiceImpl playerService = new PlayerServiceImpl(playerRepository);

    // Entonces debemos crear una dependencia inventada por nosotros,
    // debemos quitar la instancia new PlayerRepositoryImpl().

    // y debemos agregar Mockito.mock() MOCK EN ESPAÑOL ES = SIMULAR;
    // dentro del mock() agregamos como parámetro el objeto que vamos a simular
    @Test
    public void testFindAll() {
        // Given
        // Esto no es común hacerlo así lo vamos a agregar de otra forma, vea en el comienzo
        /*PlayerRepositoryImpl playerRepository = mock(PlayerRepositoryImpl.class);
        PlayerServiceImpl playerService = new PlayerServiceImpl(playerRepository);*/

        // When
        // El método Mockito.when() lo escribimos sólo when() pues porque ya agregamos el import estático de Mockito.
        // dentro del when() agregamos nuestra dependencia inventada y llamamos al método que queremos testear
        // agregamos el método thenReturn() y dentro de este método es donde debemos agregar nuestra data provider.
        // Eeste data provider vendría a ser nuestro registro inventado, lo debemos crear en la carpeta raíz de nuestro
        // proyecto o sea org.mock(depende con qué nombre creamos el nuestro).
        when(playerRepository.findAll()).thenReturn(DataProvider.playerListMock());
        List<Player> result = playerService.findAll();

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Lionel Messi", result.get(0).getName());
        assertEquals("Inter Miami", result.get(0).getTeam());
        assertEquals("Delantero", result.get(0).getPosition());
        // Con verify hacemos que verifique que nuestra dependencia esté llamando al método que necesitamos.
        verify(this.playerRepository).findAll();
    }

    // Test para el método findById()
    @Test
    public void testFindById() {
        // Given
        Long id = 1L;

        // When
        // El anyLong() sirve para que se refiera a cualquier número long, lo ponemos porque no es necesario
        // pasarle un id en específico.
        when(playerRepository.findById(anyLong())).thenReturn(DataProvider.playerMock());
        Player player= this.playerService.findById(id);

        // Then
        assertNotNull("Lionel Messi", player.getName());
        assertNotNull("Inter Miami", player.getTeam());
        assertNotNull("Delantero", player.getPosition());
        // Con verify hacemos que verifique que nuestra dependencia esté llamando al método que necesitamos.
        verify(this.playerRepository).findById( anyLong() );

        // NOTA: Cuando usas métodos de aserción (assert) para verificar el estado final o el resultado de un método
        // en tus pruebas, no siempre es necesario usar verify de Mockito. Los métodos de aserción se utilizan
        // principalmente para pruebas de estado, mientras que verify de Mockito se utiliza para pruebas de comportamiento.
    }

    // Test para el método save()
    // Aquí trabajaremos cuando un método no nos da respuesta, o sea es un void
    @Test
    public void testSave() {

        // Given
        Player player = DataProvider.newPlayerMock();

        // When
        this.playerService.save(player);

        // Then
        // Podemos capturar el objeto guardado en caso de que lo requiramos. lo hacemos con la clase ArgumentCaptor
        ArgumentCaptor<Player> playerArgumentCaptor = ArgumentCaptor.forClass(Player.class);

        // Verificar que el método save fue llamado.
        // verify(this.playerRepository).save(any(Player.class));

        // Verificar que el método save fue llamado y capturamos con el método "capture" el objeto guardado.
        verify(this.playerRepository).save(playerArgumentCaptor.capture());
        assertEquals(10L, playerArgumentCaptor.getValue().getId());
        assertEquals("Luiz Diaz", playerArgumentCaptor.getValue().getName());

        // NOTA: El ArgumentCaptor no se usa siempre, pero nos puede ayudar a verificar el argumento, ya que en la parte
        // del When no se puede validar directamente la respuesta porque es un método vacío.
    }

}

package org.mock.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mock.DataProvider;
import org.mock.persistence.entity.PlayerEntity;
import org.mock.persistence.repository.PlayerRepositoryImpl;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

// 3. Tercer forma de inyectar mocks con la anotación @ExtendWith
@ExtendWith(MockitoExtension.class)
public class PlayerServiceImplTest {


    /*
    // 1. Primer forma de inyectar mocks, es una forma muy poco común

    private PlayerRepositoryImpl playerRepository;
    private PlayerServiceImpl playerService;

    @BeforeEach
    void init() {
        this.playerRepository = mock(PlayerRepositoryImpl.class);
        this.playerService = new PlayerServiceImpl(this.playerRepository);
    }*/

    /*
    // 2. Segunda forma de inyectar mocks
    @Mock // @Mock para las dependencias
    private PlayerRepositoryImpl playerRepository;
    @InjectMocks // @InjectMocks para las clses que son testeadas y donde se inyectaran el mock
    private PlayerServiceImpl playerService;

    @BeforeEach
    void init() {
        // Esto es para que habilitemos las anotaciones de mockito
        MockitoAnnotations.openMocks(this);
    }
    */

    // 3. Tercer forma de inyectar Mock
    @Mock
    private PlayerRepositoryImpl playerRepository;
    @InjectMocks
    private PlayerServiceImpl playerService;

    // Nota para nunca olvidar: MOCKITO siempre trabaja con las dependencias.
    @Test
    public void testFindAll() {
        // When
        when(playerRepository.findAll()).thenReturn(DataProvider.playerListMock());
        List<PlayerEntity> result = playerService.findAll();

        // Then
        assertNotNull(result);
        assertEquals("Lionel Messi", result.get(0).getName());
        assertEquals("Inter Miami", result.get(0).getTeam());
        assertEquals("Delantero", result.get(0).getPosition());
    }

    @Test
    public void testFindById() {
        // Given
        PlayerEntity player = DataProvider.playerMock();

        // When
        when(playerRepository.findById( anyLong() )).thenReturn(player);
        PlayerEntity result = this.playerService.findById(1L);

        // Then
        assertEquals(1L, result.getId());
        assertEquals("Lionel Messi", result.getName());
        assertEquals("Inter Miami", result.getTeam());
        assertEquals("Delantero", result.getPosition());
        verify(this.playerRepository).findById(anyLong());
    }

    @Test
    public void testSave() {
        // Given
        PlayerEntity player = DataProvider.newPlayerMock();

        // When
        this.playerService.save(player);

        // Then
        // Capturamos el player para poder hacer verificaciones con él.
        ArgumentCaptor<PlayerEntity> playerCaptor = ArgumentCaptor.forClass(PlayerEntity.class);

        // Verificamos con verify el llamado al método save y que nos llegue un objeto PlayerEntity
        verify(this.playerRepository).save(any(PlayerEntity.class));

        // Capturamos el objeto mock para poder hacer assertions con él.
        verify(this.playerRepository).save( playerCaptor.capture() );
        assertEquals(10L, playerCaptor.getValue().getId());
        assertEquals("Iker Casillas", playerCaptor.getValue().getName());
        assertEquals("España", playerCaptor.getValue().getTeam());
        assertEquals("Arquero", playerCaptor.getValue().getPosition());
    }

    @Test
    void testDelete() {
        // Given
        Long idPlayer = 10L;

        // When
        this.playerService.deleteById(idPlayer);

        // Then
        ArgumentCaptor<Long> longCaptor = ArgumentCaptor.forClass(Long.class);
        verify(this.playerRepository).deleteById(anyLong());
        verify(this.playerRepository).deleteById(longCaptor.capture());

        assertEquals(10L, longCaptor.getValue());
    }

    /**
     * SOLUCIÓN A WARNINGS DE CONSOLA EN JAVA 21
     *
     * Si estás viendo warnings molestos en la consola al ejecutar esta clase con Java 21, sigue estos pasos:
     *
     * 1. LIMPIEZA DE CONFIGURACIONES DE EJECUCIÓN:
     * - Ve a "Edit Configurations..." (clic en el nombre de la configuración actual junto al botón "Run").
     * - Selecciona y elimina TODAS las configuraciones de ejecución existentes (usa la tecla "Supr").
     * - Haz clic en "Apply" y luego en "OK".
     *
     * 2. EJECUCIÓN INICIAL:
     * - Ejecuta esta clase directamente desde el editor (clic derecho -> "Run 'TuClase.main()'").
     * - Esto generará una nueva configuración de ejecución automáticamente.
     *
     * 3. CONFIGURACIÓN DE OPCIONES DE JVM:
     * - Vuelve a "Edit Configurations...".
     * - Selecciona la nueva configuración de ejecución creada para esta clase.
     * - En el campo "VM options" (o similar), pega lo siguiente:
     * -ea -XX:+EnableDynamicAgentLoading -Xshare:off
     * - Haz clic en "Apply" y luego en "OK".
     *
     * 4. EJECUCIÓN POSTERIOR:
     * - A partir de ahora, ejecuta esta clase SIEMPRE desde el editor (clic derecho -> "Run 'TuClase.main()'").
     * - O también puedes ejecutarla desde el botón "Run" (el verde), pues ya se encuentra configurada.
     *
     * EXPLICACIÓN:
     * - Estos warnings son causados por cambios en la forma en que Java 21 maneja ciertos aspectos internos.
     * - Las opciones de JVM añadidas fuerzan un comportamiento compatible con versiones anteriores, eliminando los warnings.
     * - Es importante seguir el proceso de creación de la configuración desde cero para asegurar que las opciones se apliquen correctamente.
     */

}

package org.mock.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mock.DataProvider;
import org.mock.persistence.entity.PlayerEntity;
import org.mock.persistence.repository.PlayerRepositoryImpl;

import java.util.List;

public class PlayerServiceImplTest {

    // Nota para nunca olvidar: MOCKITO siempre trabaja con las dependencias.
    @Test
    public void testFindAll() {
        // GIVEN
        PlayerRepositoryImpl playerRepository = mock(PlayerRepositoryImpl.class);
        PlayerServiceImpl playerService = new PlayerServiceImpl(playerRepository);

        // WHEN
        when(playerRepository.findAll()).thenReturn(DataProvider.playerListMock());
        List<PlayerEntity> result = playerService.findAll();

        // THEN
        assertNotNull(result);
        assertEquals("Lionel Messi", result.get(0).getName());
        assertEquals("Inter Miami", result.get(0).getTeam());
        assertEquals("Delantero", result.get(0).getPosition());
    }

}

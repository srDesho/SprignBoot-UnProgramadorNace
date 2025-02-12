package org.mock.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mock.DataProvider;
import org.mock.persistence.entity.PlayerEntity;
import org.mock.persistence.repository.PlayerRepositoryImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class PlayerServiceImplTest {



    // 1. Primer forma de inyectar mocks, es una forma muy poco común

    private PlayerRepositoryImpl playerRepository;
    private PlayerServiceImpl playerService;

    @BeforeEach
    void init() {
        this.playerRepository = mock(PlayerRepositoryImpl.class);
        this.playerService = new PlayerServiceImpl(this.playerRepository);
    }



    // Nota para nunca olvidar: MOCKITO siempre trabaja con las dependencias.
    @Test
    public void testFindAll() {
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

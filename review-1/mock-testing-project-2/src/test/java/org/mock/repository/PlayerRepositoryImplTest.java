package org.mock.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mock.DataProvider;
import org.mock.persistence.entity.PlayerEntity;
import org.mock.persistence.repository.PlayerRepositoryImpl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class PlayerRepositoryImplTest {

    private PlayerRepositoryImpl playerRepository;

    @BeforeEach
    public void setUp() {
        this.playerRepository = new PlayerRepositoryImpl();
        playerRepository.clearDatabase();
    }

    @Test
    public void testFindAll_ListEmpty() {
        // Given
        // When
        List<PlayerEntity> result = this.playerRepository.findAll();

        // Then
        assertEquals(new ArrayList<PlayerEntity>(), result);

    }

    @Test
    public void testFindAll_ListWithRecords() {
        // Given
        for (int i = 0; i < DataProvider.playerListMock().size(); i++) {
            this.playerRepository.addPlayer(DataProvider.playerListMock().get(i));
        }

        // When
        List<PlayerEntity> result = this.playerRepository.findAll();

        // Then
        assertFalse(result.isEmpty());
        assertEquals("Lionel Messi", result.get(0).getName());
        assertEquals("Inter Miami", result.get(0).getTeam());
        assertEquals("Delantero", result.get(0).getPosition());
    }

    @Test
    public void testFindById_IfExistsInDatabase() {
        // Given
        Long id = 10L;
        PlayerEntity playerExpected = new PlayerEntity(10L, "Iker Casillas", "España", "Arquero");
        this.playerRepository.addPlayer(playerExpected);

        // When
        PlayerEntity playerEntity = this.playerRepository.findById(id);

        // Then
        assertEquals(playerExpected, playerEntity);
        assertEquals(playerExpected.getName(), playerEntity.getName());
        assertEquals(playerExpected.getTeam(), playerEntity.getTeam());
        assertEquals(playerExpected.getPosition(), playerEntity.getPosition());
    }

    @Test
    public void testFindById_IfDoesNotExistInDataBase_Throw() {
        // Given
        Long id = 11L;
        PlayerEntity player = new PlayerEntity(10L, "Iker Casillas", "España", "Arquero");
        this.playerRepository.addPlayer(player);

        // Then/ When
        assertThrows(NoSuchElementException.class, () -> {
            PlayerEntity playerEntity = this.playerRepository.findById(id);
        });
    }

    @Test
    public void testSave_WhenPlayerIsSavedToDatabase() {
        // Given
        PlayerEntity playerEntity = DataProvider.playerMock();

        // When
        this.playerRepository.save(playerEntity);

        // Then
        List<PlayerEntity> players = this.playerRepository.findAll();
        assertNotNull(players);
        assertEquals(1, players.size());
        assertTrue(players.contains(playerEntity));

    }

    @Test
    public void testSave_WhenPlayerIsNull_NoExceptionIsThrown() {
        // Given
        PlayerEntity playerEntity = null;

        // When
        this.playerRepository.save(playerEntity);

        // Then
        List<PlayerEntity> players = this.playerRepository.findAll();
        assertNotNull(players);
        assertEquals(0, players.size());
    }

    @Test
    public void testDeleteById_WhenPlayerExists_PlayerIsDeleted() {
        // Given
        List<PlayerEntity> players = DataProvider.playerListMock();
        for (PlayerEntity player : players) {
            this.playerRepository.addPlayer(player);
        }

        PlayerEntity playerToDelete = players.get(1);
        Long idToDelete = playerToDelete.getId();

        // When
        this.playerRepository.deleteById(idToDelete);

        // Then
        assertFalse(playerRepository.findAll().contains(playerToDelete));
        assertEquals(players.size() -1, playerRepository.findAll().size());
    }

    @Test
    public void testDeleteById_WhenPlayerDoesNotExist_DoesNothing() {
        // Given
        List<PlayerEntity> players = DataProvider.playerListMock();
        for (PlayerEntity player : players) {
            this.playerRepository.addPlayer(player);
        }

        Long idToDelete = 999L;

        // When
        this.playerRepository.deleteById(idToDelete);

        // Then
        assertEquals(players.size(), this.playerRepository.findAll().size());
    }
}

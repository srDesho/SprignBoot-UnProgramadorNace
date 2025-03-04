package com.cristianml.service.impl;

import com.cristianml.dataProvider.MakerProvider;
import com.cristianml.models.MakerModel;
import com.cristianml.persistence.impl.MakerDAOImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MakerServiceImplTest {

    @Mock
    MakerDAOImpl makerDAO;

    @InjectMocks
    MakerServiceImpl makerService;

    // findAll
    @Test
    public void testFindAll() {
        // Given
        List<MakerModel> makerListMock = MakerProvider.makerListMock();

        // When
        when(this.makerDAO.findAll()).thenReturn(makerListMock);
        List<MakerModel> result = this.makerService.findAll();

        // Then
        verify(this.makerDAO).findAll();
        assertFalse(result.isEmpty());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Samsung", result.get(0).getName());
    }

    // findById

    @Test
    public void testFindById_MakerExists() {
        // Given
        Optional<MakerModel> makerModel = MakerProvider.makerOptionalMock();
        Long idToFind = makerModel.get().getId();

        // When
        when(this.makerDAO.findById( anyLong() )).thenReturn(makerModel);
        Optional<MakerModel> result = this.makerService.findById(idToFind);

        // Then
        verify(this.makerDAO).findById(anyLong());
        assertEquals(idToFind, result.get().getId());
        assertEquals("Samsung", result.get().getName());
    }

    @Test
    public void testFindById_IfMakerDoesNotExists() {
        // Given
        Long idToFind = 999L;

        // When
        when(this.makerDAO.findById( anyLong() )).thenReturn(Optional.empty());
        Optional<MakerModel> result  = this.makerService.findById(idToFind);

        // Then
        verify(this.makerDAO).findById(anyLong());
        assertFalse(result.isPresent());
    }

    // save
    @Test
    public void testSave() {
        // Given
        MakerModel makerModel = MakerProvider.makerOptionalMock().get();

        // When
        this.makerService.save(makerModel);

        // Then
        ArgumentCaptor<MakerModel> argumentCaptor = ArgumentCaptor.forClass(MakerModel.class);
        verify(this.makerDAO).save(argumentCaptor.capture());

        assertEquals(1L, argumentCaptor.getValue().getId());
        assertEquals("Samsung", argumentCaptor.getValue().getName());
    }

    @Test
    public void testDelecteById() {
        // Given
        Long idToDelete = 1L;

        // When
        this.makerService.deleteById(idToDelete);

        // Then
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(this.makerDAO).deleteById(anyLong());
        verify(this.makerDAO).deleteById(argumentCaptor.capture());

        assertEquals(idToDelete, argumentCaptor.getValue());
    }
}

package com.cristianml.persistence.impl;

import com.cristianml.dataProvider.MakerProvider;
import com.cristianml.models.MakerModel;
import com.cristianml.repository.MakerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MakerDAOImplTest {

    @Mock
    MakerRepository makerRepository;

    @InjectMocks
    MakerDAOImpl makerDAO;


    // findAll

    @Test
    public void testFindAll() {
        List<MakerModel> makerModelList = MakerProvider.makerListMock();

        when(this.makerRepository.findAll()).thenReturn(makerModelList);
        List<MakerModel> result = this.makerDAO.findAll();

        verify(this.makerRepository).findAll();

        // Verificamos cada elemento por su id con un foreach
        for(MakerModel expected : makerModelList) {
            MakerModel actual = result.stream()
                    .filter(m -> m.getId().equals(expected.getId()))
                    .findFirst()
                    .orElse(null);


            assertNotNull(actual);
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getName(), actual.getName());
        }
    }

    // findById
    @Test
    public void testFindById_MakerExists() {
        Optional<MakerModel> makerModel = MakerProvider.makerOptionalMock();
        Long idToFind = makerModel.get().getId();

        when(this.makerRepository.findById(anyLong())).thenReturn(makerModel);
        Optional<MakerModel> result = this.makerDAO.findById(idToFind);

        verify(this.makerRepository).findById(anyLong());
        assertEquals(1L, result.get().getId());
        assertEquals("Samsung", result.get().getName());
    }

    @Test
    public void testFindById_MakerDoesNotExists() {
        Long idToFind = 999L;

        when(this.makerRepository.findById(anyLong())).thenReturn(Optional.empty());
        Optional<MakerModel> result = this.makerDAO.findById(idToFind);

        verify(this.makerRepository).findById(anyLong());
        assertTrue(result.isEmpty());
    }

    @Test
    public void testSave() {
        MakerModel makerModel = MakerProvider.makerOptionalMock().get();

        this.makerDAO.save(makerModel);
        ArgumentCaptor<MakerModel> argumentCaptor = ArgumentCaptor.forClass(MakerModel.class);
        verify(this.makerRepository).save(argumentCaptor.capture());

        assertEquals(1L, argumentCaptor.getValue().getId());
        assertEquals("Samsung", argumentCaptor.getValue().getName());
    }


    @Test
    public void testDeleteById() {
        MakerModel makerModel = MakerProvider.makerOptionalMock().get();
        Long idToDelete = makerModel.getId();

        this.makerDAO.deleteById(idToDelete);
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(this.makerRepository).deleteById(argumentCaptor.capture());

        assertEquals(1L, argumentCaptor.getValue());
    }
}

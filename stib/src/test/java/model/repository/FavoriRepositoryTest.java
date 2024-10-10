package model.repository;

import model.Line;
import model.dao.FavoriDao;
import model.dto.FavoriDto;
import model.dto.StationDto;
import model.exception.RepositoryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class FavoriRepositoryTest {
    @Mock
    private FavoriDao mock;
    private FavoriDto dto1;
    private FavoriDto dto2;
    private Map<String,FavoriDto> map;

    public FavoriRepositoryTest(){
        System.out.println("FavoriRepositoryTest constructor");
        dto1 = new FavoriDto("vacance",
                new StationDto(1234,"maison",new Line(),"huis"),
                new StationDto(2023,"vacance",new Line(),"vacancy"));
        dto2 = new FavoriDto("retour",
                new StationDto(1234,"vavance",new Line(),"vacancy"),
                new StationDto(2023,"maison",new Line(),"huis"));
        map = new HashMap<>();
        map.put("vacance",dto1);
        map.put("retour",dto2);
    }

    @BeforeEach
    void init() throws RepositoryException {
        System.out.println("before each");
        //mock behavior
        Mockito.lenient().when(mock.get(dto1.getKey())).thenReturn(dto1);
        Mockito.lenient().when(mock.getAll()).thenReturn(map);
        Mockito.lenient().when(mock.get(null)).thenThrow(RepositoryException.class);
    }

    @Test
    public void testGetExist() throws RepositoryException {
        System.out.println("testGetExist");
        FavoriDto expected = dto1;
        FavoriRepository favoriRepository = new FavoriRepository(mock);
        FavoriDto result = favoriRepository.get(dto1.getKey());
        assertEquals(expected,result);
        Mockito.verify(mock, times(1)).get(dto1.getKey());
    }
}
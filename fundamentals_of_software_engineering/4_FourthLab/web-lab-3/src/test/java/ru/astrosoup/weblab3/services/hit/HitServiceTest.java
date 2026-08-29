package ru.astrosoup.weblab3.services.hit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astrosoup.weblab3.DAOs.authorisation.UserRepository;
import ru.astrosoup.weblab3.DAOs.hit.HitRepository;
import ru.astrosoup.weblab3.DTOs.authorisation.JwtDto;
import ru.astrosoup.weblab3.DTOs.hit.AreaHitDto;
import ru.astrosoup.weblab3.DTOs.hit.AreaHitRequest;
import ru.astrosoup.weblab3.DTOs.hit.AreaHitResponse;
import ru.astrosoup.weblab3.entities.authorisation.UserEntity;
import ru.astrosoup.weblab3.entities.hit.HitEntity;
import ru.astrosoup.weblab3.exceptions.InvalidHitRequestException;
import ru.astrosoup.weblab3.exceptions.UserDoesNotExistException;
import ru.astrosoup.weblab3.services.hit.HitService;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HitServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    HitRepository hitRepository;

    @InjectMocks
    HitService hitService;

    @BeforeEach
    public void setupMocks() {
        lenient().when(userRepository.findById(1L)).thenReturn(Optional.of(new UserEntity(1L, "Serge Klimenkov", "TotallyPasswordHash")));
        lenient().when(hitRepository.save(any(HitEntity.class))).thenAnswer(returnsFirstArg());
    }

    @ParameterizedTest
    @CsvFileSource(resources = {"/good_hits_source.csv"})
    public void testThatGoodHitIsRegisteredCorrectly(int x, float y, int r) {
        assertEquals(hitService.addHit(hitDto(new AreaHitRequest(r, x, y))), goodHitResponse(r, x, y));
    }
    @ParameterizedTest
    @CsvFileSource(resources = {"/bad_hits_source.csv"})
    public void testThatBadHitIsRegisteredCorrectly(int x, float y, int r) {
        assertEquals(hitService.addHit(hitDto(new AreaHitRequest(r, x, y))), badHitResponse(r, x, y));
    }

    @Test
    public void testThatInvalidHitThrows() {
        assertThrows(InvalidHitRequestException.class, () -> hitService.addHit(hitDto(InvalidHitRequest())));
    }
    @Test
    public void testThatInvalidUserThrows() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserDoesNotExistException.class, () -> hitService.addHit(hitDto(new AreaHitRequest(1, 1, 1f))));
    }

    private AreaHitDto hitDto(AreaHitRequest request) {
        return new AreaHitDto(new JwtDto(1L, null), request);
    }


    private AreaHitResponse goodHitResponse(int r, int x, float y) {
        return new AreaHitResponse(r, x, y, true, LocalDate.now());
    }

    private AreaHitResponse badHitResponse(int r, int x, float y) {
        return new AreaHitResponse(r, x, y, false, LocalDate.now());
    }

    private AreaHitRequest InvalidHitRequest() {
        return new AreaHitRequest(999, 999, 999f);
    }
}

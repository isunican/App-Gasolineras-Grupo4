package es.unican.gasolineras.activities.combustible;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import android.content.Context;

import java.util.List;

import es.unican.gasolineras.common.Utils;
import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.repository.ICallBack;
import es.unican.gasolineras.repository.IGasolinerasRepository;

/**
 * Mock repositories for tests.
 */
public class MockRepositories {

    /**
     * Create a mock repository that uses the data in the given list
     *
     * @param context application context
     * @param gasolineras  list of stations
     * @return mock repository
     */
    public static IGasolinerasRepository getTestRepository(Context context, List<Gasolinera> gasolineras) {
        IGasolinerasRepository mock = mock(IGasolinerasRepository.class);
        doAnswer(invocation -> {
            ICallBack callBack = invocation.getArgument(0);
            callBack.onSuccess(gasolineras);
            return null;
        }).when(mock).requestGasolinerasCombustible(any(ICallBack.class), any(String.class), any(String.class));
        return mock;
    }

    /**
     * Create a mock repository that uses the data in the given list
     *
     * @param context application context
     * @param gasolineras  list of stations
     * @return mock repository
     */
    public static IGasolinerasRepository getTestRepositoryFail(Context context, List<Gasolinera> gasolineras) {
        IGasolinerasRepository mock = mock(IGasolinerasRepository.class);
        doAnswer(invocation -> {
            ICallBack callBack = invocation.getArgument(0);
            callBack.onFailure(new Throwable());
            return null;
        }).when(mock).requestGasolinerasCombustible(any(ICallBack.class), any(String.class), any(String.class));
        return mock;
    }


}

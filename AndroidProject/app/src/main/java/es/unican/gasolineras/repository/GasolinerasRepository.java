package es.unican.gasolineras.repository;

import javax.annotation.Nonnull;

import es.unican.gasolineras.model.GasolinerasResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Implementation of @link{IGasolinerasRepository} that access the real
 * <a href="https://sedeaplicaciones.minetur.gob.es/ServiciosRESTCarburantes/PreciosCarburantes/help">Gasolineras API</a>
 */
public class GasolinerasRepository implements IGasolinerasRepository {

    /** Since this class does not have any state, it can be a singleton */
    public static final IGasolinerasRepository INSTANCE = new GasolinerasRepository();

    /** Singleton pattern with private constructor */
    private GasolinerasRepository() {}

    @Override
    public void requestGasolineras(ICallBack cb, String ccaa) {
        Call<GasolinerasResponse> call = GasolinerasService.api.gasolineras(ccaa);
        call.enqueue(new Callback<GasolinerasResponse>() {
            @Override
            public void onResponse(@Nonnull Call<GasolinerasResponse> call, @Nonnull Response<GasolinerasResponse> response) {
                GasolinerasResponse body = response.body();
                assert body != null;  // to avoid warning in the following line
                cb.onSuccess(body.getGasolineras());
            }

            @Override
            public void onFailure(@Nonnull Call<GasolinerasResponse> call, @Nonnull Throwable t) {
                cb.onFailure(t);
            }
        });
    }

    @Override
    public void requestGasolinerasCombustible(ICallBack cb, String ccaa, String combustible) {
        Call<GasolinerasResponse> call = GasolinerasService.api.gasolinerasCombustible(ccaa, combustible);
        call.enqueue(new Callback<GasolinerasResponse>() {
            @Override
            public void onResponse(@Nonnull Call<GasolinerasResponse> call, @Nonnull Response<GasolinerasResponse> response) {
                GasolinerasResponse body = response.body();
                assert body != null;  // to avoid warning in the following line
                cb.onSuccess(body.getGasolineras());
            }

            @Override
            public void onFailure(@Nonnull Call<GasolinerasResponse> call, @Nonnull Throwable t) {
                cb.onFailure(t);
            }
        });
    }
}
